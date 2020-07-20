package fr.ath.kata.di;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ComponentRegistry {

    Map<Class, Object> registry = new HashMap<>();

    public void register(Class componentClass) {
        try {
            registry.put(componentClass, instanciate(componentClass));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Error while instanciate "+componentClass, e);
        }
    }

    public <T> T fetch(Class<T> componentClass) {
        return (T) registry.get(componentClass);
    }

    private <T> T instanciate(Class<T> componentClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return componentClass.getDeclaredConstructor().newInstance();
    }

}
