package lk.aditi.ecom.network.request.profile;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileRequest {

    @GET("get_profile")
    Call<JsonElement> getProfile(@Query("user_id") String user_id );

}
