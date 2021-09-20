package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import lk.aditi.ecom.R;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            AuthenticationResponse model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);

//            Log.i("xvdfgv", "onCreate: "+model.toString());
            if (model!=null){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }else {
                Intent i = new Intent(SplashActivity.this, SelectLanguage.class);
                startActivity(i);
            }

            finish();
         },3000);

    }
}