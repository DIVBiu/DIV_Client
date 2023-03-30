//package com.example.managementapp.api;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ContactAPI {
//    Retrofit retrofit;
//    private WebServiceAPI webServiceAPI;
//    private String BaseUrl;
//
//    public WebServiceAPI getWebServiceAPI() {
//        return webServiceAPI;
//    }
//
//    public void setWebServiceAPI(WebServiceAPI webServiceAPI) {
//        this.webServiceAPI = webServiceAPI;
//    }
//
//    public ContactAPI(String server) {
//        BaseUrl = "http://" + server + "/api/";
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        webServiceAPI = retrofit.create(WebServiceAPI.class);
//    }
//}
