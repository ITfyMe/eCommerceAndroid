package com.itfyme.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.itfyme.ecommerce.R;
import com.itfyme.ecommerce.controller.AppController;
import com.itfyme.ecommerce.helpers.LayoutUtility;

public class BaseActivity extends AppCompatActivity {
    String mActivityName="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            mActivityName = this.getLocalClassName();
         //   LayoutUtility.setImageByUrl(this,imgView,url);
            AppController.getInstance().setActivityTag(mActivityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        try {
            super.onStop();
            AppController.getInstance().cancelPendingRequests(mActivityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            outState.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent();
            setResult(AppCompatActivity.RESULT_CANCELED, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}