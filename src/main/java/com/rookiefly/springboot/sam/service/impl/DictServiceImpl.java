package com.rookiefly.springboot.sam.service.impl;

import com.rookiefly.springboot.sam.mapper.dict.DictMapper;
import com.rookiefly.springboot.sam.model.dict.Dictionary;
import com.rookiefly.springboot.sam.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    @Cacheable(value = "dictCache", key = "targetClass + methodName + #categoryId")
    public List<Dictionary> queryDictionaryByCategoryId(Long categoryId) {
        return dictMapper.queryDictionaryByCategoryId(categoryId);
    }
}
