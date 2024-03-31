package com.lzj.controller;

import com.lzj.pojo.domain.InnerMarketDomain;
import com.lzj.pojo.entity.StockMarketIndexInfo;
import com.lzj.service.StockService;
import com.lzj.vo.resp.PageResult;
import com.lzj.vo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quot")
public class StockController {


    @Autowired
    private StockService stockService;

    @GetMapping("/index/all")
    public Result<List<InnerMarketDomain>> innerIndexAll(){
        return stockService.innerIndexAll();
    }

    @GetMapping("/sector/all")
    public Result<List<StockMarketIndexInfo>> sectorAll(){
        return stockService.sectorAllLimit();
    }

    @GetMapping("/stock/all")
    public Result<PageResult> getStockPageInfo(@RequestParam(name="page",required = false,defaultValue="1") Integer page,@RequestParam(name="pageSize",required = false, defaultValue="20")Integer pageSize){
        return stockService.getStockPageInfo(page,pageSize);

    }

    @GetMapping("/stock/updown/count")
    public Result<Map> getStockUpdownCount(){
        return stockService.getStockUpdownCount();
    }

    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response,Integer page,Integer pageSize){
        stockService.stockExport(response,page,pageSize);
    }

}
