//package com.example.managementapp;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import java.io.IOException;
//
//public class RetryExample {
//
//    private static final int MAX_RETRIES = 3;
//    private static final String url = "http://" + GetIP.getIPAddress() + "/";
//
//    public static String Retry(String[] args) {
//        OkHttpClient client = createOkHttpClientWithRetry();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            ResponseBody responseBody = response.body();
//            if (response.isSuccessful() && responseBody != null) {
//                String responseString = responseBody.string();
//                return responseString;
//            } else {
//                System.out.println("Request failed with code: " + response.code());
//            }
//        } catch (IOException e) {
//            System.out.println("Request failed with exception: " + e.getMessage());
//        }
//        return "";
//    }
//
//    private static OkHttpClient createOkHttpClientWithRetry() {
//        return new OkHttpClient.Builder()
//                .addInterceptor(new RetryInterceptor(MAX_RETRIES))
//                .build();
//    }
//
//    private static class RetryInterceptor implements okhttp3.Interceptor {
//        private final int maxRetries;
//        private int retryCount = 0;
//
//        RetryInterceptor(int maxRetries) {
//            this.maxRetries = maxRetries;
//        }
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            Response response;
//
//            // Retry the request for a maximum number of times
//            while (retryCount < maxRetries) {
//                try {
//                    response = chain.proceed(request);
//                    if (response.isSuccessful()) {
//                        return response;
//                    }
//                } catch (IOException e) {
//                    System.out.println("Request failed with exception: " + e.getMessage());
//                }
//
//                // Exponential backoff before retrying
//                long backoffDelay = (long) Math.pow(2, retryCount) * 1000;
//                System.out.println("Retrying in " + backoffDelay + " ms...");
//                retryCount++;
//                try {
//                    Thread.sleep(backoffDelay);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            // If all retries fail, throw the last encountered exception
//            throw new IOException("Request failed after " + maxRetries + " retries.");
//        }
//    }
//}



package com.example.managementapp;

import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import java.io.IOException;

public class RetryExample {

    private static final int MAX_RETRIES = 3;
    private static Response server_response = null;

    private static int i =0;

    public static Response intercept(Request request) throws IOException {


        OkHttpClient client = new OkHttpClient();
        for (; i < MAX_RETRIES; i++) {

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    server_response = response;
                    i=3;
                }
            });
        }
        return server_response;
    }
}

