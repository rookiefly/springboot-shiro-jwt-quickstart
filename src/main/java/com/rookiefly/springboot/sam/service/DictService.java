package com.rookiefly.springboot.sam.service;

import com.rookiefly.springboot.sam.model.dict.Dictionary;

import java.util.List;

public interface DictService {

    List<Dictionary> queryDictionaryByCategoryId(Long categoryId);
}
