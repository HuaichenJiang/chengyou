/*
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 *
 */

package com.cy.chengyou.util.ftputils; /**
 * Created by Huaichen.jiang on 2019/7/20.
 */

/**
 * @author Huaichen.jiang
 * @Title: FileTransferConstants
 * @Description: 文件传输工具-基础变量
 * @date 2019年7月20日
 */
public class FileTransferConstants {

    //FTP连接超时时间，如果没有配置，默认为2分钟（120秒=120000毫秒）
    public static final Integer FTP_SFTP_CONNECT_TIMEOUT = 120000;

    public static final String FILE_SEPARATOR = "/";

    //文件处理默认字符编码
    public static final String FILE_DEFAULT_ENCODING_UTF8 = "UTF-8";

}
