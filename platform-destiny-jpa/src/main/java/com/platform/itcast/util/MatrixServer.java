package com.platform.itcast.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

@Slf4j
public class MatrixServer implements Runnable{

    private Socket socket;

    private static ForkJoinPool pool = new ForkJoinPool();

    public MatrixServer() {
    }

    public MatrixServer(Socket socket) {
        this.socket = socket;
    }

    /**
     * Driver function. Start this server at port 33333.
     */
    public static void main(String[] args) {
        try {
            System.out.println("start server ");
            start(33333);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Start matrix server at the specified port. It should accept and handle
     * multiple client requests concurrently.
     *
     * @param port port number listened by the server
     * @throws IOException
     */
    public static void start(int port) throws IOException {

        // your implementation here

        ServerSocket ss = new ServerSocket(port);
        while (true) {
            Socket s = ss.accept();
            new Thread(new MatrixServer(s)).start();
        }

    }

    /**
     * Handle a matrix client request. It reads two matrices from socket,
     * compute their product, and then send the product matrix back to the
     * client.
     */
    @Override
    public void run() {

        // your implementation here
        try {
            ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
            Matrix temp1, temp2;
            temp1 = (Matrix) ios.readObject();
            temp2 = (Matrix) ios.readObject();

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(multiThreadMultiply(temp1, temp2));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /**
     * Compute A x B using fork-join framework.
     *
     * @param matA matrix A
     * @param matB matrix B
     * @return the matrix product of AxB
     */
    public static Matrix multiThreadMultiply(Matrix matA, Matrix matB) {

        Matrix product = new Matrix();

        // your implementation here

        ParallelMultiply task = new ParallelMultiply(matA, matB, product);
        System.out.println(pool);
        pool.invoke(task);
        System.out.println(task.c);
        return task.c;
    }
}

/**
 * Design a recursive and resultless ForkJoinTask. It splits the matrix multiplication
 * into multiple tasks to be executed in parallel.
 */
class ParallelMultiply extends RecursiveAction {

    // your implementation here
    public Matrix a;
    public Matrix b;
    public Matrix c;

    ParallelMultiply(Matrix a, Matrix b, Matrix c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    protected void compute() {
        ArrayList<RecursiveAction> tasks = new ArrayList<>();
        long[][] result = new long[a.row()][a.col()];
        for (int i = 0; i < a.row(); i++) {
            long[][] temp = new long[1][a.col()];
            for (int j = 0; j < a.col(); j++) {
                // 获取第一行数据
                temp[0][j] = a.at(i, j);
            }
            Matrix mattemp = new Matrix(temp);
            tasks.add(new ClaNode(mattemp, b, result, i));

//            Matrix matresult = new Matrix();
//
//            ParallelMultiply parallelMultiply = new ParallelMultiply(mattemp, b, matresult);
//            tasks.add(parallelMultiply);

//            Matrix matresult = mattemp.multiply(b);
//            for (int j = 0; j < a.col(); j++) {
//                result[i][j] = matresult.at(0, j);
//            }
            // tasks.add(new ParallelMultiply(mattemp, b, null));
        }
        invokeAll(tasks);
        this.c = new Matrix(result);
        System.out.println(c);

    }

}


class ClaNode extends RecursiveAction {

    // your implementation here
    public Matrix a;
    public Matrix b;
    public Integer i;
    public long[][] result;

    ClaNode(Matrix a, Matrix b, long[][] result, Integer i) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.i = i;
    }

    @Override
    protected void compute() {

        Matrix matresult = a.multiply(b);
        for (int j = 0; j < a.col(); j++) {
            result[i][j] = matresult.at(0, j);
        }
    }



}
