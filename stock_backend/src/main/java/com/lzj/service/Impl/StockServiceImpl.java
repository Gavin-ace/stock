package com.lzj.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzj.mapper.StockBlockRtInfoMapper;
import com.lzj.mapper.StockMarketIndexInfoMapper;
import com.lzj.mapper.StockRtInfoMapper;
import com.lzj.pojo.domain.InnerMarketDomain;
import com.lzj.pojo.domain.StockInfoConfig;
import com.lzj.pojo.domain.StockUpdownDomain;
import com.lzj.pojo.entity.StockMarketIndexInfo;
import com.lzj.service.StockService;
import com.lzj.utils.DateTimeUtil;
import com.lzj.vo.resp.PageResult;
import com.lzj.vo.resp.ResponseCode;
import com.lzj.vo.resp.Result;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    /**
     * 获取大盘的实时数据
     * @return
     */
    @Override
    public Result<List<InnerMarketDomain>> innerIndexAll() {
        //获取国内A股大盘的id集合
        List<String> inners=stockInfoConfig.getInner();
        //获取最近的交易信息
        Date lastDate=DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //将获取的date传入接口
        List<InnerMarketDomain> info=stockMarketIndexInfoMapper.getMarketInfo(inners,lastDate);
        return Result.ok(info);
    }

    /**
     * 沪深两市板块分时行情数据查询,以交易时间和交易总金额降序查询,取前10条数据
     * @return
     */
    @Override
    public Result<List<StockMarketIndexInfo>> sectorAllLimit() {
        //获取当前时间
        Date lastDate=DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //
        List<StockMarketIndexInfo> list=stockBlockRtInfoMapper.sectorAllLimit(lastDate);

        if(CollectionUtils.isEmpty(list)){
            return Result.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }


        return Result.ok(list);
    }

    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Result<PageResult> getStockPageInfo(Integer page, Integer pageSize) {
        //设置分页查询股票最新数据，并按照涨幅进行排序查询
        PageHelper.startPage(page,pageSize);
        //获取当前最新的股票交易数据
        Date lastDate=DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        List<StockUpdownDomain> infos=stockBlockRtInfoMapper.getStockPageInfo(lastDate);
        if(CollectionUtils.isEmpty(infos)){
            return Result.error(ResponseCode.NO_RESPONSE_DATA);
        }
        //获取分页信息
        PageResult<StockUpdownDomain> pageResult=new PageResult<>(new PageInfo<>(infos));
        return Result.ok(pageResult);


    }

    @Override
    public Result<Map> getStockUpdownCount() {
        //1.获取最新的交易时间范围 openTime  curTime
        //1.1 获取最新股票交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curTime = curDateTime.toDate();
        //TODO
        curTime= DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2 获取最新交易时间对应的开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(curDateTime);
        Date openTime = openDate.toDate();
        //TODO
        openTime= DateTime.parse("2022-01-06 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        List<Map> upCounts=stockRtInfoMapper.getStockUpdownCount(openDate,curDateTime,1);
        List<Map> dwCounts=stockRtInfoMapper.getStockUpdownCount(openDate,curDateTime,0);

        HashMap<String,List> mapInfo=new HashMap<>();
        mapInfo.put("upList",upCounts);
        mapInfo.put("downList",dwCounts);
        return Result.ok(mapInfo);
    }

    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        Date curDate=DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        try {

            PageHelper.startPage(page, pageSize);
            List<StockUpdownDomain> infos=stockRtInfoMapper.getAllStockUpdownByTime(curDate);
            if(CollectionUtils.isEmpty(infos)){
                Result<Object> error = Result.error(ResponseCode.NO_RESPONSE_DATA);
                //将error转化成json格式字符串
                String jsonData = new ObjectMapper().writeValueAsString(error);
                //设置响应的数据格式 告知浏览器传入的数据格式
                response.setContentType("application/json");
                //设置编码格式
                //            response.setCharacterEncoding("utf-8");
                //响应数据
                response.getWriter().write(jsonData);
                return;
            }
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String filename= URLEncoder.encode("stockRt","UTF-8");
            response.setHeader("content-disposition","attachment;filename="+filename+".xlsx");
            EasyExcel
                    .write(response.getOutputStream(),StockUpdownDomain.class)
                    .sheet("股票信息")
                    .doWrite(infos);
        }catch(Exception e){
            e.printStackTrace();
            //log.info("当前导出数据异常，当前页：{},每页大小：{},异常信息:{}",page,pageSize,e.getMessage());
        }

    }
}
