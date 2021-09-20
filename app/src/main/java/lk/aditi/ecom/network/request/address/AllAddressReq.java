package lk.aditi.ecom.network.request.address;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AllAddressReq {
    @FormUrlEncoded
    @POST("get_address")
    Call<JsonElement> getAllAddresses(@Field("user_id") String user_id
                                      ,@Field("token") String token);
}
