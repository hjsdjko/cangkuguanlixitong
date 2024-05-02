package com.service.impl;

import com.utils.StringUtil;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;

import com.dao.InOutOrderListDao;
import com.entity.InOutOrderListEntity;
import com.service.InOutOrderListService;
import com.entity.view.InOutOrderListView;

/**
 * 出入库订单详情 服务实现类
 * @author 
 * @since 2021-04-15
 */
@Service("inOutOrderListService")
@Transactional
public class InOutOrderListServiceImpl extends ServiceImpl<InOutOrderListDao, InOutOrderListEntity> implements InOutOrderListService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<InOutOrderListView> page =new Query<InOutOrderListView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}
