package com.mediator.controller;

import java.text.ParseException;
import java.time.Duration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DurationConverter implements Converter<String, Duration>, Formatter<Duration> {
    private final Pattern pattern = Pattern.compile("(\\d+)\\s*([a-zA-Z]*)"); 
    
    @Override
    public Duration convert(String source) {
        source = source.trim();
        if ("0".equals(source)) {
            return Duration.ZERO;
        }
        Matcher matcher = pattern.matcher(source);
        if (!matcher.matches()) throw new IllegalArgumentException("Illegal period: " + source);
        long value = Long.parseLong(matcher.group(1));
        switch (matcher.group(2).toLowerCase()) {
            case "ns":
            case "nanos":
                return Duration.ofNanos(value);
            case "ms":
            case "msec":
            case "millis":
                return Duration.ofMillis(value);
            case "s":
            case "sec":
                return Duration.ofSeconds(value);
            case "m":
            case "min":
                return Duration.ofMinutes(value);
            case "h":
            case "hour":
            case "hours":
                return Duration.ofHours(value);
            case "d":
            case "day":
            case "days":
                return Duration.ofDays(value);
            
        }
        throw new IllegalArgumentException("Illegal unit: " + source);
    }

    @Override
    public Duration parse(String text, Locale locale) throws ParseException {
        return convert(text);
    }

    @Override
    public String print(Duration object, Locale locale) {
        return format(object);
    }

    public String format(Duration object) {
        if (object.isZero()) {
            return "0";
        }
        if (Duration.ofDays(object.toDays()).equals(object)) {
            return object.toDays() + "d";
        }
        if (Duration.ofHours(object.toHours()).equals(object)) {
            return object.toHours() + "h";
        }
        if (Duration.ofMinutes(object.toMinutes()).equals(object)) {
            return object.toMinutes() + "m";
        }
        if (Duration.ofSeconds(object.getSeconds()).equals(object)) {
            return object.getSeconds() + "s";
        }
        if (Duration.ofMillis(object.toMillis()).equals(object)) {
            return object.toMillis() + "ms";
        }
        return object.toNanos() + "ns";
    }
}
