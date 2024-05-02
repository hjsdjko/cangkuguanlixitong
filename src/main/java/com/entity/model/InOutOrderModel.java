package com.entity.model;

import com.entity.InOutOrderEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 出入库订单
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 * @author 
 * @email
 * @date 2021-04-15
 */
public class InOutOrderModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 订单名
     */
    private String orderName;


    /**
     * 操作人姓名
     */
    private String caozuoName;


    /**
     * 操作人所在表名
     */
    private String caozuoTable;


    /**
     * 类型
     */
    private Integer orderTypes;


    /**
     * 出入库时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：订单名
	 */
    public String getOrderName() {
        return orderName;
    }


    /**
	 * 设置：订单名
	 */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    /**
	 * 获取：操作人姓名
	 */
    public String getCaozuoName() {
        return caozuoName;
    }


    /**
	 * 设置：操作人姓名
	 */
    public void setCaozuoName(String caozuoName) {
        this.caozuoName = caozuoName;
    }
    /**
	 * 获取：操作人所在表名
	 */
    public String getCaozuoTable() {
        return caozuoTable;
    }


    /**
	 * 设置：操作人所在表名
	 */
    public void setCaozuoTable(String caozuoTable) {
        this.caozuoTable = caozuoTable;
    }
    /**
	 * 获取：类型
	 */
    public Integer getOrderTypes() {
        return orderTypes;
    }


    /**
	 * 设置：类型
	 */
    public void setOrderTypes(Integer orderTypes) {
        this.orderTypes = orderTypes;
    }
    /**
	 * 获取：出入库时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：出入库时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
