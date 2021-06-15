package com.itfyme.ecommerce.activity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.itfyme.ecommerce.R;
import com.itfyme.ecommerce.dbservices.SubCategoryService;
import com.itfyme.ecommerce.helpers.LayoutUtility;
import com.itfyme.ecommerce.interfaces.ResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class SubCategoryActivity extends BaseActivity  {
    JSONArray subCategoryArr;
    RecyclerView recyclerView;


    SubCategoryActivity.SubCategoryAdapter subCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceSubCategory) {
        try {
            super.onCreate(savedInstanceSubCategory);
            setContentView(R.layout.activity_sub_category);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            recyclerView=findViewById(R.id.subcategorylist);

            if(getIntent().hasExtra("response_object")){
                String info = getIntent().getStringExtra("response_object");
                JSONObject dataObj= new JSONObject(info);
                //converting arr into json array
                subCategoryArr =  dataObj.optJSONArray("SubCategories");
            }

//            initDataSet();
            initGridView();
            showListView();
            //getMenuList();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    //SubCategories

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


//refresh page

    private void showListView() {
        try {
            subCategoryAdapter.setData(subCategoryArr);
            subCategoryAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // initializing data set
//    private void initDataSet() {
//        subCategoryArr = new JSONArray();
//
//    }
    private void initGridView() {
        try {
            subCategoryAdapter = new SubCategoryAdapter(subCategoryArr);
            recyclerView.setHasFixedSize(true);
            GridLayoutManager maneger=new GridLayoutManager(this,3);
            recyclerView.setLayoutManager(maneger);
            recyclerView.setAdapter(subCategoryAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    // adapter class
    private class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
        private JSONArray dataSource;

        public SubCategoryAdapter(JSONArray listdata) {
            this.dataSource = listdata;
        }
        public void setData(JSONArray listdata) {
            this.dataSource = listdata;
        }
        @Override
        public SubCategoryActivity.SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.template_sub_category, parent, false);
            SubCategoryActivity.SubCategoryAdapter.ViewHolder

                    viewHolder = new ViewHolder(listItem);
            return viewHolder;

        }
        @Override
        public void onBindViewHolder(SubCategoryActivity.SubCategoryAdapter.ViewHolder holder, int position) {
            try {

                JSONObject obj = dataSource.optJSONObject(position);
                String name=obj.optString("Name");
                holder.txtName.setText(name);
                String imgimage=obj.optString("ImageURL");
                LayoutUtility.setImageByUrl(SubCategoryActivity.this,holder.imgImage,imgimage);
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),ProductListActivity.class);
                        intent.putExtra("response_object" ,obj.toString());
                        Log.d("",obj.toString());
                        startActivity(intent);
                    }
                });
//

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public int getItemCount() {

            return dataSource.length();

        }
        private class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtName;
            public ImageView imgImage;
            public LinearLayout linearLayout;
            public ViewHolder(View itemView) {
                super(itemView);
                this.txtName = (TextView) itemView.findViewById(R.id.txtSubCategoryName);
                this.imgImage = (ImageView) itemView.findViewById(R.id.imgSubCategoryimage);
                linearLayout  = (LinearLayout) itemView.findViewById(R.id.subcategorylayout);

            }
        }
    }






}