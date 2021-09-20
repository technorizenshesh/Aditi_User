package lk.aditi.ecom.network.request.allcard;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetAllCardListReq {

    @FormUrlEncoded
    @POST("get_cart")
    Call<JsonElement> GetCartList(@Field("user_id") String user_id,
                                  @Field("token")String token);
}
