package com.sk.manage.mapper;

import com.sk.manage.entity.Organization;
import com.sk.manage.ext.OrgPageReq;

import java.util.List;

/**
 * @Description
 * @Date 2022-12-24 12:33 AM
 */
public interface OrganizationMapper {


    Integer insertEntity(Organization entity);


    Integer updateByEntityId(Organization entity);

    Integer deleteById(Integer id);

    Integer selectOrgCount(OrgPageReq req);

    List<Organization> selectOrgList(OrgPageReq req);

    Organization selectEntityById(Integer id);

}
