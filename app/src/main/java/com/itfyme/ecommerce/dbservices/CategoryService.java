package com.itfyme.ecommerce.dbservices;

import android.content.Context;

import com.itfyme.ecommerce.interfaces.ResponseHandler;
import com.itfyme.ecommerce.network.VolleyNetworkManager;

import java.util.HashMap;

public class CategoryService {
    Context mContext;
    private String getListByPageURL ="category/get-list-object-page";
    private String getListURL       ="category/get-list";
    private String getURL           ="category/get";
    private String addURL           ="category/add";
    private String updateURL        ="category/update";
    private String deleteURL        ="category/delete";
    private String getMenuList      ="menu/get-menu";

    public CategoryService(Context context) {
        mContext = context;

    }

    public void getCategoryListByPage(HashMap<String, String> params, ResponseHandler responseHandler) {
        new VolleyNetworkManager(mContext).getRequest(getListByPageURL, params, new ResponseHandler() {
            @Override
            public void onSuccess(Object data) {
                try {
                    responseHandler.onSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Object data) {
                responseHandler.onFail("Failed to load data");

            }

            @Override
            public void onNoData(Object data) {
                responseHandler.onFail("No data available");
            }
        });


    }


    public void getCategoryList(HashMap<String, String> params, ResponseHandler responseHandler) {
        new VolleyNetworkManager(mContext).getRequest(getListURL, params, new ResponseHandler() {
            @Override
            public void onSuccess(Object data) {
                try {
                    responseHandler.onSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Object data) {
                responseHandler.onFail("Failed to load data");

            }

            @Override
            public void onNoData(Object data) {
                responseHandler.onFail("No data available");
            }
        });


    }

    public void getMenuList(HashMap<String, String> params, ResponseHandler responseHandler) {
        new VolleyNetworkManager(mContext).getRequest(getMenuList, params, new ResponseHandler() {
            @Override
            public void onSuccess(Object data) {
                try {
                    responseHandler.onSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Object data) {
                responseHandler.onFail("Failed to load data");

            }

            @Override
            public void onNoData(Object data) {
                responseHandler.onFail("No data available");
            }
        });


    }
    public void addCategory(HashMap<String, String> params, ResponseHandler responseHandler) {
        new VolleyNetworkManager(mContext).postRequest(addURL, params, new ResponseHandler() {
            @Override
            public void onSuccess(Object data) {
                try {
                    responseHandler.onSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Object data) {
                responseHandler.onFail("Failed to add data");

            }

            @Override
            public void onNoData(Object data) {

            }
        });


    }
    public void updateCategory(HashMap<String, String> params, ResponseHandler responseHandler) {
        new VolleyNetworkManager(mContext).postRequest(updateURL, params, new ResponseHandler() {
            @Override
            public void onSuccess(Object data) {
                try {
                    responseHandler.onSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Object data) {
                responseHandler.onFail("Failed to update data");

            }

            @Override
            public void onNoData(Object data) {

            }
        });


    }
    public void deleteCategory(String id, ResponseHandler responseHandler) {
        HashMap<String, String> params=new HashMap<>();
        params.put("Categoryid",id);
        new VolleyNetworkManager(mContext).postRequest(deleteURL, params, new ResponseHandler() {
            @Override
            public void onSuccess(Object data) {
                try {
                    responseHandler.onSuccess(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Object data) {
                responseHandler.onFail("Failed to delete data");

            }

            @Override
            public void onNoData(Object data) {

            }
        });


    }

}
