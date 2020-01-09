package com.yunkakeji.baidudemo.modules.ocr;

import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.base.activity.BaseActivity;
import com.yunkakeji.baidudemo.databinding.ActivityOrcCommonBinding;
import com.yunkakeji.baidudemo.net.dataModel.ocr.ORCViewModel;

import java.io.IOException;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.ResponseBody;

/**
 * Created by Administrator
 * on 2020/1/6 15:42
 * 通用文字识别
 */
public class ORCCommonActivity extends BaseActivity {

    private ActivityOrcCommonBinding mBD;

    private ORCViewModel orcViewModel = null;


    private String accessToken = null;

    private String textImageURL = "http://pic.qqtn.com/up/2017-9/2017091915231445357.jpg";


    @Override
    protected void initView() {

        mBD = DataBindingUtil.setContentView(this, R.layout.activity_orc_common);

        orcViewModel= ViewModelProviders.of(this).get(ORCViewModel.class);

        accessToken=getIntent().getStringExtra("Token");

        mBD.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessToken==null){
                    ToastUtils.showShort("token不能为空");
                    return;
                }
                textImageURL=mBD.edtImageUrl.getText().toString();
                if (TextUtils.isEmpty(textImageURL)){
                    ToastUtils.showShort("请求输入文字图片URL");
                    return;
                }
                textImageURL=mBD.edtImageUrl.getText().toString();
                getTextInfo();
            }
        });

        mBD.edtImageUrl.setText(textImageURL);
    }

    @Override
    protected void initData() {

        orcViewModel.getTextImageURL().observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                dismiss();
                if (responseBody==null){
                    ToastUtils.showShort("请求失败!");
                    return;
                }
                try {
                    mBD.tvMessage.setText(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        getTextInfo();
    }

    private void getTextInfo(){
        show("正在请求...");
        orcViewModel.postImageTextURL(accessToken,textImageURL);
    }
}
