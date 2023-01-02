package com.sk.manage.service.impl;

import com.sk.manage.entity.PartyMember;
import com.sk.manage.ext.PageResult;
import com.sk.manage.ext.PartyPageReq;
import com.sk.manage.mapper.PartyMemberMapper;
import com.sk.manage.service.PartyMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Date 2022-12-24 1:01 AM
 */

@Slf4j
@Service
public class PartyMemberServiceImpl implements PartyMemberService {

    @Autowired
    private PartyMemberMapper baseMapper;

    @Override
    public Integer savePartyInfo(PartyMember entity) {
        return baseMapper.insertEntity(entity);
    }

    @Override
    public Integer updatePartyInfo(PartyMember entity) {
        return baseMapper.updateByEntityId(entity);
    }

    @Override
    public Integer deletePartyInfo(Integer id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public PageResult<PartyMember> queryPartyPageInfo(PartyPageReq pageReq) {

        // 查询总条数
        Integer count = baseMapper.selectPartyCount(pageReq);

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
        List<PartyMember> members = baseMapper.selectPartyList(pageReq);
        PageResult<PartyMember> result = new PageResult<>();
        result.setTotal(count);
        result.setList(members);
        result.setPages(pages);
        result.setCurrent(current);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    public PartyMember selectEntityById(Integer id) {
        return baseMapper.selectEntityById(id);
    }
}
