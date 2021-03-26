package com.demo.theweather.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        HttpUrl url = null;

        url = originalRequest.url().newBuilder().addQueryParameter("apikey", NetworkClient.API_KEY).build();

        Request newRequest = originalRequest.newBuilder().url(url).build();
        return chain.proceed(newRequest);
    }
}
