package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import lk.aditi.ecom.R;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;


public class SelectLanguage extends AppCompatActivity {

    private TextView tv_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_select_language);

        tv_next= findViewById(R.id.tv_next);
        tv_next.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));

        });
    }
}