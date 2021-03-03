package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.common.Result;
import com.rookiefly.springboot.sam.service.DictService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/type/{type}")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public Result queryDictionaryListByType(@PathVariable("type") String type) {
        return Result.success(dictService.queryDictionaryByType(type));
    }

    @GetMapping("/{code}")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public Result queryDictionaryListByCategoryId(@PathVariable("code") Long code) {
        return Result.success(dictService.queryDictionaryByCode(code));
    }
}
