package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.model.CommonResponse;
import com.rookiefly.springboot.sam.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/type/{type}")
    public CommonResponse queryDictionaryListByType(@PathVariable("type") String type) {
        CommonResponse successResponse = CommonResponse.newSuccessResponse();
        HashMap<Object, Object> data = new HashMap<>();
        successResponse.setData(data);
        data.put("dictionaryList", dictService.queryDictionaryByType(type));
        return successResponse;
    }

    @GetMapping("/{code}")
    public CommonResponse queryDictionaryListByCategoryId(@PathVariable("code") Long code) {
        CommonResponse successResponse = CommonResponse.newSuccessResponse();
        HashMap<Object, Object> data = new HashMap<>();
        successResponse.setData(data);
        data.put("dictionary", dictService.queryDictionaryByCode(code));
        return successResponse;
    }
}
