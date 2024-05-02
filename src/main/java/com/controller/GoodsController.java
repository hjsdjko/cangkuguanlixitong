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

import com.entity.GoodsEntity;

import com.service.GoodsService;
import com.entity.view.GoodsView;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 物资
 * 后端接口
 * @author
 * @email
 * @date
*/
@RestController
@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;


    //级联表service


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        params.put("flagStart",1);params.put("flagEnd",1);params.put("orderBy","id");
        PageUtils page = goodsService.queryPage(params);

        //字典表数据转换
        List<GoodsView> list =(List<GoodsView>)page.getList();
        for(GoodsView c:list){
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
        GoodsEntity goods = goodsService.selectById(id);
        if(goods !=null){
            //entity转view
            GoodsView view = new GoodsView();
            BeanUtils.copyProperties( goods , view );//把实体数据重构到view中

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
    public R save(@RequestBody GoodsEntity goods, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,goods:{}",this.getClass().getName(),goods.toString());
        Wrapper<GoodsEntity> queryWrapper = new EntityWrapper<GoodsEntity>()
            .eq("goods_name", goods.getGoodsName())
            .eq("danwei", goods.getDanwei())
            .eq("flag", goods.getFlag())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        GoodsEntity goodsEntity = goodsService.selectOne(queryWrapper);
        if(goodsEntity==null){
            goods.setCreateTime(new Date());
            goods.setFlag(1);
            goodsService.insert(goods);
            return R.ok();
        }else {
            return R.error(511,"["+goods.getGoodsName()+"]商品的单位["+goods.getDanwei()+"]已经存在在数据库中");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody GoodsEntity goods, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,goods:{}",this.getClass().getName(),goods.toString());
        //根据字段查询是否有相同数据
        Wrapper<GoodsEntity> queryWrapper = new EntityWrapper<GoodsEntity>()
            .notIn("id",goods.getId())
            .andNew()
            .eq("goods_name", goods.getGoodsName())
            .eq("danwei", goods.getDanwei())
            .eq("flag", goods.getFlag());
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        GoodsEntity goodsEntity = goodsService.selectOne(queryWrapper);
        if("".equals(goods.getGoodsPhoto()) || "null".equals(goods.getGoodsPhoto())){
                goods.setGoodsPhoto(null);
        }
        if(goodsEntity==null){
            goodsService.updateById(goods);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"["+goods.getGoodsName()+"]商品的单位["+goods.getDanwei()+"]已经存在在数据库中");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<GoodsEntity> list=new ArrayList<>();
        for(Integer i:ids){
            GoodsEntity goods = new GoodsEntity();
            goods.setFlag(2);
            goods.setId(i);
            list.add(goods);
        }
        if(list!= null && list.size()>0){
            goodsService.updateBatchById(list);
            return R.ok();
        }else{
            return R.error("没有选择要删除的数据");
        }
    }


}

