package com.seed.global.entity;

import com.seed.global.code.EnumCode;
import jakarta.persistence.AttributeConverter;
import java.util.EnumSet;
import org.springframework.util.StringUtils;

public abstract class EnumCodeJpaConverter<E extends Enum<E> & EnumCode> implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    public EnumCodeJpaConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        if(attribute == null) {
            return null;
        }else {
            return attribute.getCode();
        }
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if(!StringUtils.hasText(dbData)) {
            return null;
        }
        return EnumSet.allOf(enumClass)
                .stream()
                .filter(e -> e.getCode()
                        .equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not exist " + enumClass.toString() + " enum code"));
    }

}
