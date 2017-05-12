package in.srain.cube.views.ptr.util;

import android.content.Context;


import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.R;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by yzy on 2016/ic_01/15.
 */
public class PtrInitHelper {

    public static void initPtr(Context mContext,final PtrFrameLayout mPtrFrameLayout){
        final MaterialHeader header = new MaterialHeader(mContext);
        int[] colors = mContext.getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, dp2px(mContext, 30), 0,dp2px(mContext, 20));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(100);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.disableWhenHorizontalMove(true);

    }


    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal)
    {
		/*return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());*/
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }
}
