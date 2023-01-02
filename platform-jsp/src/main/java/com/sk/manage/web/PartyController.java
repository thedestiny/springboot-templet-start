package com.sk.manage.web;

import com.sk.manage.entity.PartyMember;
import com.sk.manage.ext.PageResult;
import com.sk.manage.ext.PartyPageReq;
import com.sk.manage.ext.Result;
import com.sk.manage.service.PartyMemberService;
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
@RequestMapping(value = "pat")
public class PartyController {


    @Autowired
    private PartyMemberService partyService;


    @GetMapping(value = "list")
    public String list(){
        return "pat/list";
    }

    @GetMapping(value = "add")
    public String add() {
        return "pat/add";
    }


    /**
     * 更新数据页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "update")
    public String update(Integer id, Model model) {
        PartyMember eff = partyService.selectEntityById(id);
        model.addAttribute("data", eff);
        return "pat/update";
    }




    @GetMapping(value = "page")
    @ResponseBody
    public Result page(PartyPageReq req, Model model) {

        PageResult<PartyMember> data = partyService.queryPartyPageInfo(req);
        data.handle();
        // model.addAttribute("result", result);
        Result<PageResult<PartyMember>> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(data);
        return result;
    }

    @PostMapping(value = "save")
    @ResponseBody
    public Result<Integer> save(@RequestBody PartyMember org) {
        org.setUpdateTime(new Date());
        org.setCreateTime(new Date());
        Integer eff = partyService.savePartyInfo(org);
        Result<Integer> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(eff);
        return result;
    }

    @PostMapping(value = "update")
    @ResponseBody
    public Result<Integer> update(@RequestBody PartyMember org) {
        org.setUpdateTime(new Date());
        Integer eff = partyService.updatePartyInfo(org);
        Result<Integer> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(eff);
        return result;
    }


    @GetMapping(value = "del")
    @ResponseBody
    public Result<Integer> delete(Integer id) {
        Integer eff = partyService.deletePartyInfo(id);
        Result<Integer> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(eff);
        return result;
    }

    @GetMapping(value = "detail")
    @ResponseBody
    public Result detail(Integer id) {
        PartyMember eff = partyService.selectEntityById(id);
        Result<PartyMember> result = new Result<>();
        result.setCode(0);
        result.setMsg("请求成功");
        result.setData(eff);
        return result;
    }


}
