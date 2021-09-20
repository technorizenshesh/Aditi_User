package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import lk.aditi.ecom.R;


public class PaymentsCheckoutActivity extends AppCompatActivity {
    private ImageView iv_back;
    private TextView tv_next,tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_checkout);
        iv_back=findViewById(R.id.iv_back);
        tv_back=findViewById(R.id.tv_back);
        tv_next=findViewById(R.id.tv_next);

        tv_back.setOnClickListener(v -> {
            finish();
        });

        iv_back.setOnClickListener(v -> {
            finish();
        });

        tv_next.setOnClickListener(v -> {
            startActivity(new Intent(PaymentsCheckoutActivity.this, MainActivity.class));
        });
    }
}