package lk.aditi.ecom.network.request.wishlist;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetWishListReq {

    @POST("get_wishlist")
    @FormUrlEncoded
    Call<JsonElement> getAllWishList(@Field("user_id") String user_id,
                                     @Field("token") String token);
}
