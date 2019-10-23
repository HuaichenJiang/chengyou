package com.cy.chengyou.utils.ftputils.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.cy.chengyou.utils.ftputils.FileTransferConstants;
import com.cy.chengyou.utils.ftputils.FileTransferException;
import com.cy.chengyou.utils.ftputils.FileTransferService;
import com.cy.chengyou.utils.ftputils.FileTransferUtil;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author Huaichen.jiang
 * @description FTP工具类
 * @date 2018年7月20日16:20:45
 */
@Component(value = "ftpService")
public class FtpService implements FileTransferService {

    private static final Logger LOG = LoggerFactory.getLogger(FtpService.class);
    private static final ThreadLocal<FTPClient> THREAD_FTP_CLIENT = new ThreadLocal<>();

    /**
     * @param host
     * @param port
     * @param username
     * @param password
     * @description 连接ftp/sftp服务器
     * @author Huaichen.jiang
     * @date 2018年7月20日
     */
    @Override
    public void getConnect(final String host,
                           final int port,
                           final String username,
                           final String password,
                           final Integer timeout,
                           final String encoding,
                           final int transferType) throws Exception {

        try {
            final FTPClient ftpClient = new FTPClient();
            // 主动模式连接
            ftpClient.enterLocalPassiveMode();

            // 该命令可以控制：是否把执行命令打印到命令行
            // ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            //连接FTP服务器
            ftpClient.connect(host, port);
            //登录
            ftpClient.login(username, password);
            final int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                disConn();
                LOG.error("Connect FTP host (" + host + ") error.Reply(" + reply + ")");
                throw new IOException("Connect FTP host (" + host + ") error.Reply(" + reply + ")");
            }

            // 传输编码
            if (StringUtils.hasText(encoding)) {
                ftpClient.setControlEncoding(encoding);
            } else {
                ftpClient.setControlEncoding(FileTransferConstants.FILE_DEFAULT_ENCODING_UTF8);
            }

            // FTP连接超时时间，如果没有配置，默认为2分钟
            if (null == timeout || timeout.intValue() == 0) {
                ftpClient.setConnectTimeout(FileTransferConstants.FTP_SFTP_CONNECT_TIMEOUT);
            } else {
                ftpClient.setConnectTimeout(timeout);
            }
            LOG.info("FTP Client connected." + ftpClient + "; ConnectResult(" + ftpClient.isConnected() + ").");
            // 设置 FTP 使用二进制传输文件
            ftpClient.setFileType(transferType);

            THREAD_FTP_CLIENT.set(ftpClient);
            LOG.info("Current thread THREAD_FTP_CLIENT:" + THREAD_FTP_CLIENT);
        } catch (final Exception e) {
            LOG.error("Connect FTP host (" + host + ":" + port + ") error. {}", e);
            throw new Exception("Connect FTP host (" + host + ":" + port + ") error.", e);
        }

    }

    /**
     * @description 断开连接
     * @author Huaichen.jiang
     * @date 2018年7月20日
     */
    @Override
    public void disConn() throws Exception {

        FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        try {
            LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
                ftpClient = null;
            }
        } catch (final Exception e) {
            throw new Exception("Disconnect FTP error.", e);
        }
    }

    /**
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2018年7月20日12:02:34
     * @description 列出FTP当前目录下的文件列表
     */
    @Override
    public List<String> listFiles() throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        ftpClient.enterLocalPassiveMode();
        final FTPFile[] remoteInFiles = ftpClient.listFiles();
        return FileTransferUtil.getAllFiles(remoteInFiles);
    }

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2018年7月20日12:02:34
     * @description 列出FTP、SFTP当前目录下制定子目录中的文件列表
     */
    @Override
    public List<String> listFiles(final String ftpDirectory) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        ftpClient.enterLocalPassiveMode();
        final String originalFtpDirectory = ftpClient.printWorkingDirectory();
        List<String> allFiles = new ArrayList<>();
        if (ftpClient.changeWorkingDirectory(ftpDirectory)) {
            final FTPFile[] remoteInFiles = ftpClient.listFiles();
            allFiles = FileTransferUtil.getAllFiles(remoteInFiles);
        }
        changeToOriginalDir(originalFtpDirectory);
        return allFiles;
    }

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2018年7月20日12:02:34
     * @description 列出FTP、SFTP当前目录下指定子目录中的文件列表
     */
    @Override
    public List<String> listFiles2th(final String ftpDirectory) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        return FileTransferUtil.getAllFiles(ftpClient.listFiles(ftpDirectory));
    }

    /**
     * @param ftpDirectory ftp目录
     * @param filePrefix   文件前缀
     * @param fileSuffix   文件后缀
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @Date 2018年7月20日14:48:56
     * @description 列出指定目录下，前缀为 filePrefix 或/且 后缀为 fileSuffix 的文件列表
     * 如果只需要单独限制前缀或者后缀，则另一个传 "" 即可
     */
    @Override
    public List<String> listFiles(final String ftpDirectory,
                                  final String filePrefix,
                                  final String fileSuffix) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }
        final List<String> allFiles = FileTransferUtil.getAllFiles(ftpClient.listFiles(ftpDirectory));
        if (CollectionUtils.isEmpty(allFiles)) {
            return null;
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        final boolean prefixFilter = StringUtils.hasText(filePrefix);
        final boolean suffixFilter = StringUtils.hasText(fileSuffix);
        if (!prefixFilter && !suffixFilter) {
            return allFiles;
        }

        final List<String> ruleFiles = new ArrayList<>();
        for (final String fileName : allFiles) {
            if (prefixFilter && suffixFilter) {
                if (fileName.startsWith(filePrefix) && fileName.endsWith(fileSuffix)) {
                    ruleFiles.add(fileName);
                }
            } else if (prefixFilter && fileName.startsWith(filePrefix)) {
                ruleFiles.add(fileName);
            } else if (suffixFilter && fileName.endsWith(fileSuffix)) {
                ruleFiles.add(fileName);
            }
        }
        return ruleFiles;
    }

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @param uploadFile   要上传的文件
     * @description 上传文件
     * @author Huaichen.jiang
     * @date 2018年7月20日
     */
    @Override
    public void upload(final String ftpDirectory,
                       final String uploadFile) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        try {
            LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
            //如果目录不存在，则创建目录
            final String originalFtpDirectory = ftpClient.printWorkingDirectory();
            if (StringUtils.hasText(ftpDirectory)) {
                if (!ftpClient.changeWorkingDirectory(ftpDirectory)) {
                    createDirectory(ftpDirectory);
                    ftpClient.changeWorkingDirectory(ftpDirectory);
                }
            }

            final File willUploadFile = new File(uploadFile);

            if (!willUploadFile.exists()) {
                LOG.error("Can't upload (" + uploadFile + "). This file doesn't exist.");
                throw new IOException("Can't upload (" + uploadFile + "). This file doesn't exist.");
            }

            ftpClient.enterLocalPassiveMode();
            final FileInputStream input = new FileInputStream(willUploadFile);
            ftpClient.storeFile(willUploadFile.getName(), input);

            //上传文件完成之后，FTP的当前工作目录需要回到原始工作目录
            if (StringUtils.hasText(ftpDirectory)) {
                changeToOriginalDir(originalFtpDirectory);
            }

            if (null != input) {
                input.close();
            }
        } catch (final Exception e) {
            throw new Exception("Upload file (" + uploadFile + ") error.", e);
        }
    }

    /**
     * @param ftpDirectory
     * @param downloadFile
     * @param localDirectory
     * @description 下载文件
     * @author Huaichen.jiang
     * @date 2018年7月20日
     */
    @Override
    public void download(final String ftpDirectory,
                         final String downloadFile,
                         final String localDirectory) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        try {
            LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
            //转移到FTP服务器目录
            final String originalFtpDirectory = ftpClient.printWorkingDirectory();
            if (StringUtils.hasText(ftpDirectory)) {
                final boolean checkInDownloadPath = ftpClient.changeWorkingDirectory(ftpDirectory);
                if (!checkInDownloadPath) {
                    throw new NullPointerException("No ftpDirectory (" + ftpDirectory + ") can't download.");
                }
            }

            ftpClient.enterLocalPassiveMode();
            final FTPFile[] fileInfoArray = ftpClient.listFiles(downloadFile);
            if (fileInfoArray == null) {
                throw new FileNotFoundException("File " + downloadFile + " was not found on FTP server.");
            }

            final FTPFile ftpFile = fileInfoArray[0];
            long size = ftpFile.getSize();
            if (size > Integer.MAX_VALUE) {
                LOG.error("File " + downloadFile + " is too large.");
                throw new IOException("File " + downloadFile + " is too large.");
            }

            final File localPath = new File(localDirectory);
            if (!localPath.exists()) {
                localPath.mkdirs();
            }

            final OutputStream ops = new FileOutputStream(new File(localDirectory + FileTransferConstants.FILE_SEPARATOR + downloadFile));
            ftpClient.retrieveFile(ftpFile.getName(), ops);

            //下载文件完成之后，FTP的当前工作目录需要回到原始工作目录
            if (StringUtils.hasText(ftpDirectory)) {
                changeToOriginalDir(originalFtpDirectory);
            }

            if (null != ops) {
                ops.flush();
                ops.close();
            }
        } catch (final Exception e) {
            throw new Exception("Download file(" + downloadFile + ") from ftpDirectory (" + ftpDirectory + ") error.", e);
        }
    }

    /**
     * @param ftpDirectory
     * @param deleteFile
     * @description 删除文件
     * @author Huaichen.jiang
     * @date 2018年7月20日
     */
    @Override
    public void delete(final String ftpDirectory,
                       final String deleteFile) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        try {
            LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
            //转移到FTP服务器目录
            final String originalFtpDirectory = ftpClient.printWorkingDirectory();
            if (StringUtils.hasText(ftpDirectory)) {
                final boolean checkInDownloadPath = ftpClient.changeWorkingDirectory(ftpDirectory);
                if (!checkInDownloadPath) {
                    throw new NullPointerException("No ftpDirectory (" + ftpDirectory + ") can't delete.");
                }
            }

            if (!ftpClient.deleteFile(deleteFile)) {
                throw new Exception("Delete ftpFile (" + ftpDirectory + FileTransferConstants.FILE_SEPARATOR + deleteFile + ") error." + ftpClient.getReplyString());
            }

            // 删除文件完成之后，FTP的当前工作目录需要回到原始工作目录
            if (StringUtils.hasText(ftpDirectory)) {
                changeToOriginalDir(originalFtpDirectory);
            }
        } catch (final Exception e) {
            throw new Exception("Delete ftpFile (" + ftpDirectory + FileTransferConstants.FILE_SEPARATOR + deleteFile + ") error.", e);
        }
    }

    /**
     * @return String
     * @author Huaichen.jiang
     * @date 2018年7月20日11:27:52
     * @description 返回FTP、SFTP的当前工作目录
     */
    @Override
    public String printWorkingDirectory() throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        return ftpClient.printWorkingDirectory();
    }

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2018年7月20日16:01:36
     * @description FTP的当前工作目录需要回到原始工作目录
     */
    @Override
    public void changeToOriginalDir(final String originalDirectory) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        if (!originalDirectory.equals(ftpClient.printWorkingDirectory())) {
            ftpClient.changeToParentDirectory();
            changeToOriginalDir(originalDirectory);
        }
    }

    /**
     * @param targetDirectory
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2018年7月20日17:00:49
     * @description FTP、SFTP 切换工作目录到指定的目录
     */
    @Override
    public boolean changeToTargetDir(final String targetDirectory) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        if ("..".equals(targetDirectory)) {
            return ftpClient.changeToParentDirectory();
        } else {
            return ftpClient.changeWorkingDirectory(targetDirectory);
        }
    }

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2018年7月20日17:09:40
     * @description 切换 FTP、SFTP 的工作目录到父目录（即当前目录的上一级目录）
     */
    @Override
    public void changeToParentDir() throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        ftpClient.changeToParentDirectory();
    }

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2018年7月20日17:28:25
     * @description 在FTP、SFTP的当前工作目录下创建指定的子目录
     */
    @Override
    public void createDirectory(String childDirectory) throws Exception {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        boolean crtNewDir = true;
        ftpClient.enterLocalPassiveMode();
        if (childDirectory.contains(FileTransferConstants.FILE_SEPARATOR)) {
            if (!childDirectory.startsWith(FileTransferConstants.FILE_SEPARATOR)) {
                final String rootPath = FileTransferConstants.FILE_SEPARATOR.equals(printWorkingDirectory()) ?
                        FileTransferConstants.FILE_SEPARATOR : printWorkingDirectory() + FileTransferConstants.FILE_SEPARATOR;
                childDirectory = rootPath + childDirectory;
            }

            final String[] dirs = childDirectory.split(FileTransferConstants.FILE_SEPARATOR);
            try {
                final String originalFtpDirectory = ftpClient.printWorkingDirectory();
                ftpClient.changeWorkingDirectory(FileTransferConstants.FILE_SEPARATOR);
                for (final String dir : dirs) {
                    if (StringUtils.hasText(dir)) {
                        boolean dirExists = openDir(dir);
                        if (!dirExists) {
                            if (!ftpClient.makeDirectory(dir)) {
                                crtNewDir = false;
                                break;
                            }
                            if (!ftpClient.changeWorkingDirectory(dir)) {
                                crtNewDir = false;
                                break;
                            }
                        }
                    }
                }
                changeToOriginalDir(originalFtpDirectory);
            } catch (final Exception e) {
                throw new Exception("FTP createDirectory (" + childDirectory + ") erroroccured.", e);
            }
        } else {
            final FTPFile[] ftpDirs = ftpClient.listDirectories();
            for (final FTPFile dir : ftpDirs) {
                if (dir.getName().equalsIgnoreCase(childDirectory)) {
                    return;
                }
            }

            final String originalFtpDirectory = ftpClient.printWorkingDirectory();
            crtNewDir = ftpClient.makeDirectory(childDirectory);
            // 创建子目录完成之后，FTP的当前工作目录需要回到原始工作目录
            changeToOriginalDir(originalFtpDirectory);
        }

        // 如果创建目录或者切换目录失败
        if (!crtNewDir) {
            throw new Exception("FTP createDirectory (" + childDirectory + ") erroroccured last.");
        }
    }

    /**
     * @param ftpDirectory
     * @return 是否成功打开
     * @author Huaichen.jiang
     * @date 2018年7月22日17:21:39
     * @description 打开FTP服务器指定目录
     */
    @Override
    public boolean openDir(final String ftpDirectory) {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        try {
            LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
            return ftpClient.changeWorkingDirectory(ftpDirectory);
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * @param sourceFileName
     * @param targetFileName
     * @throws IOException
     * @author Huaichen.jiang
     * @date 2018年7月26日19:28:27
     * @description 重命名服务器上的文件 sourceFileName ——> targetFileName
     */
    @Override
    public void rename(final String sourceFileName, final String targetFileName) throws IOException {

        final FTPClient ftpClient = THREAD_FTP_CLIENT.get();
        if (null == ftpClient || !ftpClient.isConnected()) {
            LOG.error("Can't find ftpClient in current thread.");
            throw new FileTransferException("Can't find ftpClient in current thread.");
        }

        LOG.info("FTP connectFlag (" + ftpClient.isConnected() + ").");
        ftpClient.enterLocalPassiveMode();
        if (!ftpClient.rename(sourceFileName, targetFileName)) {
            LOG.error("Can't rename sourceFileName '" + sourceFileName + "' to targetFileName '" + targetFileName + "' from FTP server.");
            throw new IOException("Can't rename sourceFileName '" + sourceFileName + "' to targetFileName '" + targetFileName + "' from FTP server.");
        }
    }
}
