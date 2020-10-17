package com.cucutaa.moviesshowcaseservice.converter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import com.cucutaa.moviesshowcaseservice.model.Director;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

public final class BinaryConverters {

    public static final Charset CHARSET = StandardCharsets.UTF_8;

    private BinaryConverters() {
    }

    static class StringBasedConverter {

        byte[] fromString(String source) {
            return source.getBytes(CHARSET);
        }

        String toString(byte[] source) {
            return new String(source, CHARSET);
        }
    }

    @WritingConverter
    static class StringToBytesConverter extends StringBasedConverter implements Converter<String, byte[]> {

        @Override
        public byte[] convert(String source) {
            return fromString(source);
        }
    }

    @ReadingConverter
    static class BytesToStringConverter extends StringBasedConverter implements Converter<byte[], String> {

        @Override
        public String convert(byte[] source) {
            return toString(source);
        }

    }

    @WritingConverter
    public static class DirectorToBytesConverter implements Converter<Director, byte[]> {

        private final Jackson2JsonRedisSerializer<Director> serializer;

        public DirectorToBytesConverter() {

            serializer = new Jackson2JsonRedisSerializer<Director>(Director.class);
            serializer.setObjectMapper(new ObjectMapper());
        }

        @Override
        public byte[] convert(Director value) {
            return serializer.serialize(value);
        }
    }

    @ReadingConverter
    public static class BytesToDirectorConverter implements Converter<byte[], Director> {

        private final Jackson2JsonRedisSerializer<Director> serializer;

        public BytesToDirectorConverter() {

            serializer = new Jackson2JsonRedisSerializer<Director>(Director.class);
            serializer.setObjectMapper(new ObjectMapper());
        }

        @Override
        public Director convert(byte[] value) {
            return serializer.deserialize(value);
        }
    }
}
