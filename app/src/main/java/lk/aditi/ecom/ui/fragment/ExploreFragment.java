package lk.aditi.ecom.ui.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.adapter.CategoryAdapter;
import lk.aditi.ecom.adapter.ShopNearByMeAdapter;
import lk.aditi.ecom.databinding.FragmentExploreBinding;
import lk.aditi.ecom.models.ResponseAuthError;
import lk.aditi.ecom.models.category.AllCategoryResponse;
import lk.aditi.ecom.models.shopnearbyme.ResultItem;
import lk.aditi.ecom.models.shopnearbyme.ShopNearByMeResponse;
import lk.aditi.ecom.models.signup.AuthenticationResponse;
import lk.aditi.ecom.network.NetworkConstraint;
import lk.aditi.ecom.network.RetrofitClient;
import lk.aditi.ecom.network.request.category.AllCategoryList;
import lk.aditi.ecom.network.request.shopnearme.GetAllShopsNearByMeReq;
import lk.aditi.ecom.ui.activity.Authentication.SignInActivity;
import lk.aditi.ecom.ui.activity.SearchActivity;
import lk.aditi.ecom.utils.SharePrefrenceConstraint;
import lk.aditi.ecom.utils.SharedPrefsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreFragment extends Fragment {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 10;
    private FragmentExploreBinding binding;
    private String mStringLatitude, mStringLongitude;
    private ShopNearByMeAdapter nearByMeAdapter;
    private CategoryAdapter categoryAdapter;
    private int API_COUNT,ALL_API_COUNT = 2;

    private final List<ResultItem> list = new ArrayList<>();
    private final List<lk.aditi.ecom.models.category.ResultItem> resultItems = new ArrayList<>();
    private AuthenticationResponse model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(getLayoutInflater());

          model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, AuthenticationResponse.class);

        init();

        Places.initialize(getContext(), getResources().getString(R.string.api_key));

        return binding.getRoot();


    }

    private void init() {

        getShopNearByMe();

        getAllCategory();
        binding.ivCamera.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SearchActivity.class));
        });

        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rvShopNearByMe.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        nearByMeAdapter = new ShopNearByMeAdapter(list);
        categoryAdapter=new CategoryAdapter(resultItems);
        binding.rvCategory.setAdapter(categoryAdapter);
        binding.rvShopNearByMe.setAdapter(nearByMeAdapter);

        binding.llMain.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });


    }

    private void getAllCategory() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        binding.layout.setVisibility(View.GONE);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(AllCategoryList.class)
                .getAllCategory()
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                                binding.layout.setVisibility(View.VISIBLE);

                        Log.i("scdvvd", "onResponse: " + response.body());
                        Log.i("scdvvd", "onResponse: " + response.toString());

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    AllCategoryResponse authentication = new Gson().fromJson(object, AllCategoryResponse.class);
                                    resultItems.addAll(authentication.getResult());
                                    categoryAdapter.notifyDataSetChanged();
                                } else {
                                    ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                }


                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                                binding.layout.setVisibility(View.VISIBLE);

                        Log.i("scdvvd", "onResponse: " + t.getMessage());

                    }
                });

    }

    private void getShopNearByMe() {
        binding.layout.setVisibility(View.GONE);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(GetAllShopsNearByMeReq.class)
                .getAllShopsNearByMe(model.getResult().getId(),model.getResult().getToken(),
                        mStringLatitude,mStringLongitude)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                                binding.layout.setVisibility(View.VISIBLE);


                        Log.i("scdvvd", "onResponse: " + response.body());
                        Log.i("scdvvd", "onResponse: " + response.toString());

                        ShopNearByMeResponse authentication = null;
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                authentication = new Gson().fromJson(object, ShopNearByMeResponse.class);
                                if (status == 1) {
                                    list.addAll(authentication.getResult());
                                    nearByMeAdapter.notifyDataSetChanged();
                                } else {
                                    if ((authentication.getMessage().equals("Your session has been expired.")||authentication.getMessage().equals("Auth Failed"))){
                                        startActivity(new Intent(getContext(), SignInActivity.class));
                                    }
                                 }


                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                                binding.layout.setVisibility(View.VISIBLE);

                        Log.i("scdvvd", "onResponse: " + t.getMessage());

                    }
                });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (place != null) {
                    LatLng latLng = place.getLatLng();
                    mStringLatitude = String.valueOf(latLng.latitude);
                    mStringLongitude = String.valueOf(latLng.longitude);
                    binding.etLocation.setText(place.getName());
                    Log.i("dfvdcdcvf", "onActivityResult: " + mStringLatitude);
                    Log.i("dfvdcdcvf", "onActivityResult: " + mStringLatitude);
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("sfsfcfefd", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}