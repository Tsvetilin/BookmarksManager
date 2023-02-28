package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private static final Map<String, Object> SINGLETON_CONTAINER;
    private static final Map<String, Map.Entry<Constructor, Class[]>> TRANSIENT_CONTAINER;

    static {
        SINGLETON_CONTAINER = new HashMap<>();
        TRANSIENT_CONTAINER = new HashMap<>();
    }

    private DIContainer() {
    }

    public static <T> T request(Class<T> tClass) {
        if (SINGLETON_CONTAINER.containsKey(tClass.getName())) {
            return (T) SINGLETON_CONTAINER.get(tClass.getName());
        }

        if (TRANSIENT_CONTAINER.containsKey(tClass.getName())) {
            var entry = TRANSIENT_CONTAINER.get(tClass.getName());
            try {
                var args = Arrays
                    .stream(entry.getValue())
                    .map(DIContainer::request)
                    .toList();
                var constructor = (Constructor<T>) entry.getKey();
                return constructor.newInstance(args);
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                throw new IllegalArgumentException("Not such service registered to comply with the request.", e);
            }
        }

        throw new IllegalArgumentException("Not such service registered");
    }

    public static void registerSingleton(Type type, Service object) {

        Type[] interfaces = object.getClass().getInterfaces();
        if (Arrays.stream(interfaces).noneMatch(x -> x == type)) {
            throw new RuntimeException("Cannot add mismatching type definitions.");
        }

        SINGLETON_CONTAINER.put(type.getTypeName(), object);
    }

    public static void registerUnchecked(Type type, Object object) {

        Type[] interfaces = object.getClass().getInterfaces();
        if (Arrays.stream(interfaces).noneMatch(x -> x == type)) {
            throw new RuntimeException("Cannot add mismatching type definitions.");
        }

        SINGLETON_CONTAINER.put(type.getTypeName(), object);
    }

    public static void registerTransient(Type registerType, Type implementationType, Class[] argTypeList) {

        Type[] interfaces = implementationType.getClass().getInterfaces();
        if (Arrays.stream(interfaces).noneMatch(x -> x == Service.class)) {
            throw new RuntimeException("Cannot add non-service type definitions.");
        }

        try {
            TRANSIENT_CONTAINER.put(registerType.getTypeName(),
                Map.entry(implementationType.getClass().getConstructor(argTypeList), argTypeList));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Invalid constructor specified.");
        }
    }

    public static void clear() {
        TRANSIENT_CONTAINER.clear();
        SINGLETON_CONTAINER.clear();
    }
}
