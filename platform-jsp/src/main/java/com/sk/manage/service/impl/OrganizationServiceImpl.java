package com.sk.manage.service.impl;

import com.sk.manage.entity.Organization;
import com.sk.manage.ext.OrgPageReq;
import com.sk.manage.ext.PageResult;
import com.sk.manage.mapper.OrganizationMapper;
import com.sk.manage.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author destiny
 * @Date 2022-12-24 1:00 AM
 */

@Slf4j
@Service
public class OrganizationServiceImpl  implements OrganizationService {

    @Autowired
    private OrganizationMapper baseMapper;


    @Override
    public Integer saveOrganizationInfo(Organization entity) {
        return baseMapper.insertEntity(entity);
    }

    @Override
    public Integer updatePartyInfo(Organization entity) {
        return baseMapper.updateByEntityId(entity);
    }

    @Override
    public Integer deleteOrganizationInfo(Integer id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public PageResult<Organization> queryOrgPageInfo(OrgPageReq pageReq) {

// 查询总条数
        Integer count = baseMapper.selectOrgCount(pageReq);

        Integer current = pageReq.getCurrent();
        Integer pageSize = pageReq.getPageSize();
        int pages = 1;
        if (count % pageSize == 0){
            pages = count / pageSize;
        } else {
            pages = (count + pageSize) / pageSize;
        }

        Integer offset = 0;
        if(current >= pages){
            offset = (current - 1) * pageSize;
        }
        // 设置起始值
        pageReq.setOffset(offset);

        // 查询当前页数据
        List<Organization> members = baseMapper.selectOrgList(pageReq);
        PageResult<Organization> result = new PageResult<>();
        result.setTotal(count);
        result.setList(members);
        result.setPages(pages);
        result.setCurrent(current);
        result.setPageSize(pageSize);

        return result;
    }


    @Override
    public Organization selectEntityById(Integer id) {
        return baseMapper.selectEntityById(id);
    }
}
