package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import lk.aditi.ecom.R;
import lk.aditi.ecom.adapter.Order_History_Adapter;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView rv_order_history;
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        rv_order_history=findViewById(R.id.rv_order_history);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(v -> {
            finish();
        });

        rv_order_history.setLayoutManager(new LinearLayoutManager(this));
        rv_order_history.setAdapter(new Order_History_Adapter());


    }
}