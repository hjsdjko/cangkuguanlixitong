package com.entity.vo;

import com.entity.InOutOrderListEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 出入库订单详情
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 * @author 
 * @email
 * @date 2021-04-15
 */
@TableName("in_out_order_list")
public class InOutOrderListVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 订单id
     */

    @TableField(value = "in_out_order_id")
    private Integer inOutOrderId;


    /**
     * 物资表id
     */

    @TableField(value = "goods_id")
    private Integer goodsId;


    /**
     * 数量
     */

    @TableField(value = "order_number")
    private Integer orderNumber;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
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
	 * 设置：订单id
	 */
    public Integer getInOutOrderId() {
        return inOutOrderId;
    }


    /**
	 * 获取：订单id
	 */

    public void setInOutOrderId(Integer inOutOrderId) {
        this.inOutOrderId = inOutOrderId;
    }
    /**
	 * 设置：物资表id
	 */
    public Integer getGoodsId() {
        return goodsId;
    }


    /**
	 * 获取：物资表id
	 */

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    /**
	 * 设置：数量
	 */
    public Integer getOrderNumber() {
        return orderNumber;
    }


    /**
	 * 获取：数量
	 */

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

}
