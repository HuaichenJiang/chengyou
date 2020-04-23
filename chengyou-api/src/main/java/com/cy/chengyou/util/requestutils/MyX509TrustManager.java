/**
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.cy.chengyou.util.requestutils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author HuaichenJiang
 * @Title
 * @Description
 * @date 2018/11/23  16:27
 */
public class MyX509TrustManager implements javax.net.ssl.X509TrustManager {
    /**
     * 检查客户端证书
     * @param x509Certificates
     * @param s
     * @throws CertificateException
     */
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    /**
     * 检查服务器端证书
     * @param x509Certificates
     * @param s
     * @throws CertificateException
     */
    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    /**
     * 返回受信任的X509证书数组
     * @return
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
