package com.destiny.cat.web;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.destiny.cat.dto.DataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description
 * @Date 2023-04-21 9:23 AM
 */

@Slf4j
@Controller
@RequestMapping(value = "/upload")
public class HomeController {


    @PostMapping(value = "/excel")
    @ResponseBody
    public String upload(MultipartFile file) throws Exception {

        ImportParams params = new ImportParams();
        // params.setTitleRows(1);
        // params.setHeadRows(1);

        List<DataDto> objects = ExcelImportUtil.importExcel(file.getInputStream(), DataDto.class, params);

        log.info(" data {}", JSONObject.toJSONString(objects));

        return "s";

    }


}
