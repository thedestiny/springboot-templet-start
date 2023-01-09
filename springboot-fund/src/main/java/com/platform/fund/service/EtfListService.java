package com.platform.fund.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.fund.dto.EtfPageReq;
import com.platform.fund.entity.EtfList;

/**
 * <p>
 * etf 基金列表 服务类
 * </p>
 *
 * @author destiny
 * @since 2023-01-09
 */
public interface EtfListService extends IService<EtfList> {


    /**
     * 自动更新场内 etf代码信息
     */
    void updateInnerEtfCodeListAuto();

    /**
     * 网页更新场内基金代码
     */
    void updateInnerEtfCodeWebAuto();

    /**
     * 更新基金变动信息
     * @param code
     */
    void updateFundRateInfoByCode(String code);

    /**
     * 分页查询数据
     * @param req
     * @return
     */
    Page<EtfList> queryEtfListPage(EtfPageReq req);
}
