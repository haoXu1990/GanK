package com.xuhao.gank.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * <pre>
 *     author : xuhao
 *     time   : 2018/06/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ThemeUtils {
    public static int getThemeColor(Context context, int attrRes){

        TypedArray typedArray = context.obtainStyledAttributes(new int[]{attrRes});
        int color = typedArray.getColor(0, 0xffffff);
        typedArray.recycle();
        return color;
    }

}
