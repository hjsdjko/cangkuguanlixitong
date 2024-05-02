package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.InOutOrderListEntity;

import com.service.InOutOrderListService;
import com.entity.view.InOutOrderListView;
import com.service.GoodsService;
import com.entity.GoodsEntity;
import com.service.InOutOrderService;
import com.entity.InOutOrderEntity;

import com.utils.PageUtils;
import com.utils.R;

/**
 * 出入库订单详情
 * 后端接口
 * @author
 * @email
 * @date 2021-04-15
*/
@RestController
@Controller
@RequestMapping("/inOutOrderList")
public class InOutOrderListController {
    private static final Logger logger = LoggerFactory.getLogger(InOutOrderListController.class);

    @Autowired
    private InOutOrderListService inOutOrderListService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;



    //级联表service
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private InOutOrderService inOutOrderService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isNotEmpty(role) && "用户".equals(role)){
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        }
        params.put("orderBy","id");
        PageUtils page = inOutOrderListService.queryPage(params);

        //字典表数据转换
        List<InOutOrderListView> list =(List<InOutOrderListView>)page.getList();
        for(InOutOrderListView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        InOutOrderListEntity inOutOrderList = inOutOrderListService.selectById(id);
        if(inOutOrderList !=null){
            //entity转view
            InOutOrderListView view = new InOutOrderListView();
            BeanUtils.copyProperties( inOutOrderList , view );//把实体数据重构到view中

            //级联表
            GoodsEntity goods = goodsService.selectById(inOutOrderList.getGoodsId());
            if(goods != null){
                BeanUtils.copyProperties( goods , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setGoodsId(goods.getId());
            }
            //级联表
            InOutOrderEntity inOutOrder = inOutOrderService.selectById(inOutOrderList.getInOutOrderId());
            if(inOutOrder != null){
                BeanUtils.copyProperties( inOutOrder , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setInOutOrderId(inOutOrder.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody InOutOrderListEntity inOutOrderList, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,inOutOrderList:{}",this.getClass().getName(),inOutOrderList.toString());
        inOutOrderList.setCreateTime(new Date());
        inOutOrderListService.insert(inOutOrderList);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody InOutOrderListEntity inOutOrderList, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,inOutOrderList:{}",this.getClass().getName(),inOutOrderList.toString());
        inOutOrderListService.updateById(inOutOrderList);//根据id更新
        return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        inOutOrderListService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }



}

