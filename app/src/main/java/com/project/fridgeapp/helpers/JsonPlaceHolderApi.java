package com.project.fridgeapp.helpers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("api/v0/product/{barcode}.json")
    Call<String> getNameFromApi(@Path("barcode") String barcode);
}
