package com.gucas.fileTransfer;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class FtpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);

    public static FTPClient connection(String hostname, int port, String username, String password) {
        FTPClient ftp = new FTPClient();
        try {
            //连接FTP服务器
            ftp.connect(hostname, port);
            //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            //登录ftp
            ftp.login(username, password);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ftp;
    }

    public static void uploadFile(FTPClient ftpClient, String path, String directory, String fileName, InputStream inputStream) {
        try {
            //让客户端告诉服务端开通一个端口用来数据传输（必须要 不然会一直卡死）
            ftpClient.enterLocalPassiveMode();
            //循环生成目录并进入
            stringPath(ftpClient, path, directory);
            //如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //开始上传
            ftpClient.storeFile(new String(fileName.getBytes("GBK"), StandardCharsets.ISO_8859_1), inputStream);
            //关闭输入流
            inputStream.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void stringPath(FTPClient ftpClient, String path, String directory) throws IOException {
        List<String> string = Lists.newArrayList();
        Arrays.stream((path + directory).split("/")).filter(StringUtils::isNotBlank).forEach(string::add);
        StringBuilder paths = new StringBuilder();
        for (String s : string) {
            paths.append("/").append(new String(s.getBytes("GBK"), StandardCharsets.ISO_8859_1));
            if (!ftpClient.changeWorkingDirectory(paths.toString())) {
                ftpClient.makeDirectory(paths.toString());
            }
        }
        ftpClient.changeWorkingDirectory(paths.toString());
    }

    public static void ftpLogout(FTPClient ftpClient) {
        try {
            ftpClient.logout();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    ;

    public static void main(String[] args) {
        String hostname = "127.0.0.1";
        int port = 9100;
        String username = "admin";
        String password = "12345678";
        String path = "/usr/local/file/";
        String directory = "wenzhang";
        String fileName = "myFile.xlsx";


        //连接ftp服务
        FTPClient ftpClient = FtpUtil.connection(hostname, port, username, password);
        //上传ftp文件
        FtpUtil.uploadFile(ftpClient, path, directory, fileName, null);
        //退出ftp
        FtpUtil.ftpLogout(ftpClient);

    }

}
