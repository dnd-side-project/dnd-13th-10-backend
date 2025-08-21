package com.seed.global.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ValidationUtil {

    public static List<Map<String, String>> toErrorList(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fe) {
                        return Map.of(
                                "field", fe.getField(),
                                "code", Objects.requireNonNull(fe.getCode()),
                                "message", Objects.requireNonNull(fe.getDefaultMessage()) // null 일 경우 NullPointerException(NPE) 던짐
                        );
                    }
                    return Map.of(
                            "object", error.getObjectName(),
                            "code", Objects.requireNonNull(error.getCode()),
                            "message", Objects.requireNonNull(error.getDefaultMessage())
                    );
                })
                .toList();
    }

}
