package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 出入库订单
 *
 * @author 
 * @email
 * @date 2021-04-15
 */
@TableName("in_out_order")
public class InOutOrderEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public InOutOrderEntity() {

	}

	public InOutOrderEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")

    private Integer id;


    /**
     * 订单名
     */
    @TableField(value = "order_name")

    private String orderName;


    /**
     * 操作人姓名
     */
    @TableField(value = "caozuo_name")

    private String caozuoName;


    /**
     * 操作人所在表名
     */
    @TableField(value = "caozuo_table")

    private String caozuoTable;


    /**
     * 类型
     */
    @TableField(value = "order_types")

    private Integer orderTypes;


    /**
     * 出入库时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：订单名
	 */
    public String getOrderName() {
        return orderName;
    }


    /**
	 * 获取：订单名
	 */

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    /**
	 * 设置：操作人姓名
	 */
    public String getCaozuoName() {
        return caozuoName;
    }


    /**
	 * 获取：操作人姓名
	 */

    public void setCaozuoName(String caozuoName) {
        this.caozuoName = caozuoName;
    }
    /**
	 * 设置：操作人所在表名
	 */
    public String getCaozuoTable() {
        return caozuoTable;
    }


    /**
	 * 获取：操作人所在表名
	 */

    public void setCaozuoTable(String caozuoTable) {
        this.caozuoTable = caozuoTable;
    }
    /**
	 * 设置：类型
	 */
    public Integer getOrderTypes() {
        return orderTypes;
    }


    /**
	 * 获取：类型
	 */

    public void setOrderTypes(Integer orderTypes) {
        this.orderTypes = orderTypes;
    }
    /**
	 * 设置：出入库时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：出入库时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "InOutOrder{" +
            "id=" + id +
            ", orderName=" + orderName +
            ", caozuoName=" + caozuoName +
            ", caozuoTable=" + caozuoTable +
            ", orderTypes=" + orderTypes +
            ", insertTime=" + insertTime +
            ", createTime=" + createTime +
        "}";
    }
}
