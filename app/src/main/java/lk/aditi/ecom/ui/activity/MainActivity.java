package lk.aditi.ecom.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import lk.aditi.ecom.R;
import lk.aditi.ecom.ui.fragment.AccountFragment;
import lk.aditi.ecom.ui.fragment.CartFragment;
import lk.aditi.ecom.ui.fragment.ExploreFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout=findViewById(R.id.frame);
        bottomNavigationView=findViewById(R.id.bottom);


        replace(new ExploreFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.explore:
                        replace(new ExploreFragment(),"ExploreFragment");
                        return true;
                    case R.id.cart:
                        replace(new CartFragment(),"CartFragment");
                        return true;

                    case R.id.account:
                        replace(new AccountFragment(),"AccountFragment");
                        return true;

                }
                return false;
            }
        });

    }

    public void replace(Fragment fragment, String tag){
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.addToBackStack(tag);
        ft.commit();
    }

    public void replace(Fragment fragment){
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    public void back(){
        finish();
    }
}