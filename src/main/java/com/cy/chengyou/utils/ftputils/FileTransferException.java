package com.cy.chengyou.utils.ftputils;
/**
 * Created by Huaichen.jiang on 2017/12/10.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Huaichen.jiang
 * @Title FileTransferException
 * @Description 文件传输运行时异常
 * @date 2017年12月10日19:58:26
 */
public class FileTransferException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(FileTransferException.class);
    private String errCode;
    private String errMsg;

    public FileTransferException() {
    }

    public FileTransferException(final String message) {
        super(message);
        LOG.error(message);
    }

    public FileTransferException(final String message, final Throwable cause) {
        super(message, cause);
        LOG.error(message);
    }

    public FileTransferException(final Throwable cause) {
        super(cause);
        LOG.error(cause.toString());
    }

    public FileTransferException(final String errCode, final String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
        LOG.error(errCode + ":" + errMsg);
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
