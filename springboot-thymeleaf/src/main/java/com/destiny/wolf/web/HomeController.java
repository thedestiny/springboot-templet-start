package com.destiny.wolf.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.redisson.RedissonDelayedQueue;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private RedissonClient redissonClient;

    @NacosValue(value = "${app.msg}")
    private String msg;


    @RequestMapping(value = {"/" , "home.html" , "index.html"})
    public String home(HttpServletRequest request) {

        String url = request.getRequestURI();
        String date = new Date().toString();
        log.info(" home is {} and date {}" , url, date);

        return "home";
    }

    // https://www.cnblogs.com/baixianlong/p/10661591.html
    // @Async("taskExecutor")
    @GetMapping(value = "test/async")
    @ResponseBody
    public Callable<String> test(HttpServletResponse response, HttpServletRequest request) {

        JSONObject json = new JSONObject();
        json.put("code" , 1);
        json.put("msg" , "请求成功!");

        // 返回对象
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(2);
            return json.toString();
        };

        return callable;
    }


    public void testLock() {

        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        String orderId = snowflake.nextIdStr();
        String lockName = "APP:ORDER:" + orderId;

        RAtomicDouble atomicDouble = redissonClient.getAtomicDouble("global");
        RLock fairLock = redissonClient.getFairLock("lock-fair");
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("read-write-lock");

        RLock lock = redissonClient.getLock(lockName);

        try {
            // todo business
            lock.lock();

        } catch (Exception e) {
            log.info("encounter exception e is {} and detail is {}" , e.getMessage(), e);

        } finally {
            lock.unlock();
        }


    }

    // 下载测试
    @GetMapping(value = "/zip")
    public void downZip(HttpServletResponse response) throws IOException {
        // 声明字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        List<String> dataList = Lists.newArrayList("aaa","bbb");

        for (String node : dataList) {

            // 可以进行循环
            // 添加到zip
            zip.putNextEntry(new ZipEntry(node + ".txt"));
            StringWriter sw = new StringWriter();
            sw.append("rrrrrrrrrr");
            IOUtils.write(sw.toString(), zip,"UTF-8");
            IOUtils.closeQuietly(sw);
            zip.flush();
            zip.closeEntry();
        }



        IOUtils.closeQuietly(zip);
        byte[] data = outputStream.toByteArray();
        genCode(response, data);

    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {

        String zipname = DateUtil.format(new Date(), "MMddHHmmss");

        response.reset();
        response.addHeader("Access-Control-Allow-Origin" , "*");
        response.addHeader("Access-Control-Expose-Headers" , "Content-Disposition");
        response.setHeader("Content-Disposition" , "attachment; filename=\"ruoyi" + zipname + ".zip\"");
        response.addHeader("Content-Length" , "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }


}
