package com.rookiefly.springboot.sam.mapper.dict;

import com.rookiefly.springboot.sam.model.dict.Dictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictMapper {

    /**
     * id查询字典
     * @param id
     * @return
     */
    Dictionary queryDictionaryById(Long id);

    /**
     * 分类id查询字典列表
     * @param categoryId
     * @return
     */
    List<Dictionary> queryDictionaryByCategoryId(Long categoryId);

}
