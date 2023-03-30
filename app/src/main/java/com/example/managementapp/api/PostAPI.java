//package com.example.managementapp.api;
//
//import com.example.managementapp.MainMenu;
//import com.example.managementapp.R;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class PostAPI {
//
//    Retrofit retrofit;
//    private WebServiceAPI webServiceAPI;
//
//    public WebServiceAPI getWebServiceAPI() {
//        return webServiceAPI;
//    }
//
//    public void setWebServiceAPI(WebServiceAPI webServiceAPI) {
//        this.webServiceAPI = webServiceAPI;
//    }
//
//    public PostAPI() {
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(MainMenu.context.getString(R.string.BaseUrl))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        webServiceAPI = retrofit.create(WebServiceAPI.class);
//    }
//
//}
