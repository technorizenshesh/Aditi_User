package lk.aditi.ecom.ui.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.ActivityEditProfileBinding;
import lk.aditi.ecom.models.ResponseAuthError;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.profile.EditProfileReq;
import lk.aditi.ecom.network.request.profile.ProfileRequest;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.utils.RealPathUtil;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int CAPTURE_IMAGE = 3;
    final int PERMISSION_ALL = 100;
    final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    private final Calendar selectedDateTime = Calendar.getInstance();
    private ActivityEditProfileBinding binding;
    private String called_from;
    private AuthenticationResponse model;
    private Uri uri;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        setUpDatePicker();

        setData();

        binding.tvSave.setOnClickListener(v -> {
            if (validate()) {
                getUpdateProfile();
            }
        });

        if (called_from != null && called_from.equalsIgnoreCase("add"))
            binding.ccp.registerPhoneNumberTextView(binding.etNo);
        binding.ccp.setCountryForPhoneCode(94);

        binding.ivCameraProfileEdit.setOnClickListener(v -> {
            if (!hasPermissions(EditProfileActivity.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(EditProfileActivity.this, PERMISSIONS, PERMISSION_ALL);
            } else {
                String fileName = "new-photo-name.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                uri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values
                );
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAPTURE_IMAGE);
            }

        });
    }

    private void setData() {
        if (model != null) {
            Log.i("sfssssf", "onCreate: "+model.toString());
            Picasso.get().load(model.getResult().getImage()).placeholder(R.drawable.user_placeholder).into(binding.ivProfile);
            binding.etNo.setText(model.getResult().getMobile());
            binding.etName.setText(model.getResult().getUserName());
            binding.etEmail.setText(model.getResult().getEmail());
            binding.etAddress.setText(model.getResult().getAddress());
            binding.etDob.setText(model.getResult().getDob());
         }

    }

    private MultipartBody.Part getPartFromFile(String name, File file) {
        if (file == null)
            return null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part profile_image = MultipartBody.Part.createFormData(name, file.getPath(), requestBody);
        Log.i("sxzfdsf", "getPartFromFile: " + profile_image);
        return profile_image;
    }

    private void getUpdateProfile() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id", model.getResult().getId());
        MultipartBody.Part token = MultipartBody.Part.createFormData("token", model.getResult().getToken());
        MultipartBody.Part user_name = MultipartBody.Part.createFormData("user_name", binding.etName.getText().toString());
        MultipartBody.Part email = MultipartBody.Part.createFormData("email", binding.etEmail.getText().toString());
        MultipartBody.Part address = MultipartBody.Part.createFormData("address", binding.etAddress.getText().toString());
        MultipartBody.Part dob = MultipartBody.Part.createFormData("dob",binding.etDob.getText().toString() );
        MultipartBody.Part mobile = MultipartBody.Part.createFormData("mobile", binding.etNo.getText().toString());
        MultipartBody.Part image = getPartFromFile("image", file);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(EditProfileReq.class)
                .getUpdateProfile(user_id,token, user_name,
                        email, address
                        , dob, mobile, image)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("xsxvdgfv", "onResponse: " + response.toString());
                        Log.i("xsxvdgfv", "onResponse: " + response.body());

                        if (response != null) {
                            if (response.isSuccessful()) {

                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                AuthenticationResponse authentication = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);
                                if (status == 1) {
                                    SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.user, response.body());
                                    Log.i("xsxvdgfv", "onResponse: " + authentication.toString());
                                    finish();

                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.") || authentication.getMessage().equals("Auth Failed"))) {
                                        Toast.makeText(EditProfileActivity.this, authentication.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(EditProfileActivity.this, SignInActivity.class));
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("xsxvdgfv", "onFailure: " + t.getMessage());
                    }
                });


    }

    private void setUpDatePicker() {
        MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setTheme(R.style.ThemeMaterialCalendar)
                .build();

        binding.etDob.setOnClickListener(v -> {
            if(picker!=null && !picker.isAdded()) {
                picker.show(getSupportFragmentManager(), "tag");
            }
        });

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                selectedDateTime.setTime(new Date(selection));
                binding.etDob.setText(new SimpleDateFormat("dd-MMM-yyyy").format(selectedDateTime.getTimeInMillis()));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.i("xzvcbc", "onActivityResult: " + 32);
            if (requestCode == PICK_IMAGE) {
                Log.i("xzvcbc", "onActivityResult: " + 327);

                Uri imageUri = data.getData();
                String s = RealPathUtil.getRealPath(this, imageUri);
                file = new File(s);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    binding.ivProfile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == CAPTURE_IMAGE) {
                binding.ivProfile.setImageURI(uri);
                String s = RealPathUtil.getRealPath(this, uri);
                file = new File(s);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    binding.ivProfile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void init() {
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);

        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!hasPermissions(EditProfileActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(EditProfileActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissionsList, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0) {
                    boolean b = true;
                    for (int per : grantResults) {
                        if (per == PackageManager.PERMISSION_DENIED) {
                            b = false;
                        }
                    }
                    if (b) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                    }

                }
                return;
            }
        }
    }

    private boolean validate() {
//        if (file == null) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.select_img,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }  else
        if (TextUtils.isEmpty(binding.etName.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_name, Snackbar.LENGTH_SHORT).show();
            return false;
        }
//        else if (TextUtils.isEmpty(binding.etEmail.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_email, Snackbar.LENGTH_SHORT).show();
//            return false;
//        } else if (!AppUtils.EMAIL_ADDRESS_PATTERN.matcher(binding.etEmail.getText().toString().replace(" ", "")).matches()
//        ) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_correct_email,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
        else if (TextUtils.isEmpty(binding.etNo.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.text_register_no, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (binding.etNo.getText().toString().replace(" ", "").length() != 10) {
            Snackbar.make(findViewById(android.R.id.content), R.string.digitofno_10, Snackbar.LENGTH_SHORT).show();
            return false;

        }
//        else if (!NUMBER_PATTERN.matcher(binding.etNo.getText().toString().replace(" ", "")).matches()) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
        else {
            return true;
        }
    }

    private void getProfile() {
        if (model != null) {
            String id = model.getResult().getId();
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(ProfileRequest.class)
                    .getProfile(id)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                            if (response != null) {
                                if (response.isSuccessful()) {
                                    JsonObject object = response.body().getAsJsonObject();
                                    int status = object.get("status").getAsInt();
                                    if (status == 1) {
                                        AuthenticationResponse authentication = new Gson().fromJson(object, AuthenticationResponse.class);
                                        Log.i("xsxvdgfv", "onResponse: " + response.toString());
                                        Log.i("xsxvdgfv", "onResponse: " + response.body());
                                        Log.i("xsxvdgfv", "authen: " + authentication.getResult().toString());

                                    } else {

                                        ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                        Snackbar.make(findViewById(android.R.id.content),
                                                authentication.getResult(),
                                                Snackbar.LENGTH_SHORT).show();
                                    }


                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            Log.i("xsxvdgfv", "onFailure: " + t.getMessage());
                        }
                    });

        }
    }


}