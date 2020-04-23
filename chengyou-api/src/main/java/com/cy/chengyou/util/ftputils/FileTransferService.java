package com.cy.chengyou.util.ftputils;

import java.io.IOException;
import java.util.List;

/**
 * @author Huaichen.jiang
 * @description sftp和ftp文件传输的接口
 * @date 2019年7月20日
 */
public interface FileTransferService {

    /**
     * @param host
     * @param port
     * @param username
     * @param password
     * @description 连接ftp/sftp服务器
     * @author Huaichen.jiang
     * @date 2019年7月20日     *
     */
    public abstract void getConnect(final String host,
                                    final int port,
                                    final String username,
                                    final String password,
                                    final Integer timeout,
                                    final String encoding,
                                    final int transferType) throws Exception;

    /**
     * @description 断开连接
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    public abstract void disConn() throws Exception;

    /**
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日12:02:34
     * @description 列出FTP、SFTP当前目录下的文件列表
     */
    public abstract List<String> listFiles() throws Exception;

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日12:02:34
     * @description 列出FTP、SFTP当前目录下指定子目录中的文件列表
     */
    public abstract List<String> listFiles(final String ftpDirectory) throws Exception;

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日12:02:34
     * @description 列出FTP、SFTP当前目录下指定子目录中的文件列表
     */
    public abstract List<String> listFiles2th(final String ftpDirectory) throws Exception;

    /**
     * @param ftpDirectory ftp目录
     * @param filePrefix   文件前缀
     * @param fileSuffix   文件后缀
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @Date 2019年7月21日14:48:56
     * @description 列出指定目录下，前缀为 filePrefix 或/且 后缀为 fileSuffix 的文件列表
     * 如果只需要单独限制前缀或者后缀，则另一个传 "" 即可
     */
    public abstract List<String> listFiles(final String ftpDirectory,
                                           final String filePrefix,
                                           final String fileSuffix) throws Exception;

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @param uploadFile   要上传的文件
     * @description 上传文件
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    public abstract void upload(final String ftpDirectory,
                                final String uploadFile) throws Exception;

    /**
     * @param ftpDirectory
     * @param downloadFile
     * @param localDirectory
     * @description 下载文件
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    public abstract void download(final String ftpDirectory,
                                  final String downloadFile,
                                  final String localDirectory) throws Exception;

    /**
     * @param ftpDirectory
     * @param deleteFile
     * @description 删除文件
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    public abstract void delete(final String ftpDirectory,
                                final String deleteFile) throws Exception;

    /**
     * @return String
     * @author Huaichen.jiang
     * @date 2019年7月20日11:27:52
     * @description 返回FTP、SFTP的当前工作目录
     */
    public abstract String printWorkingDirectory() throws Exception;

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日16:01:36
     * @description FTP的当前工作目录需要回到原始工作目录
     */
    public abstract void changeToOriginalDir(final String originalDirectory) throws Exception;

    /**
     * @param targetDirectory
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日17:00:49
     * @description FTP、SFTP 切换工作目录到指定的目录
     */
    public abstract boolean changeToTargetDir(final String targetDirectory) throws Exception;

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日17:09:40
     * @description 切换 FTP、SFTP 的工作目录到父目录（即当前目录的上一级目录）
     */
    public abstract void changeToParentDir() throws Exception;

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日17:28:25
     * @description 在FTP、SFTP的当前工作目录下创建指定的子目录
     */
    public abstract void createDirectory(String childDirectory) throws Exception;

    /**
     * @param ftpDirectory
     * @return 是否成功打开
     * @author Huaichen.jiang
     * @date 2019年7月22日17:21:39
     * @description 打开SFTP服务器指定目录
     */
    public boolean openDir(final String ftpDirectory);

    /**
     * @param sourceFileName
     * @param targetFileName
     * @throws IOException
     * @author Huaichen.jiang
     * @date 2019年4月26日19:28:27
     * @description 重命名服务器上的文件 sourceFileName ——> targetFileName
     */
    public void rename(final String sourceFileName, final String targetFileName) throws IOException;
}