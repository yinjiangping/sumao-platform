package team.yqby.platform.pojo;

import java.util.Date;

public class TOrder {
    private Long id;

    private String customerId;

    private Date putOrderTime;

    private Date refuseTime;

    private Byte isPay;

    private Byte process;

    private Long createby;

    private String createbyname;

    private Date createtime;

    private Long updateby;

    private String updatebyname;

    private Date updatetime;

    private String orderno;

    private String resorderno;

    private String orderamt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Date getPutOrderTime() {
        return putOrderTime;
    }

    public void setPutOrderTime(Date putOrderTime) {
        this.putOrderTime = putOrderTime;
    }

    public Date getRefuseTime() {
        return refuseTime;
    }

    public void setRefuseTime(Date refuseTime) {
        this.refuseTime = refuseTime;
    }

    public Byte getIsPay() {
        return isPay;
    }

    public void setIsPay(Byte isPay) {
        this.isPay = isPay;
    }

    public Byte getProcess() {
        return process;
    }

    public void setProcess(Byte process) {
        this.process = process;
    }

    public Long getCreateby() {
        return createby;
    }

    public void setCreateby(Long createby) {
        this.createby = createby;
    }

    public String getCreatebyname() {
        return createbyname;
    }

    public void setCreatebyname(String createbyname) {
        this.createbyname = createbyname == null ? null : createbyname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Long updateby) {
        this.updateby = updateby;
    }

    public String getUpdatebyname() {
        return updatebyname;
    }

    public void setUpdatebyname(String updatebyname) {
        this.updatebyname = updatebyname == null ? null : updatebyname.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getResorderno() {
        return resorderno;
    }

    public void setResorderno(String resorderno) {
        this.resorderno = resorderno == null ? null : resorderno.trim();
    }

    public String getOrderamt() {
        return orderamt;
    }

    public void setOrderamt(String orderamt) {
        this.orderamt = orderamt == null ? null : orderamt.trim();
    }
}