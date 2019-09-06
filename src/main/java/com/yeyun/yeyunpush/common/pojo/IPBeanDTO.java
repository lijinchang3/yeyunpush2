package com.yeyun.yeyunpush.common.pojo;

import java.io.Serializable;

/**
 * @Description: ip代理bean
 * @author: tzj
 * @Date: 2019/9/4
 */
public class IPBeanDTO implements Serializable {
    private String url;
    private Integer port;
    private String type;

    public IPBeanDTO(String url, Integer port, String type) {
        this.url = url;
        this.port = port;
        this.type = type;
    }

    public IPBeanDTO() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IPBean{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", type='" + type + '\'' +
                '}';
    }
}
