package lk.aditi.ecom.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import lk.aditi.ecom.R;
import lk.aditi.ecom.databinding.FragmentAccountBinding;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.ui.activity.EditProfileActivity;
import lk.aditi.ecom.ui.activity.OrderHistoryActivity;
import lk.aditi.ecom.ui.activity.ShippingAddressActivity;
import lk.aditi.ecom.ui.activity.SummaryActivity;
import lk.aditi.ecom.ui.activity.Track_OrderActivity;
import lk.aditi.ecom.ui.activity.WishlistActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private AuthenticationResponse model;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);

        init();


        return binding.getRoot();

    }


    void init() {
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);
        if (!model.getResult().getImage().equals(null)) {
            Picasso.get().load(model.getResult().getImage()).placeholder(R.drawable.user_placeholder).into(binding.cvProfile);
            binding.loaderLayout.loader.setVisibility(View.GONE);

        } else {
            binding.loaderLayout.loader.setVisibility(View.GONE);
        }
        SetData();

        binding.llShipingAdd.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ShippingAddressActivity.class));
        });
        binding.llEditProfile.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), EditProfileActivity.class));
        });

        binding.llNotification.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SummaryActivity.class));
        });

        binding.llWishlist.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), WishlistActivity.class));
        });


        binding.llTrackOrder.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Track_OrderActivity.class));

        });

        binding.llOrderHistory.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), OrderHistoryActivity.class));

        });
        binding.llLogout.setOnClickListener(v -> {
            SharedPrefsManager.getInstance().clearPrefs();
            startActivity(new Intent(getContext(), SignInActivity.class));
            getActivity().finish();

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SetData();
    }

    private void SetData() {
        if (model != null) {
            binding.tvName.setText(model.getResult().getUserName());
            binding.tvEmail.setText(model.getResult().getEmail());

        }

    }
}