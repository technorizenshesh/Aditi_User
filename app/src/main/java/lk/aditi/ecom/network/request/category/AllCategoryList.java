package lk.aditi.ecom.network.request.category;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AllCategoryList {
    @GET("get_category")
    Call<JsonElement> getAllCategory();
}
