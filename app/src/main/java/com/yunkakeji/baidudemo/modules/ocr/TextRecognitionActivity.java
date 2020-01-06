package com.yunkakeji.baidudemo.modules.ocr;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.base.Contact;
import com.yunkakeji.baidudemo.base.activity.BaseActivity;
import com.yunkakeji.baidudemo.databinding.ActivityTextRecognitionBinding;
import com.yunkakeji.baidudemo.modules.adapter.BaseAdapter;
import com.yunkakeji.baidudemo.modules.bean.HomeData;
import com.yunkakeji.baidudemo.net.dataModel.ocr.ORCViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.ResponseBody;

/**
 * Created by Administrator
 * on 2020/1/2 11:25
 * 百度文字识别
 */
public class TextRecognitionActivity extends BaseActivity {

    private ActivityTextRecognitionBinding mBD;

    private BaseAdapter adapter;

    private ORCViewModel orcViewModel;

    private String accessToken = null;

    private Class[]activityClass=new Class[]{IDCardActivity.class,ORCCommonActivity.class};

    @Override
    protected void initView() {
        orcViewModel = ViewModelProviders.of(this).get(ORCViewModel.class);
        mBD = DataBindingUtil.setContentView(this, R.layout.activity_text_recognition);

        mBD.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseAdapter(new ArrayList<HomeData>());
        mBD.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.setClass(TextRecognitionActivity.this,((HomeData)adapter.getData().get(position)).getActivity());
                intent.putExtra("Token",accessToken);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

        String[] textOrcs = getResources().getStringArray(R.array.TextRec);
        for (String temp : textOrcs) {
            adapter.getData().add(new HomeData(temp, ORCCommonActivity.class));
        }
        adapter.notifyDataSetChanged();

        for (int i=0;i<activityClass.length;i++){
            adapter.getData().get(i).setActivity(activityClass[i]);
        }

        orcViewModel.getAccessToken().observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                dismiss();
                if (responseBody == null) {
                    ToastUtils.showShort("请求失败！");
                    return;
                }
                try {

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                         accessToken = jsonObject.getString("access_token");
                        mBD.tvMessage.setText(accessToken);
                    } catch (JSONException e) {

                        ToastUtils.showShort(e.getMessage());
                    }

                } catch (IOException e) {
                    ToastUtils.showShort(e.getMessage());
                }
            }
        });


        getBaiDuAccessToken();
    }

    private void getBaiDuAccessToken() {

        show("正在请求...");
        orcViewModel.postAccessToken(Contact.grant_typ, Contact.client_id, Contact.client_secret);
    }
}
