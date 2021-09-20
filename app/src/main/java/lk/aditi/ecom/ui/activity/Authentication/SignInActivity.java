package lk.aditi.ecom.ui.activity.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.Arrays;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.ActivitySignInBinding;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RequestAuthentication;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.ui.activity.MainActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


    public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private AuthenticationResponse authentication;
    private int RC_SIGN_IN = 100;
    private String called_from;


    private GoogleSignInClient  mGoogleSignInClient;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SetupUI();

        initgoogle();

        initfb();
//        binding.tvSignin.setOnClickListener(v -> {
//            if (validate())
//            startActivity(new Intent(this,MainActivity.class));
//        });

        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this,SignUpActivity.class));
        });
        if (called_from != null && called_from.equalsIgnoreCase("add"))
            binding.ccp.registerPhoneNumberTextView(binding.etNo);
        binding.ccp.setCountryForPhoneCode(94);
    }

    private void initfb() {
        callbackManager = CallbackManager.Factory.create();

        binding.llFb.setOnClickListener(v -> {
            LoginManager.getInstance()
                    .logInWithReadPermissions(
                            this,
                            Arrays.asList("email", "public_profile","")
                    );

        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(SignInActivity.this, "Successfully sign in", Toast.LENGTH_SHORT)
                        .show();

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    Log.v("LoginActivity", response.toString());

                                    // Application code
                                    String email = "",id = "",name = "";

                                    try {
                                        email = object.getString("email");
                                        name = object.getString("name");
                                        id = object.getString("id");

                                        Log.i("kdsndfv", "fb:id "+id);
                                        Log.i("kdsndfv", "fb:email "+email);
                                        Log.i("kdsndfv", "fb:name "+name);

                                     }
                                    catch (JSONException e) {
                                        e.printStackTrace();

                                        Log.i("kdsndfv", "onSuccess: "+e.getMessage());
                                    }

                                });
                         Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i("kdsndfv", "onError: ");

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code

                        Log.i("kdsndfv", "onError: "+exception.toString());
                    }
                });
    }

    private void initgoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.llGoogle.setOnClickListener(v -> {
            signIn();

        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String id = account.getId();
            String email = account.getEmail();
            String name = account.getDisplayName();

            Log.i("kdsndfv", "handleSignInResult:id "+id);
            Log.i("kdsndfv", "handleSignInResult:email "+email);
            Log.i("kdsndfv", "handleSignInResult:name "+name);

            Toast.makeText(this, "Successfully sign in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.i("kdsndfv", "handleSignInResult: "+e.getMessage());
        }
    }

    private void SetupUI() {
        binding.tvSignin.setOnClickListener(v -> {
            if (validate()) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                        .create(RequestAuthentication.class)
                        .logiin(binding.etNo.getText().toString(), binding.etPassword.getText().toString(),
                                NetworkConstraint.type,"ssfsf")
                        .enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                                if (response != null) {
                                    if (response.isSuccessful()) {
                                        binding.loaderLayout.loader.setVisibility(View.GONE);

                                        JsonObject object = response.body().getAsJsonObject();
                                        int status = object.get("status").getAsInt();

                                        if (status == 1) {
                                              authentication = new Gson().fromJson(object, AuthenticationResponse.class);
                                            binding.loaderLayout.loader.setVisibility(View.GONE);
                                            SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.user, response.body());
//                                            CheckOtp();

                                            Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            AuthenticationResponse model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);
                                            Log.i("sffdsscc", "onResponse: "+model.getResult().toString());
                                        } else {

//                                            ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
//
//                                            Snackbar.make(findViewById(android.R.id.content),
//                                                    authentication.getResult(),
//                                                    Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonElement> call, Throwable t) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);

                                Log.i("scfsc", "onFailure: " + t.getMessage());
                            }
                        });
            }
        });
    }

    private void CheckOtp(){

        Log.i("acscscs", "CheckOtp: "+authentication.getResult().getCheckOtp());
        Log.i("acscscs", "id: "+authentication.getResult().getId());
        if (authentication.getResult().getCheckOtp().equals("1")){
            Intent  intent=new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(RequestAuthentication.class)
                    .resend(authentication.getResult().getId())
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                            if (response!=null){
                                if (response.isSuccessful()){
                                    binding.loaderLayout.loader.setVisibility(View.GONE);
//                                    SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.user, response.body());

                                    Intent intent=new Intent(SignInActivity.this, OtpVerificationActivity.class);

                                    Log.i("xzvfvxcx", "onResponse: "+authentication.getResult().getId());
                                    intent.putExtra("user_id",authentication.getResult().getId());
                                    startActivity(intent);
                                }
//                                Your session has been expired.
//                                Auth Failed
//                                status": "0"
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);

                        }
                    });

        }

    }

    private boolean validate() {
           if (TextUtils.isEmpty(binding.etNo.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.text_register_no, Snackbar.LENGTH_SHORT).show();
            return false;
        }
//        else if (binding.etNo.getText().toString().replace(" ", "").length() !=10) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.digitofno_10, Snackbar.LENGTH_SHORT).show();
//            return false;
//
//        }
//        else if (!NUMBER_PATTERN.matcher(binding.etNo.getText().toString().replace(" ", "")).matches()) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
        else if (TextUtils.isEmpty(binding.etPassword.getText().toString().replace(" ", ""))) {
               Snackbar.make(findViewById(android.R.id.content), R.string.enter_pass, Snackbar.LENGTH_SHORT).show();
               return false;
           }
        else if (binding.etPassword.getText().toString().replace(" ", "").length() <= 5) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_password,
                    Snackbar.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }

}