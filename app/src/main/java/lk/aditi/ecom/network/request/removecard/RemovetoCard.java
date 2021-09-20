package lk.aditi.ecom.network.request.removecard;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RemovetoCard {

    @FormUrlEncoded
    @POST("delete_cart_data")
    Call<JsonElement> GetRemoveToCart(@Field("user_id") String user_id,
                                   @Field("token")String token,
                                   @Field("cart_id") String cart_id);
}
