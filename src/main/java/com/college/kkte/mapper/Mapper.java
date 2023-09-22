package com.college.kkte.mapper;

import java.util.List;

public interface Mapper<E,D> {
    E toEntity(D dto);

    D toDto(E entity);

    default List<D> listToDto(List<E> listEntities) {
        return listEntities.stream().map(this::toDto).toList();
    }

    default List<E> listToEntity(List<D> listDto) {
        return listDto.stream().map(this::toEntity).toList();
    }
}
