package lk.aditi.ecom.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;

import java.util.ArrayList;
import java.util.List;

import lk.aditi.ecom.R;
import lk.aditi.ecom.adapter.filter.ColorAdapter;
import lk.aditi.ecom.adapter.filter.MultiSelectableTextAdapter;
import lk.aditi.ecom.adapter.filter.OnClickCallback;
import lk.aditi.ecom.models.ColorModel;

public class FilterActivity extends AppCompatActivity {



    int sartPrize = 1, endPrize, rating;
    String size = "", brand = "", category = "", categoryIds = "", brandIds = "", color = "", discount = "";
    private RangeSeekBar rb_Price;
    private LinearLayout ll_Categories;
    private RatingBar rb_Rating;
    private ImageView iv_back;
    private RecyclerView rv_Color, rv_Brand, rv_Categories, rv_Size, rv_Discount;
    private ExpansionLayout expansionPopularityLayout, expansionBrandsLayout, expansionCategoriesLayout, expansionPriceLayout, expansionRatingLayout,
            expansionShipedFromLayout, expansionColorLayout, expansionDiscountLayout;
    private TextView tv_PopularityDescription, tv_BrandsDescription, tv_CategoriesDescription, tv_PriceDescription, tv_RatingDescription,
            tv_ShipedFromDescription, tv_ChildShipedFromTitle, tv_ColorDescription, tv_DiscountDescription, tv_Price;
    private LinearLayout tv_ChildPopularityTitle, tv_ChildPriceLayout, tv_ChildRatingLayout,
            tv_ChildColorLayout, tv_ChildBrandsTitle, tv_ChildCategoriesTitle, tv_ChildDiscountLayout, ll_Main;
    private int activeStartPrice, activeEndPrice;

    private final List<ColorModel> activeColor = new ArrayList<>();
    private final List<String> activeBrands = new ArrayList<>();
    private final List<String> activeCategories = new ArrayList<>();
    private final List<String> activeSize = new ArrayList<>();
    private final List<String> activeDiscount = new ArrayList<>();

    private final List<String> brands = new ArrayList<>();
    private final List<String> categories = new ArrayList<>();
    private final List<ColorModel> colorList = new ArrayList<>();
    private final List<String> sizeList = new ArrayList<>();
    private final List<String> discountList = new ArrayList<>();

