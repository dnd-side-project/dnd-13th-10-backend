package com.seed.global.code;

public interface EnumCode {
    String getCode();
    String getDescription();

    static <E extends Enum<E> & EnumCode> E valueOfCode(Class<E> enumClass, String code) {
        if (code == null || code.isBlank()) {
            return null;
        }
        for (E e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    static String getDescriptionOrNull(EnumCode enumValue) {
        return enumValue == null ? null : enumValue.getDescription();
    }
}
