package com.cy.chengyou.pojos;
/**
 * Created by Huaichen.jiang on 2019/7/20.
 */

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

/**
 * @author Huaichen.jiang
 * @Title: FileTransferSftpPojo
 * @Description: SFTP 文件
 * @date 2019年7月20日
 */
public class FileTransferSftpPojo {

    private Session session = null;
    private Channel channel = null;
    private ChannelSftp sftpClient = null;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelSftp getSftpClient() {
        return sftpClient;
    }

    public void setSftpClient(ChannelSftp sftpClient) {
        this.sftpClient = sftpClient;
    }
}
