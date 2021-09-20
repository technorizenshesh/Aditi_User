package lk.aditi.ecom.network.request.addtocart;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeletetoCartReq {
    @FormUrlEncoded
    @POST("delete_cart_data")
    Call<JsonElement> GetDeleteToCart(@Field("user_id") String user_id,
                                   @Field("cart_id") String cart_id,
                                      @Field("token")String token);

}
