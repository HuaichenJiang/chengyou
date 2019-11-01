package com.cy.chengyou.pojos;

import com.cy.chengyou.utils.StaticConstant;
import org.apache.http.protocol.HTTP;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestPojo {

    private String requestBody;
    private Map<String, String> header;
    private String username;
    private String password;
    private String url;
    private String cloudKey;
    private String secret;
    private String token;
    private Map<String, String> formParams;
    private Integer socketTimeout;
    private Integer connectTimeout;
    private Integer connectionRequestTimeout;
    private String interfaceCode;
    private String currentRequestUuid;
    private String logMsgPref;

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Map<String, String> getFormParams() {
        return formParams;
    }

    public void setFormParams(Map<String, String> formParams) {
        this.formParams = formParams;
    }

    public String getCloudKey() {
        return cloudKey;
    }

    public void setCloudKey(String cloudKey) {
        this.cloudKey = cloudKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HttpRequestPojo() {
        header = new HashMap<String, String>();
        header.put(HTTP.CONTENT_TYPE, StaticConstant.JSON_TYPE);
        header.put(HTTP.CONTENT_ENCODING, StaticConstant.ENCODING_UTF_8);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getCurrentRequestUuid() {
        return currentRequestUuid;
    }

    public void setCurrentRequestUuid(String currentRequestUuid) {
        this.currentRequestUuid = currentRequestUuid;
    }

    public String getLogMsgPref() {
        return logMsgPref;
    }

    public void setLogMsgPref(String logMsgPref) {
        this.logMsgPref = logMsgPref;
    }

    @Override
    public String toString() {
        return "HttpRequestPojo{" +
                "requestBody='" + requestBody + '\'' +
                ", header=" + header +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", cloudKey='" + cloudKey + '\'' +
                ", secret='" + secret + '\'' +
                ", token='" + token + '\'' +
                ", formParams=" + formParams +
                ", socketTimeout=" + socketTimeout +
                ", connectTimeout=" + connectTimeout +
                ", connectionRequestTimeout=" + connectionRequestTimeout +
                ", interfaceCode='" + interfaceCode + '\'' +
                ", currentRequestUuid='" + currentRequestUuid + '\'' +
                ", logMsgPref='" + logMsgPref + '\'' +
                '}';
    }
}
