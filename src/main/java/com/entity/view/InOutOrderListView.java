package com.entity.view;

import com.entity.InOutOrderListEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 出入库订单详情
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email
 * @date 2021-04-15
 */
@TableName("in_out_order_list")
public class InOutOrderListView extends InOutOrderListEntity implements Serializable {
    private static final long serialVersionUID = 1L;



		//级联表 goods
			/**
			* 物品名字
			*/
			private String goodsName;
			/**
			* 物品种类
			*/
			private Integer goodsTypes;
				/**
				* 物品种类的值
				*/
				private String goodsValue;
			/**
			* 物资数量
			*/
			private Integer goodsNumber;
			/**
			* 物品图片
			*/
			private String goodsPhoto;
			/**
			* 单位
			*/
			private String danwei;
			/**
			* 单价
			*/
			private Double danjia;
			/**
			* 物资详情
			*/
			private String goodsContent;
			/**
			* 是否删除
			*/
			private Integer flag;

		//级联表 in_out_order
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
				* 类型的值
				*/
				private String orderValue;
			@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
			@DateTimeFormat
			/**
			* 出入库时间
			*/
			private Date insertTime;

	public InOutOrderListView() {

	}

	public InOutOrderListView(InOutOrderListEntity inOutOrderListEntity) {
		try {
			BeanUtils.copyProperties(this, inOutOrderListEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}











				//级联表的get和set goods
					/**
					* 获取： 物品名字
					*/
					public String getGoodsName() {
						return goodsName;
					}
					/**
					* 设置： 物品名字
					*/
					public void setGoodsName(String goodsName) {
						this.goodsName = goodsName;
					}
					/**
					* 获取： 物品种类
					*/
					public Integer getGoodsTypes() {
						return goodsTypes;
					}
					/**
					* 设置： 物品种类
					*/
					public void setGoodsTypes(Integer goodsTypes) {
						this.goodsTypes = goodsTypes;
					}


						/**
						* 获取： 物品种类的值
						*/
						public String getGoodsValue() {
							return goodsValue;
						}
						/**
						* 设置： 物品种类的值
						*/
						public void setGoodsValue(String goodsValue) {
							this.goodsValue = goodsValue;
						}
					/**
					* 获取： 物资数量
					*/
					public Integer getGoodsNumber() {
						return goodsNumber;
					}
					/**
					* 设置： 物资数量
					*/
					public void setGoodsNumber(Integer goodsNumber) {
						this.goodsNumber = goodsNumber;
					}
					/**
					* 获取： 物品图片
					*/
					public String getGoodsPhoto() {
						return goodsPhoto;
					}
					/**
					* 设置： 物品图片
					*/
					public void setGoodsPhoto(String goodsPhoto) {
						this.goodsPhoto = goodsPhoto;
					}
					/**
					* 获取： 单位
					*/
					public String getDanwei() {
						return danwei;
					}
					/**
					* 设置： 单位
					*/
					public void setDanwei(String danwei) {
						this.danwei = danwei;
					}
					/**
					* 获取： 单价
					*/
					public Double getDanjia() {
						return danjia;
					}
					/**
					* 设置： 单价
					*/
					public void setDanjia(Double danjia) {
						this.danjia = danjia;
					}
					/**
					* 获取： 物资详情
					*/
					public String getGoodsContent() {
						return goodsContent;
					}
					/**
					* 设置： 物资详情
					*/
					public void setGoodsContent(String goodsContent) {
						this.goodsContent = goodsContent;
					}
					/**
					* 获取： 是否删除
					*/
					public Integer getFlag() {
						return flag;
					}
					/**
					* 设置： 是否删除
					*/
					public void setFlag(Integer flag) {
						this.flag = flag;
					}


				//级联表的get和set in_out_order
					/**
					* 获取： 订单名
					*/
					public String getOrderName() {
						return orderName;
					}
					/**
					* 设置： 订单名
					*/
					public void setOrderName(String orderName) {
						this.orderName = orderName;
					}
					/**
					* 获取： 操作人姓名
					*/
					public String getCaozuoName() {
						return caozuoName;
					}
					/**
					* 设置： 操作人姓名
					*/
					public void setCaozuoName(String caozuoName) {
						this.caozuoName = caozuoName;
					}
					/**
					* 获取： 操作人所在表名
					*/
					public String getCaozuoTable() {
						return caozuoTable;
					}
					/**
					* 设置： 操作人所在表名
					*/
					public void setCaozuoTable(String caozuoTable) {
						this.caozuoTable = caozuoTable;
					}
					/**
					* 获取： 类型
					*/
					public Integer getOrderTypes() {
						return orderTypes;
					}
					/**
					* 设置： 类型
					*/
					public void setOrderTypes(Integer orderTypes) {
						this.orderTypes = orderTypes;
					}


						/**
						* 获取： 类型的值
						*/
						public String getOrderValue() {
							return orderValue;
						}
						/**
						* 设置： 类型的值
						*/
						public void setOrderValue(String orderValue) {
							this.orderValue = orderValue;
						}
					/**
					* 获取： 出入库时间
					*/
					public Date getInsertTime() {
						return insertTime;
					}
					/**
					* 设置： 出入库时间
					*/
					public void setInsertTime(Date insertTime) {
						this.insertTime = insertTime;
					}













}
