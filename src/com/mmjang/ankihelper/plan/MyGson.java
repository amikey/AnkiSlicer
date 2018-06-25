package com.mmjang.ankihelper.plan;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

public class MyGson {

    private Gson gson;

    public MyGson() {
        gson = new Gson();
    }

    public MyGson(Gson gson) {
        this.gson = gson;
    }

    public <T> T fromJson(String json, Class typeOfT) {
        try {
            return (T) gson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T fromJson(JsonElement json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJson(Object src) {
        return gson.toJson(src);
    }

}