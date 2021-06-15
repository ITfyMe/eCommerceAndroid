package com.itfyme.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itfyme.ecommerce.R;
import com.itfyme.ecommerce.dbservices.ProductListService;
import com.itfyme.ecommerce.helpers.LayoutUtility;
import com.itfyme.ecommerce.helpers.NetworkUtility;
import com.itfyme.ecommerce.interfaces.ResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class ProductListActivity extends BaseActivity {
    RecyclerView recyclerView;
    TextView textView;
    JSONArray productArr;
    GridView gridView;
    private int pageNum=0;
    ProductAdapter productAdapter;
    String subCategoryId;
    @Override
    protected void onCreate(Bundle savedInstanceProduct) {
        try {
            super.onCreate(savedInstanceProduct);
            setContentView(R.layout.activity_product_list);
            recyclerView = (RecyclerView) findViewById(R.id.productlist);
            textView = (TextView) findViewById(R.id.txt);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if(getIntent().hasExtra("response_object")){
               String str = getIntent().getStringExtra("response_object");
                JSONObject dataObj= new JSONObject(str);
                subCategoryId=dataObj.optString("SubCategoryID");
            }

            initDataSet();
            initListView();
            getProductList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getProductList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("sub_cat_id", String.valueOf(subCategoryId));
        params.put("page_num", String.valueOf(pageNum));
        params.put("page_size", NetworkUtility.numOfRecords);
        new ProductListService(this).getProductList(params, new ResponseHandler() {
            @Override
            public void onSuccess(Object data) {
                try {
                    JSONObject obj = new JSONObject(data.toString());
//                    JSONObject pageObj = obj.optJSONObject("pages");
                    JSONArray arr = obj.optJSONArray("data");
                    productArr = NetworkUtility.mergeArray(productArr, arr);
                    showListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Object data) {

            }

            @Override
            public void onNoData(Object data) {

            }
        });
//
    }


    private void initDataSet(){
        productArr = new JSONArray();
    }
    private void initListView(){
        try {
            productAdapter = new ProductAdapter(productArr);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager maneger = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(maneger);
            recyclerView.setAdapter(productAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showListView() {
        try {
            productAdapter.setData(productArr);
            productAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // adapter class
    private class ProductAdapter extends RecyclerView.Adapter<ProductListActivity.ProductAdapter.ViewHolder> {
        private JSONArray dataSource;

        public ProductAdapter(JSONArray listdata) {
            this.dataSource = listdata;
        }
        public void setData(JSONArray listdata) {
            this.dataSource = listdata;
        }
        @Override
        public ProductListActivity.ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.template_product_list, parent, false);
            ProductListActivity.ProductAdapter.ViewHolder

                    viewHolder = new ProductListActivity.ProductAdapter.ViewHolder(listItem);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(ProductListActivity.ProductAdapter.ViewHolder holder, int position) {
            try {

                JSONObject obj = dataSource.optJSONObject(position);
                String name=obj.optString("Name");
                String price=obj.optString("Price");
                String imageURL=obj.optString("ImageURL");
//                                       String image=obj.optString("ImageURL");
                holder.txtName.setText(name);
                holder.txtPrice.setText(price);
                LayoutUtility.setImageByUrl(ProductListActivity.this,holder.imgImage,imageURL);
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),ProductDetailActivity.class);
                        intent.putExtra("response",obj.toString());
                        Log.d("",obj.toString());
                        startActivity(intent);



                    }
                });
//
//                                        LayoutUtility.setImageByUrl(SubCategoryActivity.this,holder.imgImage,url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public int getItemCount() {

            return dataSource.length();

        }


        private class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtName, txtPrice;
            public ImageView imgImage;
            public LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                this.txtName = (TextView) itemView.findViewById(R.id.txtProductName);
                this.txtPrice = (TextView) itemView.findViewById(R.id.txtProductPrice);
                this.imgImage = (ImageView) itemView.findViewById(R.id.imgProductimage);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.productlay);
            }
        }
    }
}