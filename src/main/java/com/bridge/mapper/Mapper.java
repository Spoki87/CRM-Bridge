package com.bridge.mapper;

public interface Mapper<T, U> {
    U toDto(T entity);
    T toEntity(U dto);
}
