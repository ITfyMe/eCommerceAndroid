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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.itfyme.ecommerce.R;
import com.itfyme.ecommerce.dbservices.CategoryService;
import com.itfyme.ecommerce.helpers.LayoutUtility;
import com.itfyme.ecommerce.interfaces.ResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class LandingPageActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
         JSONArray categoryArr;
         RecyclerView recyclerView;
        LandingPageActivity.CategoryAdapter categoryAdapter;
@Override
protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView=findViewById(R.id.CategoryList);
                initDataSet();
                initGridView();
                getMenuList();
        }catch (Exception e ){
                e.printStackTrace();
        }
        }


@Override
public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
        } else {
        super.onBackPressed();
        }
        }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
        }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
                Intent intent =new Intent(LandingPageActivity.this,SearchActivity.class);
                startActivity(intent);
        return true;
        }else if (id == R.id.menu_cart) {
                Intent intent =new Intent(LandingPageActivity.this,MyCartActivity.class);
                startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
}
@SuppressWarnings("StatementWithEmptyBody")
@Override
public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_order) {
                Intent intent =new Intent(LandingPageActivity.this,MyOrderActivity.class);
                startActivity(intent);
        // Handle the Camer action
        } else if (id == R.id.nav_account) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }

//refresh page

        private void showListView() {
                try {
                        categoryAdapter.setData(categoryArr);
                        categoryAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

private void getMenuList(){
        try {


        HashMap<String,String> mapList=new HashMap<>();
        new CategoryService(this).getMenuList(mapList, new ResponseHandler() {
                @Override
                public void onSuccess(Object data) {
                        try {
                                //converting array into json array
                              categoryArr=new JSONArray(data.toString());
//                                JSONObject obj=new JSONObject(data.toString());
//                                categoryArr=obj.optJSONArray("response_object");

                                showListView();
                        }catch (Exception e){
                                e.printStackTrace();
                        }


                }

                @Override
                public void onFail(Object data) {
                        Log.d("","");
                }

                @Override
                public void onNoData(Object data) {

                }
        });}catch (Exception e){
                e.printStackTrace();
        }
}


        // initializing data set
        private void initDataSet() {
                categoryArr = new JSONArray();

        }
        private void initGridView() {
        try {
                categoryAdapter = new CategoryAdapter(categoryArr);
                recyclerView.setHasFixedSize(true);
                GridLayoutManager maneger=new GridLayoutManager(this,3);
                recyclerView.setLayoutManager(maneger);
                recyclerView.setAdapter(categoryAdapter);
        }catch (Exception e){
                e.printStackTrace();
        }


        }
        // adapter class
        private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
                private JSONArray dataSource;

                public CategoryAdapter(JSONArray listdata) {
                        this.dataSource = listdata;
                }
                public void setData(JSONArray listdata) {
                        this.dataSource = listdata;
                }
                @Override
                public LandingPageActivity.CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                        View listItem = layoutInflater.inflate(R.layout.template_content_main, parent, false);
                        LandingPageActivity.CategoryAdapter.ViewHolder

                                viewHolder = new ViewHolder(listItem);
                        return viewHolder;

                }
                @Override
                        public void onBindViewHolder(LandingPageActivity.CategoryAdapter.ViewHolder holder, int position) {
                                try {

                                        JSONObject obj = dataSource.optJSONObject(position);
                                        String name=obj.optString("Name");
//                                       String image=obj.optString("ImageURL");
                                        holder.txtName.setText(name);
                                        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                        Intent intent = new Intent(getApplicationContext(),SubCategoryActivity.class);
                                                        intent.putExtra("response_object",obj.toString());
                                                        Log.d("",obj.toString());
                                                        startActivity(intent);



                                                }
                                        });
//
//                                        LayoutUtility.setImageByUrl(LandingPageActivity.this,holder.imgImage,url);
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
                                this.txtName = (TextView) itemView.findViewById(R.id.txtCategoryNme);
                                this.imgImage = (ImageView) itemView.findViewById(R.id.imgCategoryImage);
                                linearLayout  = (LinearLayout) itemView.findViewById(R.id.categoryLayout);

                        }
                }
        }






}