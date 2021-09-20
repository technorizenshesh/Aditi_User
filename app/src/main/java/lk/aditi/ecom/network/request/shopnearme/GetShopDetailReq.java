package lk.aditi.ecom.network.request.shopnearme;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetShopDetailReq {
    @POST("get_shopitem_byid")
    @FormUrlEncoded
    Call<JsonElement> getShopDetail(@Field("shop_id") String shop_id,
                                    @Field("user_id") String user_id,
                                    @Field("token") String token
                                    );
}
