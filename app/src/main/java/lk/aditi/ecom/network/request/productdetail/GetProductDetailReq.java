package lk.aditi.ecom.network.request.productdetail;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetProductDetailReq {
    @FormUrlEncoded
    @POST("get_product_details")
    Call<JsonElement> getProductDetails(@Field("item_id") String item_id,
                                        @Field("user_id") String user_id,
                                        @Field("token") String token);
}
