package lk.aditi.ecom.network.request.address;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteAddress {

    @FormUrlEncoded
    @POST("delete_address")
    Call<JsonElement> deleteAddress(@Field("address_id")String address_id,
                                    @Field("token") String token,
                                    @Field("user_id") String user_id);
}
