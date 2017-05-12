package com.kupurui.cjhp.ui.postit;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.crop.CropHandler;
import com.android.frame.crop.CropHelper;
import com.android.frame.crop.CropParams;
import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseFragment;
import com.android.frame.util.Toolkit;
import com.android.frame.view.dialog.FormBotomDialogBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.Repair;
import com.kupurui.cjhp.ui.complaint.ComplaintFgt;
import com.kupurui.cjhp.ui.index.IndexPostitAty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 报事报修
 * Created by Administrator on 2017/4/18.
 */

public class PostitFgt extends BaseFragment implements HttpListener,CropHandler {

    @Bind(R.id.img_add_pic)
    ImageView imgAddPic;
    @Bind(R.id.edit_title)
    EditText eTTitle;
    @Bind(R.id.edit_content)
    EditText etContent;
    @Bind(R.id.edit_phone)
    EditText eTPhone;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.img_pic_1)
    SimpleDraweeView simpleDraweeView1;
    @Bind(R.id.img_pic_2)
    SimpleDraweeView simpleDraweeView2;
    @Bind(R.id.img_pic_3)
    SimpleDraweeView simpleDraweeView3;
    @Bind(R.id.tv_check_contentSize)
    TextView tvCheckContentSize;

    private ComplaintFgt complaintFgt;
    private String u_id = null;

    private File file;
    CropParams cropParams;

    private List<File> files = new ArrayList<>();
    private List<Uri> uris = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.post_fgt;
    }

    @Override
    public void initData() {
        u_id = UserManager.getUserInfo().getU_id();
        complaintFgt = new ComplaintFgt();
        cropParams = new CropParams(getActivity());
        cropParams.outputY = 300;
        cropParams.outputX = 300;

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                tvCheckContentSize.setText("您已输入"+s.length()+"字");
            }
        });
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_add_pic,R.id.btn_submit})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_add_pic:
                showdialog();
                break;
            case R.id.btn_submit:
                String title = eTTitle.getText().toString();
                String content = etContent.getText().toString();
                String phone = eTPhone.getText().toString();
                if (TextUtils.isEmpty(title)){
                    showToast("请输入标题");
                    return;
                }
                if (TextUtils.isEmpty(content)){
                    showToast("请输入内容");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    showToast("请输入电话");
                    return;
                }
                if (!Toolkit.isMobile(phone)){
                    showToast("请输入正确电话");
                    return;
                }
                showLoadingDialog("");
                new Repair().index(u_id,title,content,phone,files,this,1);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        showToast("上传成功");
        IndexPostitAty activity = (IndexPostitAty) getActivity();
        activity.setTabSelection(1);
        super.onSuccess(result, call, response, what);
    }

    private void showdialog(){
        final FormBotomDialogBuilder photoDialog = new FormBotomDialogBuilder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.postit_picture_bottom_dialog,null);
        photoDialog.setFB_AddCustomView(view);
        photoDialog.show();
        photoDialog.getWindow().findViewById(R.id.text_photograph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                cropParams.enable = true;
                cropParams.compress = true;
                cropParams.refreshUri();
                Intent intent1 = CropHelper.buildCameraIntent(cropParams);
                startActivityForResult(intent1, CropHelper.REQUEST_CAMERA);
                photoDialog.dismiss();
            }
        });
        photoDialog.getWindow().findViewById(R.id.text_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                cropParams.enable = true;
                cropParams.compress = true;
                cropParams.refreshUri();
                Intent intent2 = CropHelper.buildGalleryIntent(cropParams);
                startActivityForResult(intent2, CropHelper.REQUEST_CROP);
                photoDialog.dismiss();
            }
        });
        photoDialog.getWindow().findViewById(R.id.text_pic_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                photoDialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    //拍照
    @Override
    public void onPhotoCropped(Uri uri) {
        file = new File(uri.getPath());
        if (files.size() == 0){
            files.add(0,file);
            uris.add(0,uri);
            simpleDraweeView1.setVisibility(View.VISIBLE);
            simpleDraweeView1.setImageURI(uris.get(0));
        }else if(files.size() == 1){
            files.add(1,file);
            uris.add(1,uri);
            simpleDraweeView1.setVisibility(View.VISIBLE);
            simpleDraweeView2.setVisibility(View.VISIBLE);
            simpleDraweeView1.setImageURI(uris.get(0));
            simpleDraweeView2.setImageURI(uris.get(1));
        }else{
            files.add(2,file);
            uris.add(2,uri);
            simpleDraweeView1.setVisibility(View.VISIBLE);
            simpleDraweeView2.setVisibility(View.VISIBLE);
            simpleDraweeView3.setVisibility(View.VISIBLE);
            simpleDraweeView1.setImageURI(uris.get(0));
            simpleDraweeView2.setImageURI(uris.get(1));
            simpleDraweeView3.setImageURI(uris.get(2));
        }
    }

    //相册
    @Override
    public void onCompressed(Uri uri) {
        file = new File(uri.getPath());
        if (files.size() == 0){
            files.add(0,file);
            uris.add(0,uri);
            simpleDraweeView1.setVisibility(View.VISIBLE);
            simpleDraweeView1.setImageURI(uris.get(0));
        }else if(files.size() == 1){
            files.add(1,file);
            uris.add(1,uri);
            simpleDraweeView1.setVisibility(View.VISIBLE);
            simpleDraweeView2.setVisibility(View.VISIBLE);
            simpleDraweeView1.setImageURI(uris.get(0));
            simpleDraweeView2.setImageURI(uris.get(1));
        }else{
            files.add(2,file);
            uris.add(2,uri);
            simpleDraweeView1.setVisibility(View.VISIBLE);
            simpleDraweeView2.setVisibility(View.VISIBLE);
            simpleDraweeView3.setVisibility(View.VISIBLE);
            simpleDraweeView1.setImageURI(uris.get(0));
            simpleDraweeView2.setImageURI(uris.get(1));
            simpleDraweeView3.setImageURI(uris.get(2));
        }
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onFailed(String message) {
        showToast("失败:" + message);
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public CropParams getCropParams() {
        return cropParams;
    }

    @Override
    public void onDestroyView() {
        CropHelper.clearCacheDir();
        super.onDestroyView();
        if (file != null) {
            file.delete();
        }
    }

}
