package com.bridge.mapper;

public interface MapperImpl<T, U> {
    U toDto(T entity);
}
