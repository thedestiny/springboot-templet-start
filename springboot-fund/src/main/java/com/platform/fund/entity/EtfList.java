package com.platform.fund.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * etf 基金列表
 * </p>
 *
 * @author destiny
 * @since 2023-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_etf_list")
public class EtfList implements Serializable {

    private static final long serialVersionUID = 5968309991845649776L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * etf代码
     */
    private String code;

    /**
     * etf名称
     */
    private String name;

    /**
     * 基金别名
     */
    private String alias;

    /**
     * 基金规模
     */
    private BigDecimal fundSize;

    /**
     * 基金经理
     */
    private String fundManager;

    /**
     * 基金公司
     */
    private String fundCompany;

    /**
     * 成立日期
     */
    private Integer createTime;

    /**
     * 基金规模更新日期
     */
    private Integer updateTime;

    /**
     * 净值更新日期
     */
    private Integer formatTime;

    /**
     * 跟踪标地
     */
    private String indexTarget;

    /**
     * 跟踪误差
     */
    private BigDecimal indexError;

    /**
     * 基金价格
     */
    private BigDecimal price;

    /**
     * 价格变动
     */
    private BigDecimal priceChange;

    /**
     * 变动比例
     */
    private BigDecimal rateChange;

    /**
     * 访问链接
     */
    private String href;

    /**
     * 阶段涨幅-近一周
     */
    private BigDecimal stageWeek1;

    /**
     * 阶段涨幅-近一个月
     */
    private BigDecimal stageMonth1;

    /**
     * 阶段涨幅-近三个月
     */
    private BigDecimal stageMonth3;

    /**
     * 阶段涨幅-近六个月
     */
    private BigDecimal stageMonth6;

    /**
     * 阶段涨幅-今年以来
     */
    private BigDecimal stageYear;

    /**
     * 阶段涨幅-近1年
     */
    private BigDecimal stageYear1;

    /**
     * 阶段涨幅-近2年
     */
    private BigDecimal stageYear2;

    /**
     * 阶段涨幅-近3年
     */
    private BigDecimal stageYear3;

    /**
     * 当月
     */
    private String monthA;

    /**
     * 前一个月
     */
    private String monthB;

    /**
     * 前2个月
     */
    private String monthC;

    /**
     * 前3个月
     */
    private String monthD;

    /**
     * 前4个月
     */
    private String monthE;

    /**
     * 前5个月
     */
    private String monthF;

    /**
     * 前6个月
     */
    private String monthG;

    /**
     * 数据类型 1 etf 2 stock
     */
    private String dataType;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateTime;

    /**  1 终止 0 正常 */
    private String endFlag = "0";


    /**
     * 标准差
     * 反映基金收益率的波动程度。标准差越小，基金的历史阶段收益越稳定
     * */
    private BigDecimal standError;

    /**
     * 夏普比率
     * 反映基金承担单位风险，所能获得的超过无风险收益的超额收益。夏普比率越大，基金的历史阶段绩效表现越佳。
     * */
    private BigDecimal sharpRate;


}
