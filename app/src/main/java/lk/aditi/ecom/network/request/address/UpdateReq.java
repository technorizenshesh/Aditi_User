package lk.aditi.ecom.network.request.address;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpdateReq {

    @FormUrlEncoded
    @POST("update_address")
    Call<JsonElement> updateAddressReq(@Field("address_id") String address_id,
                                    @Field("street_address1") String street_address1,
                                    @Field("street_address2") String street_address2,
                                    @Field("city") String city,
                                    @Field("state") String state,
                                    @Field("country") String country,
                                    @Field("address_type") String address_type,
                                       @Field("token") String token,
                                       @Field("user_id") String user_id);
}
