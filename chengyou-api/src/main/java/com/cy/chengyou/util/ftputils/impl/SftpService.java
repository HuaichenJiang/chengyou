package com.cy.chengyou.util.ftputils.impl;

import java.io.*;
import java.util.*;

import com.cy.chengyou.pojos.FileTransferSftpPojo;
import com.cy.chengyou.util.ftputils.FileTransferConstants;
import com.cy.chengyou.util.ftputils.FileTransferException;
import com.cy.chengyou.util.ftputils.FileTransferService;
import com.cy.chengyou.util.ftputils.FileTransferUtil;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author Huaichen.jiang
 * @description SFTP工具类
 * @date 2019年7月20日16:34:37
 */
@Component(value = "sftpService")
public class SftpService implements FileTransferService {

    private static final Logger LOG = LoggerFactory.getLogger(SftpService.class);
    private static final ThreadLocal<FileTransferSftpPojo> THREAD_SFTP_CLIENT = new ThreadLocal<>();

    /**
     * @param host
     * @param port
     * @param username
     * @param password
     * @description 连接ftp/sftp服务器
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    @Override
    public void getConnect(final String host,
                           final int port,
                           final String username,
                           final String password,
                           final Integer timeout,
                           final String encoding,
                           final int transferType) throws Exception {

        // SFTP JSCH Session
        final JSch jsch = new JSch();
        final Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        final Properties config = new Properties();
        // 不验证 HostKey
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        if (null == timeout || timeout.intValue() == 0) {
            session.setTimeout(FileTransferConstants.FTP_SFTP_CONNECT_TIMEOUT);
        } else {
            session.setTimeout(timeout);
        }

        try {
            session.connect();
        } catch (final Exception e) {
            if (session.isConnected()) {
                session.disconnect();
            }
            LOG.error("session Connect SFTP host (" + host + ":" + port + ") error. {}", e);
            throw new Exception("session Connect SFTP host (" + host + ":" + port + ") error.", e);
        }

        //  SFTP Channel
        final Channel channel = session.openChannel("sftp");
        try {
            channel.connect();
        } catch (Exception e) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
            LOG.error("channel Connect SFTP host (" + host + ":" + port + ") error. {}", e);
            throw new Exception("channel Connect SFTP host (" + host + ":" + port + ") error.", e);
        }

        // SFTP Client
        final ChannelSftp sftpClient = (ChannelSftp) channel;
        LOG.info("SFTP Client connected." + sftpClient);

        final FileTransferSftpPojo sftpPojo = new FileTransferSftpPojo();
        sftpPojo.setSession(session);
        sftpPojo.setChannel(channel);
        sftpPojo.setSftpClient(sftpClient);
        THREAD_SFTP_CLIENT.set(sftpPojo);
        LOG.info("Current thread THREAD_SFTP_CLIENT:" + THREAD_SFTP_CLIENT);

    }

    /**
     * @description 断开连接
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    @Override
    public void disConn() throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        Session session = sftpPojo.getSession();
        Channel channel = sftpPojo.getChannel();
        ChannelSftp sftpClient = sftpPojo.getSftpClient();

        if (null != sftpClient) {
            sftpClient.disconnect();
            sftpClient.exit();
            sftpClient = null;
        }

        if (null != channel) {
            channel.disconnect();
            channel = null;
        }

        if (null != session) {
            session.disconnect();
            session = null;
        }
    }

    /**
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日12:02:34
     * @description 列出SFTP当前目录下的文件列表
     */
    @Override
    public List<String> listFiles() throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        final Vector sftpFiles = sftpClient.ls(sftpClient.pwd());
        final List<String> sftpAllFiles = FileTransferUtil.getAllFiles(sftpFiles);
        return sftpAllFiles;
    }

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日12:02:34
     * @description 列出FTP、SFTP当前目录下制定子目录中的文件列表
     */
    @Override
    public List<String> listFiles(final String ftpDirectory) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        final String originalFtpDirectory = sftpClient.pwd();
        List<String> allFiles = new ArrayList<>();
        try {
            sftpClient.cd(ftpDirectory);
        } catch (final SftpException sException) {
            if (ChannelSftp.SSH_FX_NO_SUCH_FILE == sException.id) {
                changeToOriginalDir(originalFtpDirectory);
                return allFiles;
            }
        }

        final Vector sftpFiles = sftpClient.ls(sftpClient.pwd());
        allFiles = FileTransferUtil.getAllFiles(sftpFiles);
        return allFiles;
    }

    /**
     * @param ftpDirectory ftp/sftp服务器上的目录
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日12:02:34
     * @description 列出FTP、SFTP当前目录下指定子目录中的文件列表
     */
    @Override
    public List<String> listFiles2th(final String ftpDirectory) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        return FileTransferUtil.getAllFiles(sftpClient.ls(ftpDirectory));
    }

    /**
     * @param ftpDirectory ftp目录
     * @param filePrefix   文件前缀
     * @param fileSuffix   文件后缀
     * @return
     * @throws Exception
     * @author Huaichen.jiang
     * @Date 2019年7月220日14:48:56
     * @description 列出指定目录下，前缀为 filePrefix 或/且 后缀为 fileSuffix 的文件列表
     * 如果只需要单独限制前缀或者后缀，则另一个传 "" 即可
     */
    @Override
    public List<String> listFiles(final String ftpDirectory,
                                  final String filePrefix,
                                  final String fileSuffix) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        final List<String> allFiles = FileTransferUtil.getAllFiles(sftpClient.ls(ftpDirectory));
        if (CollectionUtils.isEmpty(allFiles)) {
            return null;
        }

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
     * @date 2019年7月20日
     */
    @Override
    public void upload(final String ftpDirectory,
                       final String uploadFile) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        try {
            final String originalFtpDirectory = sftpClient.pwd();
            if (StringUtils.hasText(ftpDirectory)) {
                try {
                    sftpClient.cd(ftpDirectory);
                } catch (final SftpException sException) {
                    //指定上传路径不存在,创建目录
                    if (ChannelSftp.SSH_FX_NO_SUCH_FILE == sException.id) {
                        createDirectory(ftpDirectory);
                        sftpClient.cd(ftpDirectory);
                    }
                }
            }

            final File file = new File(uploadFile);
            final FileInputStream fileInputStream = new FileInputStream(file);
            sftpClient.put(fileInputStream, file.getName());

            // 文件上传完成之后，SFTP的当前工作目录需要回到原始工作目录
            sftpClient.cd(originalFtpDirectory);

            if (null != fileInputStream) {
                fileInputStream.close();
            }
        } catch (final Exception e) {
            LOG.error("SFTP upload file (" + uploadFile + ") error. {}", e);
            throw new Exception("SFTP upload file (" + uploadFile + ") error.",e);
        }
    }

    /**
     * @param ftpDirectory
     * @param downloadFile
     * @param localDirectory
     * @description 下载文件
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    @Override
    public void download(final String ftpDirectory,
                         final String downloadFile,
                         final String localDirectory) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        try {
            final String originalFtpDirectory = sftpClient.pwd();
            sftpClient.cd(ftpDirectory);
            final File file = new File(localDirectory);
            boolean bFile = file.exists();
            if (!bFile) {
                file.mkdirs();
            }
            final OutputStream ops = new FileOutputStream(new File(localDirectory, downloadFile));
            sftpClient.get(downloadFile, ops);

            // 文件下载完成之后，SFTP的当前工作目录需要回到原始工作目录
            sftpClient.cd(originalFtpDirectory);

            if (null != ops) {
                ops.flush();
                ops.close();
            }
        } catch (final Exception e) {
            LOG.error("SFTP download file (" + downloadFile + ") error. {}", e);
            throw new Exception("SFTP download file (" + downloadFile + ") error.", e);
        }
    }

    /**
     * @param ftpDirectory
     * @param deleteFile
     * @description 删除文件
     * @author Huaichen.jiang
     * @date 2019年7月20日
     */
    @Override
    public void delete(final String ftpDirectory,
                       final String deleteFile) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        try {
            final String originalFtpDirectory = sftpClient.pwd();
            sftpClient.cd(ftpDirectory);
            sftpClient.rm(deleteFile);

            // 文件删除完成之后，SFTP的当前工作目录需要回到原始工作目录
            sftpClient.cd(originalFtpDirectory);
        } catch (final Exception e) {
            LOG.error("SFTP delete file(" + deleteFile + ") error. {}", e);
            throw new Exception("SFTP delete file(" + deleteFile + ") error.", e);
        }
    }

    /**
     * @return String
     * @author Huaichen.jiang
     * @date 2019年7月20日11:27:52
     * @description 返回FTP、SFTP的当前工作目录
     */
    @Override
    public String printWorkingDirectory() throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        return sftpClient.pwd();
    }

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日16:01:36
     * @description FTP的当前工作目录需要回到原始工作目录
     */
    @Override
    public void changeToOriginalDir(final String originalDirectory) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        if (!originalDirectory.equals(sftpClient.pwd())) {
            changeToParentDir();
            changeToOriginalDir(originalDirectory);
        }
    }

    /**
     * @param targetDirectory
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日17:00:49
     * @description FTP、SFTP 切换工作目录到指定的目录
     */
    @Override
    public boolean changeToTargetDir(final String targetDirectory) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        boolean changeResult = false;
        try {
            sftpClient.cd(targetDirectory);
            changeResult = true;
        } catch (final Exception e) {
            /**
             * 指定上传路径不存在,创建目录
             * if (ChannelSftp.SSH_FX_NO_SUCH_FILE == sException.id) {
             changeResult = false;
             }*/
            changeResult = false;
        }
        return changeResult;
    }

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日17:09:40
     * @description 切换 FTP、SFTP 的工作目录到父目录（即当前目录的上一级目录）
     */
    @Override
    public void changeToParentDir() throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        sftpClient.cd("..");
    }

    /**
     * @throws Exception
     * @author Huaichen.jiang
     * @date 2019年7月20日17:28:25
     * @description 在FTP、SFTP的当前工作目录下创建指定的子目录
     */
    @Override
    public void createDirectory(String childDirectory) throws Exception {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        if (childDirectory.contains(FileTransferConstants.FILE_SEPARATOR)) {
            if (!childDirectory.startsWith(FileTransferConstants.FILE_SEPARATOR)) {
                final String rootPath = FileTransferConstants.FILE_SEPARATOR.equals(printWorkingDirectory()) ?
                        FileTransferConstants.FILE_SEPARATOR : printWorkingDirectory() + FileTransferConstants.FILE_SEPARATOR;
                childDirectory = rootPath + childDirectory;
            }

            final String[] dirs = childDirectory.split(FileTransferConstants.FILE_SEPARATOR);
            try {
                final String originalFtpDirectory = sftpClient.pwd();
                sftpClient.cd(FileTransferConstants.FILE_SEPARATOR);
                for (int i = 0; i < dirs.length; i++) {
                    if (StringUtils.hasText(dirs[i])) {
                        boolean dirExists = openDir(dirs[i]);
                        if (!dirExists) {
                            sftpClient.mkdir(dirs[i]);
                            sftpClient.cd(dirs[i]);
                        }
                    }
                }
                sftpClient.cd(originalFtpDirectory);
            } catch (final Exception e) {
                LOG.error("SFTP createDirectory (" + childDirectory + ") erroroccured!", e);
                throw new Exception("SFTP createDirectory (" + childDirectory + ") erroroccured!", e);
            }
        } else {
            final String originalFtpDirectory = sftpClient.pwd();
            sftpClient.mkdir(childDirectory);
            sftpClient.cd(originalFtpDirectory);
        }
    }

    /**
     * @param ftpDirectory
     * @return 是否成功打开
     * @author Huaichen.jiang
     * @date 2019年7月22日17:21:39
     * @description 打开SFTP服务器指定目录
     */
    @Override
    public boolean openDir(final String ftpDirectory) {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        try {
            if (StringUtils.hasText(ftpDirectory)) {
                sftpClient.cd(ftpDirectory);
            }
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * @param sourceFileName
     * @param targetFileName
     * @throws IOException
     * @author Huaichen.jiang
     * @date 2019年7月26日19:28:27
     * @description 重命名服务器上的文件 sourceFileName ——> targetFileName
     */
    @Override
    public void rename(final String sourceFileName, final String targetFileName) throws IOException {

        final FileTransferSftpPojo sftpPojo = THREAD_SFTP_CLIENT.get();
        if (null == sftpPojo || null == sftpPojo.getSftpClient() || !sftpPojo.getSftpClient().isConnected()) {
            LOG.error("Can't find sftpClient sftpPojo in current thread.");
            throw new FileTransferException("Can't find sftpClient sftpPojo in current thread.");
        }

        final ChannelSftp sftpClient = sftpPojo.getSftpClient();
        try {
            sftpClient.rename(sourceFileName, targetFileName);
        } catch (SftpException e) {
            LOG.error("Can't rename sourceFileName '" + sourceFileName + "' to targetFileName '" + targetFileName + "' from FTP server.", e);
            throw new IOException("Can't rename sourceFileName '" + sourceFileName + "' to targetFileName '" + targetFileName + "' from FTP server.");
        }
    }
}
