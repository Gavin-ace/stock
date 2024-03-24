package com.lzj.mapper;

import com.lzj.pojo.entity.StockBusiness;

/**
* @author HAWEI
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2024-03-19 15:35:53
* @Entity com.lzj.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

}
