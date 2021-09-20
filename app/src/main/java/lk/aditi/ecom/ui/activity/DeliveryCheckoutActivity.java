package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import lk.aditi.ecom.R;

public class DeliveryCheckoutActivity extends AppCompatActivity {
    private ImageView iv_back;

    private TextView tv_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_checkout);

        tv_next=findViewById(R.id.tv_next);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(v -> {
            finish();
        });

        tv_next.setOnClickListener(v -> {
            startActivity(new Intent(this,AddressCheckoutActivity.class));
        });
    }
}