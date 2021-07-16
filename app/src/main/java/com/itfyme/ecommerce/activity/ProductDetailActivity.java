package com.itfyme.ecommerce.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.navigation.NavigationView;
import com.itfyme.ecommerce.R;
import com.itfyme.ecommerce.dbservices.MyCartService;
import com.itfyme.ecommerce.dbservices.ProductDetailService;
import com.itfyme.ecommerce.helpers.LayoutUtility;
import com.itfyme.ecommerce.helpers.Utility;
import com.itfyme.ecommerce.interfaces.ResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
/*
        Shows product detail
        Gets the data from previous page (product list or search page) through Intent
        Data is a JSON object containing product information
 */

public class ProductDetailActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView imgView;
    Button addToCart;
    TextView nameTxt, priceTxt, desTxt;
    JSONObject dataObj;
    String ProductID,Name,Description,Price,Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_detail);
            nameTxt = (TextView) findViewById(R.id.nameText);
            priceTxt = (TextView) findViewById(R.id.priceText);
            desTxt = (TextView) findViewById(R.id.desText);
            addToCart = (Button) findViewById(R.id.addCart);
            imgView = (ImageView) findViewById(R.id.productImg);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            ActionBar actionBar;
            actionBar = getSupportActionBar();
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
            // Set BackgroundDrawable
            actionBar.setBackgroundDrawable(colorDrawable);
            if (getIntent().hasExtra("product")) {
                String str = getIntent().getStringExtra("product");
                dataObj = new JSONObject(str);
                ProductID = dataObj.optString("ProductID");
                Name      = dataObj.optString("Name");
                Description = dataObj.optString("Description");
                Price = dataObj.optString("Price");
                Image = dataObj.optString("ImageURL");
            }
            nameTxt.setText(Name);
            priceTxt.setText(Price);
            desTxt.setText(Description);
            LayoutUtility.setImageByUrl(this,imgView,Image);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        addItemToCart();
                }
            });

        }catch (Exception e ){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Intent intent =new Intent(ProductDetailActivity.this,SearchActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.menu_count) {
            Intent intent =new Intent(ProductDetailActivity.this,MyCartActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void addItemToCart(){
        /*
            1. If user is known, meaning if we have user information with customer id pass customerID as parameter else -1
            2. If user is unknown, pass the deviceID / sessionID
         */
        try{
            HashMap<String,String> params=new HashMap<>();
            JSONObject postObj=new JSONObject();
            if (this.userObj != null) { // you have user information already available
                postObj.put("customerid",this.userObj.optString("CustomerID"));
                postObj.put("sessionid","");
            } else {
                postObj.put("customerid","-1");
                postObj.put("sessionid",getFromSharedPreference(Utility.sessionKey));
            }
            JSONObject cartItem =new JSONObject();
            cartItem.put("productid",ProductID);
            cartItem.put("price",Price);
            cartItem.put("qty","1");
            postObj.put("cartitem",cartItem);
            params.put("cart_json",postObj.toString());
            Log.d("param",params.toString());
            new ProductDetailService(this).addCart(params,new ResponseHandler() {
                @Override
                public void onSuccess(Object data) {
                    Toast.makeText(ProductDetailActivity.this," added successfully",Toast.LENGTH_LONG).show();
                    Log.d("cart",data.toString());
                }

                @Override
                public void onFail(Object data) {
                    Toast.makeText(ProductDetailActivity.this,"Error while adding ",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNoData(Object data) {
                    Toast.makeText(ProductDetailActivity.this,"Error while no data ",Toast.LENGTH_LONG).show();

                }
            });
        }catch (Exception e ){
            e.printStackTrace();
        }
    }





}