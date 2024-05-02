package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;

import com.entity.GoodsEntity;
import com.entity.InOutOrderListEntity;
import com.service.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;

import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.InOutOrderEntity;

import com.entity.view.InOutOrderView;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 出入库订单
 * 后端接口
 * @author
 * @email
 * @date
*/
@RestController
@Controller
@RequestMapping("/inOutOrder")
public class InOutOrderController {
    private static final Logger logger = LoggerFactory.getLogger(InOutOrderController.class);

    @Autowired
    private InOutOrderService inOutOrderService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;


    //级联表service

    // 商品表
    @Autowired
    private GoodsService goodsService;
    //订单详情表
    @Autowired
    private InOutOrderListService inOutOrderListService;


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
        PageUtils page = inOutOrderService.queryPage(params);

        //字典表数据转换
        List<InOutOrderView> list =(List<InOutOrderView>)page.getList();
        for(InOutOrderView c:list){
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
        InOutOrderEntity inOutOrder = inOutOrderService.selectById(id);
        if(inOutOrder !=null){
            //entity转view
            InOutOrderView view = new InOutOrderView();
            BeanUtils.copyProperties( inOutOrder , view );//把实体数据重构到view中

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
    public R save(@RequestBody InOutOrderEntity inOutOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,inOutOrder:{}",this.getClass().getName(),inOutOrder.toString());
        Wrapper<InOutOrderEntity> queryWrapper = new EntityWrapper<InOutOrderEntity>()
            .eq("order_name", inOutOrder.getOrderName())
            .eq("caozuo_name", inOutOrder.getCaozuoName())
            .eq("caozuo_table", inOutOrder.getCaozuoTable())
            .eq("order_types", inOutOrder.getOrderTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        InOutOrderEntity inOutOrderEntity = inOutOrderService.selectOne(queryWrapper);
        if(inOutOrderEntity==null){
            inOutOrder.setInsertTime(new Date());
            inOutOrder.setCreateTime(new Date());
        //  String role = String.valueOf(request.getSession().getAttribute("role"));
        //  if("".equals(role)){
        //      inOutOrder.set
        //  }
            inOutOrderService.insert(inOutOrder);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody InOutOrderEntity inOutOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,inOutOrder:{}",this.getClass().getName(),inOutOrder.toString());
        //根据字段查询是否有相同数据
        Wrapper<InOutOrderEntity> queryWrapper = new EntityWrapper<InOutOrderEntity>()
            .notIn("id",inOutOrder.getId())
            .eq("order_name", inOutOrder.getOrderName())
            .eq("caozuo_name", inOutOrder.getCaozuoName())
            .eq("caozuo_table", inOutOrder.getCaozuoTable())
            .eq("order_types", inOutOrder.getOrderTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        InOutOrderEntity inOutOrderEntity = inOutOrderService.selectOne(queryWrapper);
        if(inOutOrderEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      inOutOrder.set
            //  }
            inOutOrderService.updateById(inOutOrder);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
     * 出库
     */
    @RequestMapping("/outOrder")
    public R outGoods(@RequestBody  Map<String, Object> params,HttpServletRequest request){
        logger.debug("outGoods方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        Map<String, Integer> map = (Map<String, Integer>) params.get("map");
        String orderName = String.valueOf(params.get("orderName"));
        InOutOrderEntity one = inOutOrderService.selectOne(new EntityWrapper<InOutOrderEntity>().eq("order_name", orderName));
        if(one != null){
            return R.error(orderName+"的订单名已经被使用");
        }

        Set<String> ids = map.keySet();
        List<GoodsEntity> list = goodsService.selectList(new EntityWrapper<GoodsEntity>().in("id", ids));
        Date date = new Date();
        for(GoodsEntity g:list){
            Integer shiyongnumber = map.get(String.valueOf(g.getId()));
            if(g.getGoodsNumber()==null || shiyongnumber == null){
                return R.error(g.getGoodsName()+"的库存数量为空或者出库数量为空");
            }else if(g.getGoodsNumber()-shiyongnumber<0){
                return R.error(g.getGoodsName()+"的出库数量:"+shiyongnumber+"不能大于库存数量"+g.getGoodsNumber());
            }else{
                //正常出库
                g.setGoodsNumber(g.getGoodsNumber()-shiyongnumber);
            }
        }
        //修改库存
        goodsService.updateBatchById(list);

        //新增订单
        InOutOrderEntity inOutOrderEntity = new InOutOrderEntity();
        inOutOrderEntity.setCreateTime(date);
        inOutOrderEntity.setInsertTime(date);
        inOutOrderEntity.setCaozuoName(String.valueOf(request.getSession().getAttribute("username")));
        inOutOrderEntity.setCaozuoTable(String.valueOf(request.getSession().getAttribute("tableName")));
        inOutOrderEntity.setOrderTypes(1);//设置订单为出库
        inOutOrderEntity.setOrderName(orderName);
        inOutOrderService.insert(inOutOrderEntity);

        if(inOutOrderEntity.getId()!=null){
            //新增订单详情
            List<InOutOrderListEntity> inOutOrderListEntityList = new ArrayList<>();
            for(String i:ids){
                InOutOrderListEntity inOutOrderListEntity = new InOutOrderListEntity();
                inOutOrderListEntity.setCreateTime(date);
                inOutOrderListEntity.setGoodsId(Integer.valueOf(i));
                inOutOrderListEntity.setInOutOrderId(inOutOrderEntity.getId());
                inOutOrderListEntity.setOrderNumber(map.get(i));
                inOutOrderListEntityList.add(inOutOrderListEntity);
            }
            if(inOutOrderListEntityList != null && inOutOrderListEntityList.size()>0){
                inOutOrderListService.insertBatch(inOutOrderListEntityList);
                return R.ok();
            }
        }else{
            return R.error("新增订单失败");
        }
        return R.ok();
    }

    /**
     * 入库
     */
    @RequestMapping("/inOrder")
    public R inGoods(@RequestBody  Map<String, Object> params,HttpServletRequest request){
        logger.debug("inGoods方法:,,Controller:{},,map:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        Map<String, Integer> map = (Map<String, Integer>) params.get("map");
        String orderName = String.valueOf(params.get("orderName"));
        InOutOrderEntity one = inOutOrderService.selectOne(new EntityWrapper<InOutOrderEntity>().eq("order_name", orderName));
        if(one != null){
            return R.error(orderName+"的订单名已经被使用");
        }
        Set<String> ids = map.keySet();

        List<GoodsEntity> list = goodsService.selectList(new EntityWrapper<GoodsEntity>().in("id", ids));
        Date date = new Date();
        for(GoodsEntity g:list){
            Integer rukunumber = map.get(String.valueOf(g.getId()));
            Integer oldGoodsNumber = g.getGoodsNumber();
            if(rukunumber == null){
                return R.error(g.getGoodsName()+"的入库数量为空");
            }else if( oldGoodsNumber== null || oldGoodsNumber == 0){
                g.setGoodsNumber(rukunumber);
            }else{
                g.setGoodsNumber(oldGoodsNumber+rukunumber);
            }
        }
        //修改库存
        goodsService.updateBatchById(list);

        //新增订单
        InOutOrderEntity inOutOrderEntity = new InOutOrderEntity();
        inOutOrderEntity.setCreateTime(date);
        inOutOrderEntity.setInsertTime(date);
        inOutOrderEntity.setCaozuoName(String.valueOf(request.getSession().getAttribute("username")));
        inOutOrderEntity.setCaozuoTable(String.valueOf(request.getSession().getAttribute("tableName")));
        inOutOrderEntity.setOrderTypes(2);//设置订单为入库
        inOutOrderEntity.setOrderName(orderName);
        inOutOrderService.insert(inOutOrderEntity);

        if(inOutOrderEntity.getId()!=null){
            //新增订单详情
            List<InOutOrderListEntity> inOutOrderListEntityList = new ArrayList<>();
            for(String i:ids){
                InOutOrderListEntity inOutOrderListEntity = new InOutOrderListEntity();
                inOutOrderListEntity.setCreateTime(date);
                inOutOrderListEntity.setGoodsId(Integer.parseInt(i));
                inOutOrderListEntity.setInOutOrderId(inOutOrderEntity.getId());
                inOutOrderListEntity.setOrderNumber(map.get(i));
                inOutOrderListEntityList.add(inOutOrderListEntity);
            }
            if(inOutOrderListEntityList != null && inOutOrderListEntityList.size()>0){
                inOutOrderListService.insertBatch(inOutOrderListEntityList);
                return R.ok();
            }
        }else{
            return R.error("新增订单失败");
        }
        return R.ok();
    }
    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<InOutOrderEntity> list = inOutOrderService.selectList(new EntityWrapper<InOutOrderEntity>().in("id", ids));
        inOutOrderService.deleteBatchIds(Arrays.asList(ids));
        List<Integer> inOutOrderIds = new ArrayList<>();
        for(InOutOrderEntity order:list){
            inOutOrderIds.add(order.getId());
        }
        if(inOutOrderIds != null && inOutOrderIds.size()>0){
            inOutOrderListService.delete(new EntityWrapper<InOutOrderListEntity>().in("in_out_order_id", inOutOrderIds));
        }
        return R.ok();
    }


}

