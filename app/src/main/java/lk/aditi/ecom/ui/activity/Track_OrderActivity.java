package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import lk.aditi.ecom.R;
import lk.aditi.ecom.adapter.Track_Order_Adapter;

public class Track_OrderActivity extends AppCompatActivity {

    private RecyclerView rv_track_order;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        rv_track_order=findViewById(R.id.rv_track_order);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(v -> {
            finish();
        });
        rv_track_order.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        rv_track_order.setAdapter(new Track_Order_Adapter());
    }
}