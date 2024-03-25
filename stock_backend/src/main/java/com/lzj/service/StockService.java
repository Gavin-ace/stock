package com.lzj.service;

import com.lzj.pojo.domain.InnerMarketDomain;
import com.lzj.vo.resp.Result;

import java.util.List;

public interface StockService {
    Result<List<InnerMarketDomain>> innerIndexAll();

}
