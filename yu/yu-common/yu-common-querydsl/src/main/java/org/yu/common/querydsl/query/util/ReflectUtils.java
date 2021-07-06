package org.yu.common.querydsl.query.util;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.yu.common.querydsl.exception.YuQueryException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 反射工具类
 *
 * @author wangxd
 * @date 2021-04-26
 */
public class ReflectUtils {

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * 获取类中所有属性字段（包括父类，子类父类都存在的字段，使用子类中的）
     * com.querydsl.core.util.ReflectUtils
     */
    public static Set<Field> getFields(Class<?> cl) {
        Set<Field> fields = new HashSet();

        for (Class<?> c = cl; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }

        return fields;
    }

    public static <T> T invoke(Object obj, String methodName) {
        return invoke(obj, methodName, EMPTY_OBJECT_ARRAY);
    }

    public static <T> T invoke(Object obj, String methodName, Object... args) {
        Class[] argsClassArray = Arrays.stream(args).map(item -> item.getClass()).toArray(Class[]::new);
        Method method = ReflectionUtils.findMethod(obj.getClass(), methodName, argsClassArray);
        if (null == method) {
            throw new YuQueryException(String.format("No such method: [%s]", methodName));
        } else {
            return (T) ReflectionUtils.invokeMethod(method, obj, args);
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) throws YuQueryException {
        return null != obj && !StringUtils.isEmpty(fieldName) ? getFieldValue(obj, ReflectionUtils.findField(obj instanceof Class ? (Class) obj : obj.getClass(), fieldName)) : null;
    }

    public static Object getFieldValue(Object obj, Field field) throws YuQueryException {
        if (null == field) {
            return null;
        } else {
            if (obj instanceof Class) {
                obj = null;
            }

            ReflectionUtils.makeAccessible(field);

            try {
                return field.get(obj);
            } catch (IllegalAccessException var4) {
                throw new YuQueryException(String.format("IllegalAccess for %.%", field.getDeclaringClass(), field.getName()), var4);
            }
        }
    }
}
