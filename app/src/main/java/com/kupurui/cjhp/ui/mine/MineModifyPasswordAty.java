package com.kupurui.cjhp.ui.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.User;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 修改密码
 * Created by Administrator on 2017/4/17.
 */

public class MineModifyPasswordAty extends BaseActivity implements HttpListener {

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_next)
    Button btn_next;
    @Bind(R.id.edit_old_pwd)
    EditText eToldPassword;
    @Bind(R.id.edit_new_pwd)
    EditText eTnewPassword;
    @Bind(R.id.edit_new_pwd_b)
    EditText eTnewPasswordb;

    @Override
    public int getLayoutId() {
        return R.layout.mine_modify_password_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.modify_password));
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        if (what == 1) {
            showToast("密码修改成功");
            finish();
        }
        super.onSuccess(result, call, response, what);

    }

    @OnClick({R.id.img_back, R.id.btn_next})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                String lod_password = eToldPassword.getText().toString();
                String new_password = eTnewPassword.getText().toString();
                String new_password_b = eTnewPasswordb.getText().toString();
                String u_id = UserManager.getUserInfo().getU_id();
                if (TextUtils.isEmpty(lod_password)) {
                    showToast("请输入原始密码");
                    return;
                }
                if (TextUtils.isEmpty(new_password)) {
                    showToast("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(new_password_b)) {
                    showToast("请输入确认新密码");
                    return;
                }
                showLoadingDialog("");
                new User().modifyPassword(u_id, lod_password, new_password, new_password_b, this, 1);
                break;
        }
    }


}
