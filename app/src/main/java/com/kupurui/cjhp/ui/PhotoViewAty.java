package com.kupurui.cjhp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;

import butterknife.Bind;
import butterknife.OnClick;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Administrator on 2017/5/9.
 */

public class PhotoViewAty extends BaseActivity{

    @Bind(R.id.photoview)
    PhotoDraweeView photoDraweeView;

    String uri = null;

    @Override
    public int getLayoutId() {
        return R.layout.photoview_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        uri = bundle.getString("uri");
        photoDraweeView.setPhotoUri(Uri.parse(uri));
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.photoview})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.photoview:
                finish();
                break;
        }
    }
}
