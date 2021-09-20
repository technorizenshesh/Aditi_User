package lk.aditi.ecom.network.request.shopnearme;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetAllShopsNearByMeReq {
    @POST("get_shop_list_nearbuy")
    @FormUrlEncoded
    Call<JsonElement> getAllShopsNearByMe(@Field("user_id")String user_id,
                                          @Field("token")String token,
                                          @Field("lat")String lat,
                                          @Field("lon")String lon
                                          );
}

//    @Query("lat") String lat,
//    @Query("lon")String lon

//http://yashtechsolutions.com/takatu/webservice/get_shop_list_nearbuy
//https://technorizen.com/Aditi/webservice/get_shop_list_nearbuy