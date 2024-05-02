package com.dao;

import com.entity.InOutOrderListEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.InOutOrderListView;

/**
 * 出入库订单详情 Dao 接口
 *
 * @author 
 * @since 2021-04-15
 */
public interface InOutOrderListDao extends BaseMapper<InOutOrderListEntity> {

   List<InOutOrderListView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
