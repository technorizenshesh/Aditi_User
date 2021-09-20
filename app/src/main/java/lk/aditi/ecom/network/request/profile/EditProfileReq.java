package lk.aditi.ecom.network.request.profile;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface EditProfileReq {

    @Multipart
    @POST("update_user_profile")
    Call<JsonElement> getUpdateProfile(@Part MultipartBody.Part user_id,
                             @Part MultipartBody.Part token,
                             @Part MultipartBody.Part user_name,
                             @Part MultipartBody.Part email,
                             @Part MultipartBody.Part address,
                             @Part MultipartBody.Part dob,
                             @Part MultipartBody.Part mobile,
                             @Part MultipartBody.Part image);
}