    private ColorAdapter colorAdapter;
    private MultiSelectableTextAdapter multiSelectableTextAdapter, brandAdapter, categoriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        init();
        setUpAnimation();
    }

    private void init() {


        ll_Categories = findViewById(R.id.ll_Categories);


        expansionPopularityLayout = findViewById(R.id.expansionPopularityLayout);
        expansionBrandsLayout = findViewById(R.id.expansionBrandsLayout);
        expansionPriceLayout = findViewById(R.id.expansionPriceLayout);
        expansionRatingLayout = findViewById(R.id.expansionRatingLayout);
        expansionShipedFromLayout = findViewById(R.id.expansionShipedFromLayout);
        expansionColorLayout = findViewById(R.id.expansionColorLayout);
        expansionDiscountLayout = findViewById(R.id.expansionDiscountLayout);
        expansionCategoriesLayout = findViewById(R.id.expansionCategoriesLayout);

        tv_ChildPopularityTitle = findViewById(R.id.tv_ParentPopularityTitle);
        tv_PopularityDescription = findViewById(R.id.tv_PopularityDescription);

        tv_ChildBrandsTitle = findViewById(R.id.tv_ChildBrandsTitle);
        tv_BrandsDescription = findViewById(R.id.tv_BrandsDescription);

        tv_ChildCategoriesTitle = findViewById(R.id.tv_ChildCategoriesTitle);
        tv_CategoriesDescription = findViewById(R.id.tv_CategoriesDescription);


        tv_ChildPriceLayout = findViewById(R.id.tv_ChildPriceLayout);
        tv_PriceDescription = findViewById(R.id.tv_PriceDescription);
        rb_Price = findViewById(R.id.rb_Price);
        tv_Price = findViewById(R.id.tv_Price);

        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(v -> {
            finish();
        });
        rb_Price.setProgress(Float.parseFloat("1"), Float.parseFloat("3000"));
        activeStartPrice = 1;
        activeEndPrice = 3000;
        String s = "₹" + (int) 1 + "" + " - " + "₹" + (int) 3000 + "";
        tv_Price.setText(s);
        tv_PriceDescription.setText(s);
        serUpPrice();

        tv_ChildRatingLayout = findViewById(R.id.tv_ChildRatingLayout);
        tv_RatingDescription = findViewById(R.id.tv_RatingDescription);
        rb_Rating = findViewById(R.id.rb_Rating);
        rb_Rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv_RatingDescription.setText((int) rb_Rating.getRating() + " Star");
            }
        });

        tv_ShipedFromDescription = findViewById(R.id.tv_ShipedFromDescription);
        tv_ChildShipedFromTitle = findViewById(R.id.tv_ChildShipedFromTitle);

        tv_ColorDescription = findViewById(R.id.tv_ColorDescription);
        tv_ChildColorLayout = findViewById(R.id.tv_ChildColorLayout);

        tv_DiscountDescription = findViewById(R.id.tv_DiscountDescription);
        tv_ChildDiscountLayout = findViewById(R.id.tv_ChildDiscountLayout);

        getColor();
        rv_Color = findViewById(R.id.rv_Color);
        rv_Color.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        colorAdapter = new ColorAdapter(colorList, activeColor, new OnClickCallback() {
            @Override
            public void onClick() {
                StringBuffer buffer = new StringBuffer();
                for (ColorModel model : activeColor)
                    buffer.append(model.getName() + ",");
                if (buffer.length() > 0)
                    buffer.deleteCharAt(buffer.length() - 1);
                tv_ColorDescription.setText(buffer.length() > 0 ? buffer : "Not selected");
            }
        });
        rv_Color.setAdapter(colorAdapter);

        getbrand();
        rv_Brand = findViewById(R.id.rv_Brand);
        rv_Brand.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        brandAdapter = new MultiSelectableTextAdapter(brands, activeBrands, new OnClickCallback() {
            @Override
            public void onClick() {
                StringBuffer buffer = new StringBuffer();
                StringBuffer ids = new StringBuffer();
                for (String model : activeBrands)
                    buffer.append(model + ",");
                if (buffer.length() > 0)
                    buffer.deleteCharAt(buffer.length() - 1);
                brand = buffer + "";
                brandIds = ids + "";
                tv_BrandsDescription.setText(buffer.length() > 0 ? buffer : "Not selected");
            }
        });
        rv_Brand.setAdapter(brandAdapter);


        getCategory();
        rv_Categories = findViewById(R.id.rv_Categories);
        rv_Categories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        categoriesAdapter = new MultiSelectableTextAdapter(categories, activeCategories, () -> {
            StringBuffer buffer = new StringBuffer();
            StringBuffer ids = new StringBuffer();

            for (String model : activeCategories)
                buffer.append(model + ",");


            if (buffer.length() > 0)
                buffer.deleteCharAt(buffer.length() - 1);
            category = buffer + "";
            categoryIds = ids + "";
            tv_CategoriesDescription.setText(buffer.length() > 0 ? buffer : "Not selected");
        });
        rv_Categories.setAdapter(categoriesAdapter);


        getSize();
        rv_Size = findViewById(R.id.rv_Size);
        rv_Size.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        multiSelectableTextAdapter = new MultiSelectableTextAdapter(sizeList, activeSize, new OnClickCallback() {
            @Override
            public void onClick() {
                StringBuffer buffer = new StringBuffer();
                for (String model : activeSize)
                    buffer.append(model + ",");
                if (buffer.length() > 0)
                    buffer.deleteCharAt(buffer.length() - 1);
                tv_PopularityDescription.setText(buffer.length() > 0 ? buffer : "Not selected");
            }
        });
        rv_Size.setAdapter(multiSelectableTextAdapter);

        getDiscount();
        rv_Discount = findViewById(R.id.rv_Discount);
        rv_Discount.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        multiSelectableTextAdapter = new MultiSelectableTextAdapter(discountList, activeDiscount, new OnClickCallback() {
            @Override
            public void onClick() {
                StringBuffer buffer = new StringBuffer();
                for (String model : activeDiscount)
                    buffer.append(model + ",");
                if (buffer.length() > 0)
                    buffer.deleteCharAt(buffer.length() - 1);
                tv_DiscountDescription.setText(buffer.length() > 0 ? buffer : "Not selected");
            }
        });
        rv_Discount.setAdapter(multiSelectableTextAdapter);


    }

    private void getColor() {
        colorList.add(new ColorModel(0xFFFFFFFF, false, "White"));
        colorList.add(new ColorModel(0xFF000000, false, "Black"));
        colorList.add(new ColorModel(0xFF0019FE, false, "Blue"));
        colorList.add(new ColorModel(0xFF00DC1D, false, "Green"));
        colorList.add(new ColorModel(0xFFFFC107, false, "Yellow"));
        colorList.add(new ColorModel(0xFFFF113F, false, "Red"));
    }

    private void serUpPrice() {
        rb_Price.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                String s = "₹" + (int) leftValue + "" + " - " + "₹" + (int) rightValue + "";
                tv_Price.setText(s);
                tv_PriceDescription.setText(s);
                activeStartPrice = (int) leftValue;
                activeEndPrice = (int) rightValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
    }

    private void getSize() {
        sizeList.add("S");
        sizeList.add("M");
        sizeList.add("L");
        sizeList.add("XL");
    }

    private void getCategory() {
        categories.add("S");
        categories.add("M");
        categories.add("L");
        categories.add("XL");
    }

    private void getbrand() {
        brands.add("S");
        brands.add("M");
        brands.add("L");
        brands.add("XL");
    }

    private void getDiscount() {
        discountList.add("10% Off");
        discountList.add("25% Off");
        discountList.add("35% Off");
        discountList.add("50% Off");
    }


    private void setUpAnimation() {
        popularityAnimation();
        brandAnimation();
        categoriesAnimation();
        priceAnimation();
        ratingAnimation();
        shipedFromAnimation();
        colorAnimation();
        discountAnimation();
    }

    private void colorAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_ColorDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildColorLayout.isShown()) {
                            tv_ChildColorLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_ColorDescription.setVisibility(View.INVISIBLE);
                            tv_ChildColorLayout.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildColorLayout, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionColorLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });


    }

    private void shipedFromAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_ShipedFromDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildShipedFromTitle.isShown()) {
                            tv_ChildShipedFromTitle.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_ShipedFromDescription.setVisibility(View.INVISIBLE);
                            tv_ChildShipedFromTitle.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildShipedFromTitle, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionShipedFromLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });
    }

    private void ratingAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_RatingDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildRatingLayout.isShown()) {
                            tv_ChildRatingLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_RatingDescription.setVisibility(View.INVISIBLE);
                            tv_ChildRatingLayout.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildRatingLayout, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionRatingLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });
    }

    private void priceAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_PriceDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildPriceLayout.isShown()) {
                            tv_ChildPriceLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_PriceDescription.setVisibility(View.INVISIBLE);
                            tv_ChildPriceLayout.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildPriceLayout, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionPriceLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });
    }

    private void popularityAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_PopularityDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildPopularityTitle.isShown()) {
                            tv_ChildPopularityTitle.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_PopularityDescription.setVisibility(View.INVISIBLE);
                            tv_ChildPopularityTitle.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildPopularityTitle, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionPopularityLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });

    }

    private void brandAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_BrandsDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildBrandsTitle.isShown()) {
                            tv_ChildBrandsTitle.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_BrandsDescription.setVisibility(View.INVISIBLE);
                            tv_ChildBrandsTitle.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildBrandsTitle, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionBrandsLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });

    }

    private void categoriesAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_CategoriesDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildCategoriesTitle.isShown()) {
                            tv_ChildCategoriesTitle.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_CategoriesDescription.setVisibility(View.INVISIBLE);
                            tv_ChildCategoriesTitle.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildCategoriesTitle, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionCategoriesLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });

    }

    private void discountAnimation() {
        SlideUp slideUp = new SlideUpBuilder(tv_DiscountDescription)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100 && tv_ChildDiscountLayout.isShown()) {
                            tv_ChildDiscountLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            tv_DiscountDescription.setVisibility(View.INVISIBLE);
                            tv_ChildDiscountLayout.setVisibility(View.VISIBLE);
                            StartSmartAnimation.startAnimation(tv_ChildDiscountLayout, AnimationType.SlideInDown, 500, 0, true);
                        }
                    }
                })
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.TOP).build();

        expansionDiscountLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded)
                    slideUp.hide();
                else
                    slideUp.show();

            }
        });

    }


}