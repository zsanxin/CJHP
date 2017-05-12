package com.kupurui.cjhp.utils;

import java.util.List;

/**
 * 文字判断工具
 * <p>
 * Created by Administrator on 2017/4/6.
 */
public class StringUtil {

    /**
     * 是否不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s.trim()) || s.equals("null");
    }

    /**
     * 是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim()) || s.equals("null");
    }

    /**
     * list 数据是否不为空
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 通过{n},格式化.
     *
     * @param src
     * @param objects
     * @return
     */
    public static String format(String src, Object... objects) {
        int k = 0;
        for (Object obj : objects) {
            src = src.replace("{" + k + "}", obj.toString());
            k++;
        }
        return src;
    }

}
