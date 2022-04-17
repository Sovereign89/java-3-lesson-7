package com.geekbrains.test;

import com.geekbrains.annotations.*;
import com.geekbrains.exceptions.FailedToInvokeMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class TestAnnotations {

    private Object classInstance;

    public void RunTests(Class<?> classInstance) {

        try {
            this.classInstance = classInstance.getDeclaredConstructor().newInstance();
        } catch (InstantiationException|IllegalAccessException|NoSuchMethodException|InvocationTargetException e) {
            throw new RuntimeException("Не удалось создать экземпляр класса");
        }

        Method[] methods = classInstance.getDeclaredMethods();

        if (!checkIntegrity(methods,BeforeSuite.class) || !checkIntegrity(methods,AfterSuite.class)) {
            throw new RuntimeException("Методы с аннотациями BeforeSuite/AfterSuite должны быть в единственном экзепляре");
        }

        try {
            invokeMethod(methods, BeforeSuite.class);
            invokeSortedMethods(methods);
            invokeMethod(methods, AfterSuite.class);
        } catch (FailedToInvokeMethod e) {
            System.out.println(e);
        }
    }

    private boolean checkIntegrity(Method[] methods, Class<? extends Annotation> annotation) {
        int count = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                count++;
            }
        }
        return count <= 1;
    }

    private void invokeMethod(Method[] methods, Class<? extends Annotation> annotation) throws FailedToInvokeMethod {
        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(annotation)) {
                    method.invoke(classInstance);
                }
            } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
                throw new FailedToInvokeMethod("Не удалось запустить метод \""
                        + method.getName() + "\" в классе \""
                        + classInstance.getClass().getName() + "\"");
            }
        }
    }

    private void invokeSortedMethods(Method[] methods) {
        Comparator<Method> comparator = Comparator.comparingInt(m -> m.getAnnotation(Priority.class).value());
        Arrays.stream(methods)
              .filter(method -> method.getAnnotation(Priority.class) != null)
              .sorted(comparator)
              .forEach(method -> {
                  try {
                      method.invoke(classInstance);
                  } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
                      e.printStackTrace();
                  }
              });
    }

}
