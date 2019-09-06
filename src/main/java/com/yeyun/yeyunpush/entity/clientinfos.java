package com.yeyun.yeyunpush.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_clientinfo")
public class clientinfos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String clientid;

    private Short connected;

    private Long mostsignbits;

    private Long leastsignbits;

    private Date lastconnecteddate;

    /**
     * @return clientid
     */
    public String getClientid() {
        return clientid;
    }

    /**
     * @param clientid
     */
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    /**
     * @return connected
     */
    public Short getConnected() {
        return connected;
    }

    /**
     * @param connected
     */
    public void setConnected(Short connected) {
        this.connected = connected;
    }

    /**
     * @return mostsignbits
     */
    public Long getMostsignbits() {
        return mostsignbits;
    }

    /**
     * @param mostsignbits
     */
    public void setMostsignbits(Long mostsignbits) {
        this.mostsignbits = mostsignbits;
    }

    /**
     * @return leastsignbits
     */
    public Long getLeastsignbits() {
        return leastsignbits;
    }

    /**
     * @param leastsignbits
     */
    public void setLeastsignbits(Long leastsignbits) {
        this.leastsignbits = leastsignbits;
    }

    /**
     * @return lastconnecteddate
     */
    public Date getLastconnecteddate() {
        return lastconnecteddate;
    }

    /**
     * @param lastconnecteddate
     */
    public void setLastconnecteddate(Date lastconnecteddate) {
        this.lastconnecteddate = lastconnecteddate;
    }
}