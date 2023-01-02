package com.sk.manage.web;

import com.sk.manage.entity.Organization;
import com.sk.manage.ext.OrgPageReq;
import com.sk.manage.ext.PageResult;
import com.sk.manage.ext.Result;
import com.sk.manage.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description
 * @Date 2022-12-24 1:14 AM
 */


@Slf4j
@Controller
@RequestMapping(value = "org")
public class OrgController {


    @Autowired
    private OrganizationService orgService;


    @GetMapping(value = "list")
    public String list() {
        return "org/list";
    }

    @GetMapping(value = "add")
    public String add() {
        return "org/add";
    }


    /**
     * 更新数据页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "update")
    public String update(Integer id, Model model) {
        Organization eff = orgService.selectEntityById(id);
        model.addAttribute("data", eff);
        return "org/update";
    }


    @GetMapping(value = "page")
    @ResponseBody
    public Result page(OrgPageReq req, Model model) {

        PageResult<Organization> data = orgService.queryOrgPageInfo(req);
        data.handle();
        Result<PageResult<Organization>> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(data);
        return result;
    }

    @PostMapping(value = "save")
    @ResponseBody
    public Result save(@RequestBody Organization org) {
        org.setUpdateTime(new Date());
        org.setCreateTime(new Date());
        Integer eff = orgService.saveOrganizationInfo(org);
        Result<Integer> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(eff);
        return result;
    }

    @PostMapping(value = "update")
    @ResponseBody
    public Result update(@RequestBody Organization org) {
        org.setUpdateTime(new Date());
        Integer eff = orgService.updatePartyInfo(org);
        Result<Integer> result = new Result<>();
        result.setCode(0);
        result.setData(eff);
        result.setMsg("请求成功");
        return result;
    }


    @GetMapping(value = "del")
    @ResponseBody
    public Result delete(Integer id) {
        Integer eff = orgService.deleteOrganizationInfo(id);
        Result<Integer> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(eff);
        return result;
    }

    @GetMapping(value = "detail")
    @ResponseBody
    public Result detail(Integer id) {
        Organization eff = orgService.selectEntityById(id);
        Result<Organization> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(eff);
        return result;
    }


}
