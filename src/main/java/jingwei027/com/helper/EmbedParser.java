package jingwei027.com.helper;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * 將 URL ?embed 轉成階層
 * <pre>
 * ex-1 (目標物件為 Book) :
 *   description,vendor(addresses),authors(address(simpleAddress)),contentPreview
 *   ==>
 *   description
 *   vendor
 *   vendor.addresses
 *   authors.address
 *   authors
 *   authors.address.simpleAddress
 *   contentPreview
 * </pre>
 */
@UtilityClass
public class EmbedParser {

  private static final Pattern embedPattern = Pattern.compile("[a-zA-Z0-9 ,()]*");

  public static Set<String> parseToKeySet(
    String src)
  {
    if (src == null || src.length() == 0) {
      return new HashSet<>();
    }
    else {
      src = src.replaceAll(" ", "");
      verifyFormat(src);
    }

    return parseToKeySetInternal(src);
  }

  private static void appendOneLevel(
    @NonNull StringBuffer prefix,
    @NonNull StringBuffer tmpStr)
  {
    prefix.append(tmpStr).append(".");
  }

  private static void clear(
    @NonNull StringBuffer strBuf)
  {
    strBuf.delete(0, strBuf.length()); // clear
  }

  private Set<String> parseToKeySetInternal(
    @NonNull String src)
  {
    final Set<String> expandFields = new HashSet<>();
    final StringBuffer prefix = new StringBuffer();
    final StringBuffer tmpStr = new StringBuffer();

    for (int i = 0; i < src.length(); i++) {
      final char c = src.charAt(i);

      if (c != ',' && c != '(' && c != ')') {
        tmpStr.append(c);
      }
      else if (c == ',' && tmpStr.length() > 0) {
        expandFields.add(String.format("%s%s", prefix, tmpStr));
        clear(tmpStr);
      }
      else if (c == '(' && tmpStr.length() > 0) {
        expandFields.add(String.format("%s%s", prefix, tmpStr));
        appendOneLevel(prefix, tmpStr);
        clear(tmpStr);
      }
      else if (c == ')' && tmpStr.length() > 0) {
        expandFields.add(String.format("%s%s", prefix, tmpStr));
        removeOneLevel(prefix);
        clear(tmpStr);
      }
      else if (c == ')') {
        removeOneLevel(prefix);
      }
    }

    if (tmpStr.length() > 0) {
      expandFields.add(String.format("%s%s", prefix, tmpStr));
      clear(tmpStr);
      clear(prefix);
    }

    return expandFields;
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

  private void verifyFormat(
    @NonNull String str)
  {
    if (!embedPattern.matcher(str).matches()) {
      throw new RuntimeException("embed, format error");
    }
    else if (StringUtils.countMatches(str, '(') != StringUtils.countMatches(str, ')')) {
      throw new RuntimeException("embed, format error");
    }
  }

}
