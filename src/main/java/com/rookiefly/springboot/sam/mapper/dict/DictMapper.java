package com.rookiefly.springboot.sam.mapper.dict;

import com.rookiefly.springboot.sam.model.dict.Dictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictMapper {

    /**
     * code查询字典
     * @param code
     * @return
     */
    Dictionary queryDictionaryByCode(Long code);

    /**
     * 分类id查询字典列表
     * @param type
     * @return
     */
    List<Dictionary> queryDictionaryByType(String type);

}
