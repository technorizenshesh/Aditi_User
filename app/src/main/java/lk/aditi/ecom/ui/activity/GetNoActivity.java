package lk.aditi.ecom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.ActivityGetNoBinding;

import static lk.aditi.ecom.utils.AppUtils.NUMBER_PATTERN;

public class GetNoActivity extends AppCompatActivity {

    private ActivityGetNoBinding  binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGetNoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvContinue.setOnClickListener(v -> {
            if (validate()) {
                startActivity(new Intent(this,MainActivity.class));
            }
        });
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etNo.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.text_register_no, Snackbar.LENGTH_SHORT).show();
            return false;
        }  else if (binding.etNo.getText().toString().replace(" ", "").length() != 10) {
            Snackbar.make(findViewById(android.R.id.content), R.string.digitofno_10, Snackbar.LENGTH_SHORT).show();
            return false;

        } else if (!NUMBER_PATTERN.matcher(binding.etNo.getText().toString().replace(" ", "")).matches()) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_right_no,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}