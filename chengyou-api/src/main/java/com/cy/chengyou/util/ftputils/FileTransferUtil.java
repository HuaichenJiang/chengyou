package com.cy.chengyou.util.ftputils;
/**
 * Created by Huaichen.jiang on 2019/7/20.
 */
import com.cy.chengyou.util.SpringUtil;
import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.net.ftp.FTPFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Huaichen.jiang
 * @Title: FileTransferUtil
 * @Description: 文件传输工具
 * @date 2019年7月20日17:38:57
 */
public class FileTransferUtil {

    /**
     * @param fileArray
     * @return List<String> fileNameList
     * @author Huaichen.jiang
     * @date 2017年4月6日20:03:04
     * @description 提取一个文件数组中所有的文件名
     */
    public static List<String> getAllFiles(final FTPFile[] fileArray) {
        final List<String> allFiles = new ArrayList<>();

        for (final FTPFile file : fileArray) {
            allFiles.add(file.getName());
        }
        return allFiles;
    }

    /**
     * @param fileArray
     * @return List<String> fileNameList
     * @author Huaichen.jiang
     * @date 2019年7月20日20:03:04
     * @description 提取一个文件数组中所有的文件名
     */
    public static List<String> getAllFiles(final Vector fileArray) {
        final List<String> allFiles = new ArrayList<String>();
        // Vector遍历方式1
        for (int i = 0; i < fileArray.size(); i++) {
            final ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) fileArray.get(i);
            final String fileName = lsEntry.getFilename();
            if ((".").equals(fileName) || ("..").equals(fileName)) {
                continue;
            }
            allFiles.add(fileName);
        }
        /**
         * Vector遍历方式2
         for (final Iterator<ChannelSftp.LsEntry> iterator = fileArray.iterator(); iterator.hasNext(); ) {
         final ChannelSftp.LsEntry lsEntry = iterator.next();
         final String fileName = lsEntry.getFilename();
         if ((".").equals(fileName) || ("..").equals(fileName)) {
         continue;
         }
         allFiles.add(lsEntry.getFilename());
         }*/
        return allFiles;
    }

    /**
     * @param fileArray
     * @return List<String> fileNameList
     * @author Huaichen.jiang
     * @date 2019年7月20日20:03:04
     * @description 提取一个文件数组中所有的文件名
     */
    public static List<String> getAllFiles(final File[] fileArray) {
        final List<String> allFiles = new ArrayList<>();

        for (final File file : fileArray) {
            allFiles.add(file.getName());
        }
        return allFiles;
    }

    /**
     * @param file
     * @return boolean
     * @author Huaichen.jiang
     * @date 2019年7月20日19:06:14
     * @description 判断某个文件是否为图片格式
     */
    public static boolean checkPicture(final File file) {
        final MimetypesFileTypeMap mtftp = new MimetypesFileTypeMap();
        mtftp.addMimeTypes("image png jpg jpeg gif");
        final String mimetype = mtftp.getContentType(file);
        final String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    /**
     * @param springBean
     * @return
     * @author youlong.peng
     * @date 2018年3月6日10:26:27
     * @description 根据传输方式(FTP、SFTP)获取对应的传输Service，默认FTP
     */
    public static FileTransferService loadTransferService(final String springBean) {
        return SpringUtil.getBean(springBean, FileTransferService.class);
    }

}
