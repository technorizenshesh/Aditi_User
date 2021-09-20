package lk.aditi.ecom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import lk.aditi.ecom.R;
import lk.aditi.ecom.adapter.SuggestionAdapter;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rv_Suggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        init();
    }

    private void init(){
        rv_Suggestion=findViewById(R.id.rv_Suggestion);
        rv_Suggestion.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        rv_Suggestion.setAdapter(new SuggestionAdapter());
    }
}