package jingwei027.com.helper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jingwei027.com.annotation.NeedEmbed;
import jingwei027.com.dto.GenericDto;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ClassUtils;

@UtilityClass
public class EmbedCleaner {

  public static <T extends GenericDto> T clean(
    String embedQueryString,
    @NonNull T dto)
  {
    recursive(new StringBuffer(), dto, EmbedParser.parseToKeySet(embedQueryString));
    return dto;
  }

  public static <T extends GenericDto> List<T> clean(
    String embedQueryString,
    @NonNull List<T> list)
  {
    recursive(new StringBuffer(), list, EmbedParser.parseToKeySet(embedQueryString));
    return list;
  }

  private static void appendOneLevel(
    @NonNull StringBuffer prefix,
    @NonNull String name)
  {
    prefix.append(name).append(".");
  }

  private static void clearNotEmbedField(
    @NonNull Field field,
    Object obj)
  {
    if (obj != null) {
      safeSetObject(field, obj, null);
    }
  }

  private static Class<?> extractParameterizedType(
    @NonNull Field field)
  {
    return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
  }

  private static boolean filterAttentionField(
    @NonNull Field field)
  {
    if (!isAnonymousInnerClass(field) && (hasAnnotated(field) || isGenericDto(field.getType()) || isGenericDtoCollection(field))) {
      return true;
    }
    else {
      return false;
    }
  }

  private static boolean hasAnnotated(
    @NonNull Field field)
  {
    return field.getAnnotation(NeedEmbed.class) != null;
  }

  private static boolean isAnonymousInnerClass(
    @NonNull Field field)
  {
    return field.getName().startsWith("this$");
  }

  private static boolean isCollection(
    @NonNull Class<?> clazz)
  {
    return Collection.class.isAssignableFrom(clazz);
  }

  private static boolean isEmbed(
    @NonNull String embedKey,
    @NonNull Set<String> embedKeySet)
  {
    return embedKeySet.contains(embedKey);
  }

  private static boolean isGenericDto(
    @NonNull Class<?> clazz)
  {
    return ClassUtils.getAllInterfaces(clazz).contains(GenericDto.class);
  }

  private static boolean isGenericDtoCollection(
    @NonNull Field field)
  {
    return Stream.of(field)
      .filter(f -> isCollection(f.getType()))
      .filter(f -> isParameterizedType(f.getGenericType().getClass()))
      .filter(f -> isGenericDto(extractParameterizedType(field)))
      .findAny().isPresent();
  }

  private static boolean isParameterizedType(
    @NonNull Class<?> clazz)
  {
    return ParameterizedType.class.isAssignableFrom(clazz);
  }

  private static List<Field> listAttentionFields(
    @NonNull Class<?> dto)
  {
    return new ArrayList<Class<?>>() {{
      addAll(ClassUtils.getAllSuperclasses(dto));
      add(dto);
    }}.stream()
      .flatMap(clazz -> Arrays.asList(clazz.getDeclaredFields()).stream())
      .filter(field -> filterAttentionField(field))
      .collect(Collectors.toList());
  }

  private static String makeEmbedKey(
    @NonNull StringBuffer prefix,
    @NonNull String fieldName)
  {
    return prefix + fieldName;
  }

  private static void recursive(
    @NonNull StringBuffer prefix,
    @NonNull List<? extends GenericDto> list,
    @NonNull Set<String> embedKeySet)
  {
    if (list != null) {
      for (GenericDto dto : list) {
        recursive(prefix, dto, embedKeySet);
      }
    }
  }

  private static void recursive(
    @NonNull StringBuffer prefix,
    @NonNull GenericDto dto,
    @NonNull Set<String> embedKeySet)
  {
    trace("***** " + dto.toString());
    final List<Field> fields = listAttentionFields(dto.getClass());
    fields.forEach(f -> trace(f));
    trace("");
    trace("");

    for (Field field : fields) {
      if (isCollection(field.getType()) && safeGetObject(field, dto) != null) {
        final String embedKey = makeEmbedKey(prefix, field.getName()); // key : collection
        if (isEmbed(embedKey, embedKeySet)) {
          appendOneLevel(prefix, field.getName());
          final Iterator it = ((Collection) safeGetObject(field, dto)).iterator();
          while (it.hasNext()) {
            recursive(prefix, (GenericDto) it.next(), embedKeySet);
          }
          removeOneLevel(prefix);
        }
        else {
          clearNotEmbedField(field, dto);
        }
      }
      else if (isGenericDto(field.getType()) && safeGetObject(field, dto) != null) {
        final String embedKey = makeEmbedKey(prefix, field.getName()); // key : GenericDto
        if (isEmbed(embedKey, embedKeySet)) {
          appendOneLevel(prefix, field.getName());
          recursive(prefix, (GenericDto) safeGetObject(field, dto), embedKeySet);
          removeOneLevel(prefix);
        }
        else {
          clearNotEmbedField(field, dto);
        }
      }
      else {
        final String embedKey = makeEmbedKey(prefix, field.getName()); // key : Non-GenericDto
        if (!isEmbed(embedKey, embedKeySet)) {
          clearNotEmbedField(field, dto);
        }
      }
    }
  }

  private static void removeOneLevel(
    @NonNull StringBuffer prefix)
  {
    if (prefix.length() > 0) {
      prefix.deleteCharAt(prefix.length() - 1);
    }
    if (prefix.lastIndexOf(".") == -1) {
      prefix.delete(0, prefix.length()); // clear
    }
    else {
      prefix.delete(prefix.lastIndexOf(".") + 1, prefix.length());
    }
  }

  @SneakyThrows
  private static Object safeGetObject(
    @NonNull Field field,
    @NonNull Object obj)
  {
    field.setAccessible(true);
    return field.get(obj);
  }

  @SneakyThrows
  private static void safeSetObject(
    @NonNull Field field,
    @NonNull Object obj,
    Object value)
  {
    field.setAccessible(true);
    field.set(obj, value);
  }

  private static void trace(Object any)
  {
//    System.out.println(any);
  }

}
