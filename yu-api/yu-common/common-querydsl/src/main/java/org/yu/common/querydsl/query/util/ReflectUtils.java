package org.yu.common.querydsl.query.util;

import com.querydsl.core.util.ArrayUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.yu.common.querydsl.exception.YuQueryException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 反射工具类
 *
 * @author wangxd
 * @date 2021-04-26
 */
public class ReflectUtils {

    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentReferenceHashMap(256);

    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * 获取类中所有属性字段（包括父类，子类父类都存在的字段，使用子类中的）
     * @return
     */
    public static Collection<Field> getDistinctFields(Class<?> cl) {
        Map<String, Field> fieldMap= new HashMap<>(16);

        for (Class<?> c = cl; c != null; c = c.getSuperclass()) {
            for(Field field : c.getDeclaredFields()) {
                fieldMap.putIfAbsent(field.getName(), field);
            }
        }

        return fieldMap.values();
    }


    /**
     * 根据注解类标注的字段
     */
    public static <T extends Annotation> List<Field> getFieldsByAnnotation(Class<?> cl, Class<T> annotationClass) {
        List<Field> fields = new ArrayList<>(1);
        for (Field field : ReflectUtils.getDistinctFields(cl)) {
            if(field.isAnnotationPresent(annotationClass)) {
                fields.add(field);
            }
        }
        return fields;
    }

    public static <T> T invoke(Object obj, String methodName) {
        return invoke(obj, methodName, EMPTY_OBJECT_ARRAY);
    }

    public static <T> T invoke(Object obj, String methodName, Object... args) {
        Class<?>[] argsClassArray = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
        Method method = findMethod(obj.getClass(), methodName, argsClassArray);
        if (null == method) {
            throw new YuQueryException(String.format("No such method: [%s]", methodName));
        } else {
            return (T) ReflectionUtils.invokeMethod(method, obj, args);
        }
    }

    @Nullable
    public static Method findMethod(Class<?> clazz, String name, @Nullable Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");

        for(Class searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType, false);
            Method[] var5 = methods;
            int var6 = methods.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
                if (name.equals(method.getName()) && (paramTypes == null || isAllAssignableFrom(method.getParameterTypes(), paramTypes))) {
                    return method;
                }
            }
        }

        return null;
    }

    /**
     * 比较判断types1和types2两组类，如果types1中所有的类都与types2对应位置的类相同，或者是其父类或接口，则返回{@code true}
     *
     * @param types1 类组1
     * @param types2 类组2
     * @return 是否相同、父类或接口
     */
    public static boolean isAllAssignableFrom(Class<?>[] types1, Class<?>[] types2) {
        if (ArrayUtils.isEmpty(types1) && ArrayUtils.isEmpty(types2)) {
            return true;
        }
        if (null == types1 || null == types2) {
            // 任何一个为null不相等（之前已判断两个都为null的情况）
            return false;
        }
        if (types1.length != types2.length) {
            return false;
        }

        Class<?> type1;
        Class<?> type2;
        for (int i = 0; i < types1.length; i++) {
            type1 = types1[i];
            type2 = types2[i];
            if (isBasicType(type1) && isBasicType(type2)) {
                // 原始类型和包装类型存在不一致情况
                if (BasicType.unWrap(type1) != BasicType.unWrap(type2)) {
                    return false;
                }
            } else if (false == type1.isAssignableFrom(type2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return 是否为基本类型
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return BasicType.WRAPPER_PRIMITIVE_MAP.containsKey(clazz);
    }

    private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
        Assert.notNull(clazz, "Class must not be null");
        Method[] result = (Method[])declaredMethodsCache.get(clazz);
        if (result == null) {
            try {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
                if (defaultMethods != null) {
                    result = new Method[declaredMethods.length + defaultMethods.size()];
                    System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                    int index = declaredMethods.length;

                    for(Iterator var6 = defaultMethods.iterator(); var6.hasNext(); ++index) {
                        Method defaultMethod = (Method)var6.next();
                        result[index] = defaultMethod;
                    }
                } else {
                    result = declaredMethods;
                }

                declaredMethodsCache.put(clazz, result.length == 0 ? EMPTY_METHOD_ARRAY : result);
            } catch (Throwable var8) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", var8);
            }
        }

        return result.length != 0 && defensive ? (Method[])result.clone() : result;
    }

    @Nullable
    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        Class[] var2 = clazz.getInterfaces();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Class<?> ifc = var2[var4];
            Method[] var6 = ifc.getMethods();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Method ifcMethod = var6[var8];
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList();
                    }

                    result.add(ifcMethod);
                }
            }
        }

        return result;
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
