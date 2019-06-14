package com.yanhua.mvvmlibrary.http.encode;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.yanhua.mvvmlibrary.ssm.PublicGmResult;
import com.yanhua.mvvmlibrary.utils.KLog;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public  class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private TypeAdapter<T> adapter;
    private Gson gson;

    public DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    public DecodeResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String ciphertext = new String(responseBody.bytes());
        try {
            JSONObject obj = new JSONObject(ciphertext);
            String object= PublicGmResult.decryptData(obj.optString("data"));
            JSONObject data = new JSONObject(object);
            obj.put("data",data);
            KLog.e(obj.toString());
            return adapter.fromJson(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}