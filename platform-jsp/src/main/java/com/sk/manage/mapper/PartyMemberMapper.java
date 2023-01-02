package com.sk.manage.mapper;

import com.sk.manage.entity.PartyMember;
import com.sk.manage.ext.PartyPageReq;

import java.util.List;

/**
 * @Description
 * @Date 2022-12-24 12:33 AM
 */
public interface PartyMemberMapper {



    Integer insertEntity(PartyMember entity);


    Integer updateByEntityId(PartyMember entity);

    Integer deleteById(Integer id);

    Integer selectPartyCount(PartyPageReq req);

    List<PartyMember> selectPartyList(PartyPageReq req);

    PartyMember selectEntityById(Integer id);


}
