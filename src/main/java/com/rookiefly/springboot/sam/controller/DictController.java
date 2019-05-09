package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.model.CommonResponse;
import com.rookiefly.springboot.sam.model.dict.Dictionary;
import com.rookiefly.springboot.sam.model.dict.DictionaryCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/dict")
public class DictController {

    @GetMapping("/list")
    public CommonResponse queryDictionaryList() {
        CommonResponse successResponse = CommonResponse.newSuccessResponse();
        HashMap<Object, Object> data = new HashMap<>();
        successResponse.setData(data);
        data.put("dictionaryList", new ArrayList<Dictionary>());
        data.put("dictionaryCategoryList", new ArrayList<DictionaryCategory>());
        return successResponse;
    }
}
