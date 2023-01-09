package com.platform.fund.utils;


import cn.hutool.extra.ftp.Ftp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class FtpUtils {



    /**
     * 下载文件
     */
    public static File downloadFtpFile(String localPath) throws IOException {

        String host = "";
        String user = "";
        String password = "";
        Integer port = 21;
        // todo 切换 ftp 目录, 服务器上的目录
        String workPath = "";

        log.info("开始连接Ftp服务器 . . .");
        Ftp ftp = new Ftp(host, port, user, password);
        log.info("服务器连接成功 . . .");
        FTPClient client = ftp.getClient();
        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.setControlEncoding("UTF-8");
        client.enterLocalPassiveMode();

        int replyCode = client.getReplyCode();
        if (!FTPReply.isNegativePermanent(replyCode)) {
            log.info("登录 ftp 失败!");
        }

        client.changeWorkingDirectory(workPath);
        String fileName = "data_nmSBjx.zip";

        // List<FTPFile> ftpFiles = ftp.lsFiles("/", FTPFile::isFile);
        FTPFile[] ftpFiles = client.listFiles();
        File file = new File(localPath + fileName);
        for (FTPFile ftpFile : ftpFiles) {
            if (fileName.equalsIgnoreCase(ftpFile.getName())) {

                 OutputStream os = new FileOutputStream(file);
                 client.retrieveFile(fileName, os);
                 os.close();
            }
        }
        // 退出 ftp
        client.logout();
        return file;
    }


    public static List<File> unZipFiles(File zipFile, String descDir) throws IOException {

        List<File> result = new ArrayList<>();

        File destFile = new File(descDir);
        if (!destFile.exists()) {
            destFile.mkdirs();
        }
        // 解决zip文件中有中文目录或者中文文件
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            InputStream in = zip.getInputStream(entry);
            String curEntryName = entry.getName();
            // 判断文件名路径是否存在文件夹
            int endIndex = curEntryName.lastIndexOf('/');
            // 替换
            String outPath = (descDir + curEntryName).replaceAll("\\*", "/");
            if (endIndex != -1) {
                File file = new File(outPath.substring(0, outPath.lastIndexOf("/")));
                if (!file.exists()) {
                    file.mkdirs();
                }
            }

            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            File outFile = new File(outPath);
            if (outFile.isDirectory()) {
                continue;
            }
            result.add(outFile);
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        log.info("解压{}成功", zipFile.getName());
        return result;
    }

    public static void main(String[] args) throws IOException {

        File file = new File("D:\\tmp\\data_nmSBjx.zip");

        List<File> files = unZipFiles(file, "D:\\tmp\\data\\");
        System.out.println(files);


    }

}
