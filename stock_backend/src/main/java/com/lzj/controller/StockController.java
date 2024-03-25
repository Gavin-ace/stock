package com.lzj.controller;

import com.lzj.pojo.domain.InnerMarketDomain;
import com.lzj.service.StockService;
import com.lzj.vo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quot")
public class StockController {


    @Autowired
    private StockService stockService;

    @GetMapping("/index/all")
    public Result<List<InnerMarketDomain>> innerIndexAll(){
        return stockService.innerIndexAll();
    }
}
