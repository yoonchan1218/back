package com.app.trycatch.mapper.member;

import com.app.trycatch.domain.member.AddressVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
    public void insert(AddressVO  addressVO);
}
