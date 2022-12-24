package com.sk.manage.service;

import com.sk.manage.entity.Organization;
import com.sk.manage.entity.PartyMember;
import com.sk.manage.ext.OrgPageReq;
import com.sk.manage.ext.PageResult;
import com.sk.manage.ext.PartyPageReq;

/**
 * @Description
 * @Date 2022-12-24 12:56 AM
 */
public interface OrganizationService {


    /**
     * 保存党员信息
     * @param entity
     * @return
     */
    Integer saveOrganizationInfo(Organization entity);


    /**
     * 更新党员信息
     * @param entity
     * @return
     */
    Integer updatePartyInfo(Organization entity);

    /**
     * 删除党员信息
     * @param id
     * @return
     */
    Integer deleteOrganizationInfo(Integer id);




    /**
     * 分页查询党员信息
     * @param pageReq
     * @return
     */
    PageResult<Organization> queryOrgPageInfo(OrgPageReq pageReq);



    Organization selectEntityById(Integer id );


}
