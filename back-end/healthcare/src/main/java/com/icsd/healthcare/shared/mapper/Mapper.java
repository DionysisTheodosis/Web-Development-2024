package com.icsd.healthcare.shared.mapper;

import org.springframework.stereotype.Service;

@Service
public interface Mapper<S,T> {
     S mapDtoToEntity(T dto);
     T mapEntityToDto(S entity);
}
