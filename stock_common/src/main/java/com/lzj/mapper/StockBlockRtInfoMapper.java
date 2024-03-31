package com.lzj.mapper;

import com.lzj.pojo.domain.StockUpdownDomain;
import com.lzj.pojo.entity.StockBlockRtInfo;
import com.lzj.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;


import java.util.Date;
import java.util.List;


/**
* @author HAWEI
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2024-03-19 15:35:53
* @Entity com.lzj.pojo.entity.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockMarketIndexInfo> sectorAllLimit(Date lastDate);

    List<StockUpdownDomain> getStockPageInfo(@Param("timePoint") Date lastDate);
}

