package com.cy.chengyou.pojos;

/**
 *
 * @author HuaichenJiang
 * @Description http请求结果
 * @date 2019-10-23
 */
public class HttpResponsePojo {

    private int statusCode;

    private String reasonPhrase;

    private String respBody;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRespBody() {
        return respBody;
    }

    public void setRespBody(final String respBody) {
        this.respBody = respBody;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(final String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String toString() {
        return "HttpResponsePojo{" +
                "statusCode=" + statusCode +
                ", reasonPhrase='" + reasonPhrase + '\'' +
                ", respBody='" + respBody + '\'' +
                '}';
    }
}
