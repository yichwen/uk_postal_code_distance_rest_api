package io.yichwen.converter;

public interface Converter<F, T> {
    T convert(F from);
}
