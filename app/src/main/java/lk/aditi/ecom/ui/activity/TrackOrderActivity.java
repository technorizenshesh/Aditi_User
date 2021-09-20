package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.models.TimeLineModel;
 import lk.aditi.ecom.adapter.TimeLineAdapter;

public class TrackOrderActivity extends AppCompatActivity {

    private ImageView iv_back;
    private RecyclerView rv_TrackOrder;
    private TimeLineAdapter timeLineAdapter;
    private List<TimeLineModel> timeLineList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order2);

        rv_TrackOrder = findViewById(R.id.rv_TrackOrder);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(v -> {
            finish();
        });
        rv_TrackOrder.setLayoutManager(new LinearLayoutManager(this));
        timeLineAdapter = new TimeLineAdapter(timeLineList);
        rv_TrackOrder.setAdapter(timeLineAdapter);
        String status="Ready for Pickup";
        adddata(status);
    }

    private void adddata(String status) {

        switch (status) {
            case "Order Signed":
                timeLineList.add(new TimeLineModel("Order Signed", "Lagos State, Nigeria", 1,"20/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Order Processed", "Lagos State, Nigeria", 0,"20/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Ready for Pickup", "Lagos State, Nigeria", 0,"20/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("In Transit", "Lagos State, Nigeria", 0,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Delivered", "Lagos State, Nigeria", 0,"21/18","10:00 AM"));
                break;

            case "Order Processed":
                timeLineList.add(new TimeLineModel("Order Signed", "Lagos State, Nigeria", 1,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Order Processed", "Lagos State, Nigeria", 1,"20/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Ready for Pickup", "Lagos State, Nigeria", 0,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("In Transit", "Lagos State, Nigeria", 0,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Delivered", "Lagos State, Nigeria", 0,"21/18","10:00 AM"));
                break;

            case "Ready for Pickup":
                timeLineList.add(new TimeLineModel("Order Signed", "Lagos State, Nigeria", 1,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Order Processed", "Lagos State, Nigeria", 1,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Ready for Pickup", "Lagos State, Nigeria", 1,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("In Transit", "Lagos State, Nigeria", 0,"21/18","10:00 AM"));
                timeLineList.add(new TimeLineModel("Delivered", "Lagos State, Nigeria", 0,"21/18","10:00 AM"));
                break;

            case "In Transit":
                timeLineList.add(new TimeLineModel("Order Signed", "Lagos State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("Order Processed", "Lagos State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("Ready for Pickup", "Lagos State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("In Transit", "Edo State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("Delivered", "Edo State, Nigeria", 0,"",""));
                break;

            case "Delivered":
                timeLineList.add(new TimeLineModel("Order Signed", "Lagos State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("Order Processed", "Lagos State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("Ready for Pickup", "Lagos State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("In Transit", "Lagos State, Nigeria", 1,"",""));
                timeLineList.add(new TimeLineModel("Delivered", "Edo State, Nigeria", 1,"",""));
                break;
        }


    }


//    private void getList() {
//
//        //5 order cancel(by user), 4 is order placed, 3 is order packed,
//        // 2 is orderd shipped, 1 is order delivered, 0 is order rejected
//
//        timeLineList.clear();
//
//        if(order!=null){
//            timeLineList.add(new TimeLineModel("Confirmed","Your order has been placed.",1));
//            if(order.getOrder_status()==0){
//                timeLineList.add(new TimeLineModel("Rejected","Your order has been Reject.",-1));
//            }
//            else if(order.getOrder_status()==1){
//                timeLineList.add(new TimeLineModel("Packed","Seller has processed your order.",1));
//                timeLineList.add(new TimeLineModel("Shipped","Your item has been shipped.",1));
//                timeLineList.add(new TimeLineModel("Delivered","Your item has been deliverd.",1));
//            }
//            else if(order.getOrder_status()==3){
//                timeLineList.add(new TimeLineModel("Packed","Seller has processed your order.",1));
//                timeLineList.add(new TimeLineModel("Shipped","",0));
//                timeLineList.add(new TimeLineModel("Delivered","",0));
//            }
//            else if(order.getOrder_status()==2){
//                timeLineList.add(new TimeLineModel("Packed","Seller has processed your order.",1));
//                timeLineList.add(new TimeLineModel("Shipped","Your item has been shipped.",1));
//                timeLineList.add(new TimeLineModel("Delivered","",0));
//            }
//
//            else if(order.getOrder_status()==4){
//                timeLineList.add(new TimeLineModel("Packed","",0));
//                timeLineList.add(new TimeLineModel("Shipped","",0));
//                timeLineList.add(new TimeLineModel("Delivered","",0));
//            }
//            else if(order.getOrder_status()==5){
//                timeLineList.add(new TimeLineModel("Cancel","Your order has been Cancel.",-1));
//            }
//        }
//
//        timeLineAdapter.notifyDataSetChanged();
//
//    }

}