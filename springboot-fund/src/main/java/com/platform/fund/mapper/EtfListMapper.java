package com.platform.fund.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.fund.dto.EtfPageReq;
import com.platform.fund.entity.EtfList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * etf 基金列表 Mapper 接口
 * </p>
 *
 * @author destiny
 * @since 2023-01-09
 */
public interface EtfListMapper extends BaseMapper<EtfList> {

    Integer insertSpecial(EtfList rate);


    Integer insertEntityList(List<EtfList> etfListList);

    Integer insertEntity(EtfList etfList);

    Integer updateByEntityId(EtfList etfList);

    List<EtfList> selectEntityList();

    /**
     * 根据 code 查询信息
     * */
    EtfList selectEntityByCode(@Param("code") String code);

    /**
     * 分页查询数据
     * @param req
     * @return
     */
    Page<EtfList> queryEtfListPage(EtfPageReq req);

    /**
     * 查询数据
     * @param dataType
     * @return
     */
    List<String> queryDataList(String dataType);
}
