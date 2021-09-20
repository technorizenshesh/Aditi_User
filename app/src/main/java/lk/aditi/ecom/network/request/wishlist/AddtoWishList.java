package lk.aditi.ecom.network.request.wishlist;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddtoWishList {

    @FormUrlEncoded
    @POST("add_to_wishlist")
    Call<JsonElement> GetAddToWishList(@Field("shop_id") String shop_id,
                                   @Field("user_id") String user_id,
                                   @Field("item_id") String item_id,
                                   @Field("quantity") String quantity,
                                       @Field("token") String token);
}
