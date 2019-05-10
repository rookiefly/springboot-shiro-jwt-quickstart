package com.rookiefly.springboot.sam.service;

import com.rookiefly.springboot.sam.model.dict.Dictionary;

import java.util.List;

public interface DictService {

    Dictionary queryDictionaryByCode(Long code);

    List<Dictionary> queryDictionaryByType(String type);
}
