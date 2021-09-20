package lk.aditi.ecom.network.request.addtocart;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddtoCastReq {

    @FormUrlEncoded
    @POST("add_to_cart")
    Call<JsonElement> GetAddToCart(@Field("shop_id") String shop_id,
                                   @Field("user_id") String user_id,
                                   @Field("item_id") String item_id,
                                   @Field("quantity") String quantity,
                                   @Field("token") String token);
}

