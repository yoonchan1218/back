package com.app.trycatch.repository.member;

import com.app.trycatch.domain.member.AddressVO;
import com.app.trycatch.mapper.member.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AddressDAO {
    private final AddressMapper addressMapper;

    public void save(AddressVO addressVO) {
        addressMapper.insert(addressVO);
    }
}