package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lk.aditi.ecom.adapter.Prouct_Summary_Adapter;
import lk.aditi.ecom.databinding.ActivitySummaryBinding;

public class SummaryActivity extends AppCompatActivity {
    private ActivitySummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.tvChange.setOnClickListener(v -> {
            Intent intent =new Intent(this,DeliveryCheckoutActivity.class);
            startActivity(intent);
        });

        binding.rvProduct.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.rvProduct.setAdapter(new Prouct_Summary_Adapter());
    }


}