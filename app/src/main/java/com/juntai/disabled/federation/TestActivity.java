package com.juntai.disabled.federation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosBusinessFragment;

public class TestActivity extends AppCompatActivity  implements  SelectPhotosBusinessFragment.OnPhotoItemClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }
}
