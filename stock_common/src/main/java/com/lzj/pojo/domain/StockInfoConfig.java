package com.lzj.pojo.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author : itheima
 * @date : 2022/9/20 16:35
 * @description : 定义股票的常量信息的配置类
 */
@Data
@ConfigurationProperties(prefix = "stock")
public class StockInfoConfig {
    /**
     * 国内大盘编码集合 上证和深证
     */
    private List<String> inner;

    /**
     * 外盘相关编码集合
     */
    private List<String> outer;

}
