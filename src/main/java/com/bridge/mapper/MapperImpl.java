package com.bridge.mapper;

public interface MapperImpl<T, U> {
    U toDto(T entity);
    T toEntity(U dto);
}
