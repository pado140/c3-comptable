package com.redevance.dao.ORM.converter;

public interface ConverterBase<T> {
    T parse(Object d);
}
