package com.sqli.intern.api.validator.utilities.enums;

import com.sqli.intern.api.validator.utilities.exceptions.OperationException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum ExpectedTypeEnum {
    STRING, BOOLEAN, NUMBER, JSON, VOID;

    private static final List<ExpectedTypeEnum> VALID_POST_EXPECTED_TYPE;
    private static final List<ExpectedTypeEnum> VALID_PUT_EXPECTED_TYPE;
    private static final List<ExpectedTypeEnum> VALID_GET_EXPECTED_TYPE;
    private static final List<ExpectedTypeEnum> VALID_DELETE_EXPECTED_TYPE;

    static {
        VALID_POST_EXPECTED_TYPE = new ArrayList<>();
        VALID_POST_EXPECTED_TYPE.add(NUMBER);
        VALID_POST_EXPECTED_TYPE.add(STRING);
        VALID_POST_EXPECTED_TYPE.add(JSON);

        VALID_PUT_EXPECTED_TYPE = new ArrayList<>();
        VALID_PUT_EXPECTED_TYPE.add(NUMBER);
        VALID_PUT_EXPECTED_TYPE.add(STRING);
        VALID_PUT_EXPECTED_TYPE.add(JSON);

        VALID_GET_EXPECTED_TYPE = new ArrayList<>();
        VALID_GET_EXPECTED_TYPE.add(NUMBER);
        VALID_GET_EXPECTED_TYPE.add(STRING);
        VALID_GET_EXPECTED_TYPE.add(JSON);
        VALID_GET_EXPECTED_TYPE.add(BOOLEAN);

        VALID_DELETE_EXPECTED_TYPE = new ArrayList<>();
        VALID_DELETE_EXPECTED_TYPE.add(VOID);
        VALID_DELETE_EXPECTED_TYPE.add(NUMBER);
    }

    public static boolean estPostTypeValid(String type) {
        return StringUtils.isNotBlank(type)
                && VALID_POST_EXPECTED_TYPE.contains(ExpectedTypeEnum.from(type));
    }

    public static boolean estPutTypeValid(String type) {
        return StringUtils.isNotBlank(type)
                && VALID_PUT_EXPECTED_TYPE.contains(ExpectedTypeEnum.from(type));
    }

    public static boolean estGetTypeValid(String type) {
        return StringUtils.isNotBlank(type)
                && VALID_GET_EXPECTED_TYPE.contains(ExpectedTypeEnum.from(type));
    }

    public static boolean estDeleteTypeValid(String type) {
        return StringUtils.isNotBlank(type)
                && VALID_DELETE_EXPECTED_TYPE.contains(ExpectedTypeEnum.from(type));
    }

    public static ExpectedTypeEnum from(String value) {
        return Stream.of(ExpectedTypeEnum.values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new OperationException("EXPECTED TYPE NOT VALID"));
    }

}

