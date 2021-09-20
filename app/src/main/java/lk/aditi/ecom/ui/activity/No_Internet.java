package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import lk.aditi.ecom.R;

public class No_Internet extends AppCompatActivity {

    private TextView tv_retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internwt);

        tv_retry=findViewById(R.id.tv_retry);
    }
}