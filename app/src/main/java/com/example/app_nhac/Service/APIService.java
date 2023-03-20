package com.example.app_nhac.Service;

public class APIService {
    private static String base_url="https://thuancntt3.000webhostapp.com/Sever/";

    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
