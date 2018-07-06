package jingwei027.com.core.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public abstract class BaseTest {

  protected <T> void privateConstructor(Class<T> clazz) throws Exception
  {
    final Constructor constructor = clazz.getDeclaredConstructor();
    assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    assertThrows(Exception.class, () -> {
      constructor.setAccessible(true);
      constructor.newInstance();
    });
  }

}
