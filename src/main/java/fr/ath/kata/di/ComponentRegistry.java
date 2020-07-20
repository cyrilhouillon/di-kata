package fr.ath.kata.di;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ComponentRegistry {

    boolean dependenciesInjected = false;
    Map<Class, Object> registry = new HashMap<>();

    public void register(Class componentClass) {
        try {
            registry.put(componentClass, instanciate(componentClass));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Error while instanciate "+componentClass, e);
        }
    }

    public <T> T fetch(Class<T> componentClass) {
        if(!dependenciesInjected){
            injectDependencies();
        }
        return (T) registry.get(componentClass);
    }

    private <T> T instanciate(Class<T> componentClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return componentClass.getDeclaredConstructor().newInstance();
    }

    private void injectDependencies() {

        registry.keySet().forEach(this::injectDependencies);
        dependenciesInjected = true;
    }

    private void injectDependencies(Class componentClass) {
        Stream.of(componentClass.getMethods())
                .filter(m -> m.getName().startsWith("set"))
                .forEach(this::applySetter);
    }

    private void applySetter(Method setter) {
        try {
            Object fieldToInject = getFieldToInject(setter);
            if(fieldToInject == null){
                throw new RuntimeException("Missing depencie : " + setter.getParameterTypes()[0] + " needed for bean " + setter.getDeclaringClass());
            }
            setter.invoke(getBean(setter), fieldToInject);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw  new RuntimeException("Unable to apply setter " + setter.toString(), e);
        }
    }

    private Object getFieldToInject(Method setter) {
        return registry.get(setter.getParameterTypes()[0]);
    }

    private Object getBean(Method setter) {
        return registry.get(setter.getDeclaringClass());
    }

}
