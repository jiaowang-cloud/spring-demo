package yooo.yun.com.common.utils;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
public final class StringUtils {
  public static final String SPACE = " ";
  public static final String EMPTY = "";
  public static final String LF = "\n";
  public static final String CR = "\r";
  public static final int INDEX_NOT_FOUND = -1;
  private static final int PAD_LIMIT = 8192;

  public StringUtils() {}

  public static boolean isEmpty(CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  public static boolean isNotEmpty(CharSequence cs) {
    return !isEmpty(cs);
  }

  public static boolean isAnyEmpty(CharSequence... css) {
    if (ArrayUtils.isEmpty(css)) {
      return false;
    } else {
      CharSequence[] arr$ = css;
      int len$ = css.length;

      for (int i$ = 0; i$ < len$; ++i$) {
        CharSequence cs = arr$[i$];
        if (isEmpty(cs)) {
          return true;
        }
      }

      return false;
    }
  }

  public static String[] split(String str, String separatorChars) {
    return splitWorker(str, separatorChars, -1, false);
  }

  private static String[] splitWorker(
      String str, String separatorChars, int max, boolean preserveAllTokens) {
    if (str == null) {
      return null;
    } else {
      int len = str.length();
      if (len == 0) {
        return ArrayUtils.EMPTY_STRING_ARRAY;
      } else {
        List<String> list = new ArrayList();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars != null) {
          if (separatorChars.length() != 1) {
            label87:
            while (true) {
              while (true) {
                if (i >= len) {
                  break label87;
                }

                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                  if (match || preserveAllTokens) {
                    lastMatch = true;
                    if (sizePlus1++ == max) {
                      i = len;
                      lastMatch = false;
                    }

                    list.add(str.substring(start, i));
                    match = false;
                  }

                  ++i;
                  start = i;
                } else {
                  lastMatch = false;
                  match = true;
                  ++i;
                }
              }
            }
          } else {
            char sep = separatorChars.charAt(0);

            label71:
            while (true) {
              while (true) {
                if (i >= len) {
                  break label71;
                }

                if (str.charAt(i) == sep) {
                  if (match || preserveAllTokens) {
                    lastMatch = true;
                    if (sizePlus1++ == max) {
                      i = len;
                      lastMatch = false;
                    }

                    list.add(str.substring(start, i));
                    match = false;
                  }

                  ++i;
                  start = i;
                } else {
                  lastMatch = false;
                  match = true;
                  ++i;
                }
              }
            }
          }
        } else {
          label103:
          while (true) {
            while (true) {
              if (i >= len) {
                break label103;
              }

              if (Character.isWhitespace(str.charAt(i))) {
                if (match || preserveAllTokens) {
                  lastMatch = true;
                  if (sizePlus1++ == max) {
                    i = len;
                    lastMatch = false;
                  }

                  list.add(str.substring(start, i));
                  match = false;
                }

                ++i;
                start = i;
              } else {
                lastMatch = false;
                match = true;
                ++i;
              }
            }
          }
        }

        if (match || preserveAllTokens && lastMatch) {
          list.add(str.substring(start, i));
        }

        return (String[]) list.toArray(new String[list.size()]);
      }
    }
  }

  public static int[] toCodePoints(CharSequence str) {
    if (str == null) {
      return null;
    } else if (str.length() == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      String s = str.toString();
      int[] result = new int[s.codePointCount(0, s.length())];
      int index = 0;

      for(int i = 0; i < result.length; ++i) {
        result[i] = s.codePointAt(index);
        index += Character.charCount(result[i]);
      }

      return result;
    }
  }

}
