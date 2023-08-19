package com.sqli.intern.api.validator.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.sqli.intern.api.validator.utilities.exceptions.JsonNodeException;

import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.NODE_NOT_FOUND;
import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.NOT_VALUE_NODE;

public final class JsonUtils {
    private JsonUtils() {
        throw new AssertionError("JsonUtils should not be instantiated.");
    }
    public static String getNodeValueAsText(JsonNode json, String path) {
        JsonNode node = json.at(path);

        if (node.isMissingNode()) {
            throw new JsonNodeException(NODE_NOT_FOUND);
        }

        if (!node.isValueNode()) {
            throw new JsonNodeException(NOT_VALUE_NODE);
        }

        return node.asText();
    }

    public static boolean isNodeValueNotEqual(JsonNode json, String path, String value) {
        return !getNodeValueAsText(json, path).equals(value);
    }
}

