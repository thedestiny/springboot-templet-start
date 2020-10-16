package com.destiny.camel.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class CamelLock {

    private static final Unsafe unsafe = Instance.reflectObj();
    private static long stateOffset = 0;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset(CamelLock.class.getDeclaredField("state"));
        } catch (Exception e) {
            log.error("error !");
        }
    }
    /**
     * 记录加锁的次数
     */
    private static final int state = 0;

    public static int getState() {
        return state;
    }

    private static ConcurrentLinkedDeque<Thread> linkedDeque = new ConcurrentLinkedDeque<Thread>();

    /**
     * 当前线程
     */
    private static Thread lockHolder;

    public static void setLockHolder(Thread lockHolder) {
        CamelLock.lockHolder = lockHolder;
    }

    public static Thread getLockHolder() {
        return lockHolder;
    }

    public void lock() {
        if (acquire()) {
            return;
        }

        Thread thread = Thread.currentThread();
        linkedDeque.offer(thread);

        for (; ; ) {
            // 让出 cpu 使用权 Thread.yield();
            if (thread == linkedDeque.peek() && acquire()) {
                linkedDeque.poll(); // 队列中移出
                break;
            }

            LockSupport.park(thread);
        }
    }

    public void unlock() {

        // wait 和 notify 唤醒是随机的，不能准确唤醒一个线程
        if (Thread.currentThread() != getLockHolder()) {
            throw new RuntimeException("解锁失败");
        }
        int state = getState();
        if (cas(state, 0)) {
            setLockHolder(null);
            Thread peek = linkedDeque.peek();
            if (peek != null) {
                LockSupport.unpark(peek);
            }
        }


    }

    private boolean acquire() {
        Thread thread = Thread.currentThread();
        int state = getState();
        if (state == 0) {
            if ((linkedDeque.size() == 0 || thread == linkedDeque.peek()) && cas(0, 1)) {
                setLockHolder(thread);
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    public final boolean cas(int except, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, except, update);

    }


    static class Instance {
        public static Unsafe reflectObj() {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(Boolean.TRUE);
                return (Unsafe) theUnsafe.get(null);
            } catch (Exception e) {
                log.error("error !");
            }
            return null;
        }
    }

}
