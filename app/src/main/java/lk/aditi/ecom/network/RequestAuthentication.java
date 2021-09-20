package lk.aditi.ecom.network;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAuthentication {


    @POST("signup")
    @FormUrlEncoded
    Call<JsonElement> SignUp(@Field("user_name") String user_name,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("mobile") String mobile,
                                @Field("register_id") String register_id,
                                @Field("type") String type);

    @POST("check_otp")
    @FormUrlEncoded
    Call<JsonElement> Otp(@Query("otp") String otp,
                          @Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("login")

    Call<JsonElement> logiin(@Field("email") String mobile,
                             @Field("password") String password,
                             @Field("type") String type,
                             @Field("register_id")String register_id);




    @POST("resend_otp")
    @FormUrlEncoded
    Call<JsonElement> resend(@Field("user_id ") String user_id );

}
