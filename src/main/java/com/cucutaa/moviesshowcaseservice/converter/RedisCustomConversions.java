package com.cucutaa.moviesshowcaseservice.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.redis.core.convert.Jsr310Converters;
import org.springframework.stereotype.Component;

@Component
public class RedisCustomConversions extends org.springframework.data.convert.CustomConversions {

    private static final StoreConversions STORE_CONVERSIONS;
    private static final List<Object> STORE_CONVERTERS;

    static {

        List<Object> converters = new ArrayList<>();

        converters.add(new BinaryConverters.StringToBytesConverter());
        converters.add(new BinaryConverters.BytesToStringConverter());
        converters.add(new BinaryConverters.DirectorToBytesConverter());
        converters.add(new BinaryConverters.BytesToDirectorConverter());

        converters.addAll(Jsr310Converters.getConvertersToRegister());

        STORE_CONVERTERS = Collections.unmodifiableList(converters);
        STORE_CONVERSIONS = StoreConversions.of(SimpleTypeHolder.DEFAULT, STORE_CONVERTERS);
    }

    public RedisCustomConversions() {
        this(Collections.emptyList());
    }

    public RedisCustomConversions(List<?> converters) {
        super(STORE_CONVERSIONS, converters);
    }
}

