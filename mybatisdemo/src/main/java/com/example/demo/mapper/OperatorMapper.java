package com.example.demo.mapper;

import com.example.demo.entity.Operator;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OperatorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Operator record);


    List<Operator> selectAll();

    int updateByPrimaryKey(Operator record);
}