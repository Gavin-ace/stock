package com.lzj.service.Impl;

import com.lzj.mapper.StockMarketIndexInfoMapper;
import com.lzj.pojo.domain.InnerMarketDomain;
import com.lzj.service.StockService;
import com.lzj.vo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Override
    public Result<List<InnerMarketDomain>> innerIndexAll() {
        return null;
    }
}
