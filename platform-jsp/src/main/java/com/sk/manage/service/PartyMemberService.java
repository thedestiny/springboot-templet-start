package com.sk.manage.service;

import com.sk.manage.entity.PartyMember;
import com.sk.manage.ext.PageResult;
import com.sk.manage.ext.PartyPageReq;

/**
 * @Description
 * @Date 2022-12-24 12:56 AM
 */
public interface PartyMemberService {


    /**
     * 保存党员信息
     * @param entity
     * @return
     */
    Integer savePartyInfo(PartyMember entity);


    /**
     * 更新党员信息
     * @param entity
     * @return
     */
    Integer updatePartyInfo(PartyMember entity);

    /**
     * 删除党员信息
     * @param id
     * @return
     */
    Integer deletePartyInfo(Integer id);


    /**
     * 分页查询党员信息
     * @param pageReq
     * @return
     */
    PageResult<PartyMember> queryPartyPageInfo(PartyPageReq pageReq);



    PartyMember selectEntityById(Integer id);


}
