package com.lzj.service;

import com.lzj.pojo.domain.InnerMarketDomain;
import com.lzj.pojo.domain.StockUpdownDomain;
import com.lzj.pojo.entity.StockMarketIndexInfo;
import com.lzj.vo.resp.PageResult;
import com.lzj.vo.resp.Result;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StockService {
    Result<List<InnerMarketDomain>> innerIndexAll();

    Result<List<StockMarketIndexInfo>> sectorAllLimit();


    Result<PageResult> getStockPageInfo(Integer page, Integer pageSize);

    Result<Map> getStockUpdownCount();

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);
}
