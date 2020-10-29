package com.cn.common.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;


/**
 * @Author fengzhilong
 * @Date 2020/10/28 14:57
 * @Desc //TODO FTP工具类
 **/
public class FTPUtil {

    //本地字符编码
    private static String localCharset = "GBK";
    private static String defaultUser = "anonymous";
    /**
     * @Author fengzhilong
     * @Desc //TODO 上传文件到FTP
     * @Date 2020/10/28 15:00
     * @Param [ftpUrl-ftp地址, filePath-ftp上传路径, fileName-文件地址(带文件名)]
     * @return boolean true-上传成功
    **/
    public static boolean upLoadFile(String ftpUrl, String username, String password, String filePath, String fileName) {
        boolean flag = false;

        filePath = filePath.replaceAll("\\\\", "\\/");
        fileName = fileName.replaceAll("\\\\", "\\/");
        String[] splitUrl = ftpUrl.trim().split(":");
        String hostname = splitUrl[0];
        Integer port = Integer.valueOf(splitUrl[1]);

        FTPClient ftpClient = new FTPClient();
        FileInputStream inputStream = null;

        try {
            //连接登陆Ftp服务器
            ftpClient.connect(hostname, port);
            ftpClient.login((username == "" ? defaultUser : username), password);
            //判断是否登陆成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("FTP连接失败");
                return flag;
            }

            //判断Ftp是否支持UTF-8编码
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))); localCharset = "UTF-8";
            ftpClient.setControlEncoding(localCharset);

            //设置被动模式
            ftpClient.enterLocalPassiveMode();
            //设置传输模式
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            //根据路径创建文件
            //FTP不能创建多级目录
            String[] splitPath = filePath.split("\\/");
            for(String directory : splitPath){
                //转码 - 防止目录中文转码
                directory = new String(directory.getBytes(localCharset), "iso-8859-1");
                //判断目录是否已存在
                if (!ftpClient.changeWorkingDirectory(directory)){
                    //创建目录
                    ftpClient.makeDirectory(directory);
                }
                ftpClient.changeWorkingDirectory(directory);
            }

            //读取上传的文件
            inputStream = new FileInputStream(new File(fileName));
            //获取文件名
            String[] splitFile = fileName.split("\\/");
            fileName = splitFile[splitFile.length - 1];
            //转码- 防止文件名中文乱码
            fileName = new String(fileName.getBytes(localCharset), "iso-8859-1");
            flag = ftpClient.storeFile(fileName, inputStream);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                ftpClient.logout();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return flag;
    }


    /**
     * @Author fengzhilong
     * @Desc //TODO FTP下载文件
     * @Date 2020/10/28 16:54
     * @Param [ftpUrl, username, password, filePath-ftp文件地址(带文件名), localPath-本地路径]
     * @return boolean
    **/
    public static boolean downLoadFile(String ftpUrl, String username, String password, String filePath, String localPath){
        boolean flag = false;

        filePath = filePath.replaceAll("\\\\", "\\/");
        localPath = localPath.replaceAll("\\\\", "\\/");
        String[] splitUrl = ftpUrl.trim().split(":");
        String hostname = splitUrl[0];
        Integer port = Integer.valueOf(splitUrl[1]);

        FTPClient ftpClient = new FTPClient();
        OutputStream outputStream = null;

        try {
            ftpClient.connect(hostname, port);
            ftpClient.login((username == "" ? defaultUser : username), password);

            //判断是否登陆成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("FTP连接失败");
                return flag;
            }
            //切换到FTP目录
            ftpClient.changeWorkingDirectory(filePath.substring(0,filePath.lastIndexOf("/")));
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if (filePath.substring(filePath.lastIndexOf("/") + 1).equals(file.getName())){
                    File localFile = new File(localPath+"/"+file.getName());
                    File path = new File(localPath);
                    if (!path.exists()){
                        path.mkdirs();
                    }
                    outputStream = new FileOutputStream(localFile);
                    flag = ftpClient.retrieveFile(file.getName(), outputStream);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                ftpClient.logout();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        return flag;
    }

    public static void main(String[] args) {

        String hostname = "172.16.200.31:21";
        boolean bool = upLoadFile(hostname, "test", "123456", "test1", "C:\\Users\\13982\\Pictures\\Saved Pictures\\5.jpeg");
        System.out.println(bool);
        /*boolean b = downLoadFile(hostname, "", "", "test1/2.jpg", "D://测试下载/1");
        System.out.println(b);*/


    }
}
