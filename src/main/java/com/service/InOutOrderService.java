package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.InOutOrderEntity;
import java.util.Map;

/**
 * 出入库订单 服务类
 * @author 
 * @since 2021-04-15
 */
public interface InOutOrderService extends IService<InOutOrderEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);
}