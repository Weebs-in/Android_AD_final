package com.example.mobile_adproject.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 在这里实现添加请求头的逻辑
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiYSI6WyJzeXM6bWVtYmVyIl0sInUiOiJTbm93IiwiZXhwIjoxNjkxNDgwMDY3fQ.aMM1e5h1R2WJcckmhW7o-Os80vrL-Gd8QH5EBaR93f4"); // 设置认证令牌

        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }
}
