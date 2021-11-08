package org.yu.common.web.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author wangxd
 * @date 2018-11-23
 */
public class EntityExistException extends RuntimeException {

    public EntityExistException(Class clazz, Object... saveBodyParamsMap) {
        super(EntityExistException.generateMessage(clazz.getSimpleName(), toMap(String.class, saveBodyParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> saveBodyParams) {
        return StringUtils.capitalize(entity) +
                " 已存在 " +
                saveBodyParams;
    }

    private static <K, V> Map<K, V> toMap(Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1) {
            throw new IllegalArgumentException("Invalid entries");
        }
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(((Class<K>) String.class).cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}