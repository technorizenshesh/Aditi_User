package lk.aditi.ecom.network.request.brand;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetBrandRequest {
    @GET("get_brand")
    Call<JsonElement> getAllBrands();
}
