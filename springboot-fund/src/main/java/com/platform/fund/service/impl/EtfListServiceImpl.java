package com.platform.fund.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.fund.dto.EtfPageReq;
import com.platform.fund.entity.EtfList;
import com.platform.fund.mapper.EtfListMapper;
import com.platform.fund.service.EtfListService;
import com.platform.fund.utils.EtfListModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * etf 基金列表 服务实现类
 * </p>
 *
 * @author destiny
 * @since 2023-01-09
 */
@Service
public class EtfListServiceImpl extends ServiceImpl<EtfListMapper, EtfList> implements EtfListService {


    @Override
    public void updateInnerEtfCodeListAuto() {

        List<EtfList> rateList = baseMapper.selectEntityList();
        try {

            Map<String, EtfList> tmpList = rateList
                    .stream()
                    .collect(Collectors.toMap(EtfList::getCode, Function.identity(), (k1, k2) -> k2));

            List<EtfList> rateList1 = EtfListModel.captureEtfList();
            if (CollUtil.isNotEmpty(rateList1)) {
                for (EtfList rate : rateList1) {
                    String code = rate.getCode();
                    EtfList etfRate = tmpList.get(code);
                    if (ObjectUtil.isEmpty(etfRate)) {
                        baseMapper.insertSpecial(rate);
                    } /*else {
						rate.setId(etfRate.getId());
						baseMapper.updateById(rate);
					}*/
                }
            }


        } catch (Exception e) {

        }
    }

    @Override
    public void updateInnerEtfCodeWebAuto() {
        List<String> codeList = EtfListModel.crawlEtfCodeOnline();
        if(CollUtil.isNotEmpty(codeList)){
            codeList.forEach(node -> {
                if (StrUtil.isNotBlank(node)){
                    updateFundRateInfoByCode(node);
                }
            });
        }
    }




    @Override
    public void updateFundRateInfoByCode(String code) {
        // 查询变动信息
        EtfList rate = EtfListModel.queryEtfFoundRateInfo(code);
        if (ObjectUtil.isEmpty(rate)) {
            return;
        }
        // 填充 k 线信息
        EtfListModel.handleEtfKline(rate);
        EtfList fund = baseMapper.selectEntityByCode(code);
        if (ObjectUtil.isEmpty(fund)) {
            baseMapper.insert(rate);
        } else {
            // 更新别名信息
            if (StrUtil.isBlank(fund.getAlias())) {
                // 处理简称
                String brief = EtfListModel.capEtfBriefName(code);
                rate.setAlias(brief);
            }
            rate.setId(fund.getId());
            baseMapper.updateById(rate);
        }

    }

    @Override
    public Page<EtfList> queryEtfListPage(EtfPageReq req) {
        return baseMapper.queryEtfListPage(req);
    }


}
