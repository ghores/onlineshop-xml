package com.example.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.onlineshop.handlers.CardDBHandler;
import com.example.onlineshop.mock.MockDataHandler;
import com.example.onlineshop.models.CardItem;
import com.example.onlineshop.models.Color;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.Size;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import java.util.Objects;

public class ProductDetailsActivity extends AppCompatActivity {

    private Product product;
    private TextView price, name, description;
    private ImageView image;
    private Button btnAddToCard;
    private LinearLayout mainView;
    private ChipGroup sizeChips, colorChips;
    private List<Size> sizes;
    private List<Color> colors;

    private Size selectedSize = null;
    private Color selectedColor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        //region bind views
        bindViews();
        //endregion

        //region product details
        product = (Product) Objects.requireNonNull(getIntent().getExtras()).get("product");
        price.setText(product.getPrice() + "$");
        name.setText(product.getName());
        image.setImageDrawable(getResources().getDrawable(product.getImage(), null));
        //endregion

        //region fill colors and sizes
        fillColors();
        fillSizes();
        //endregion

        //region handle sizes
        for (Size size : sizes) {
            Chip chip = new Chip(this);
            chip.setText(size.getName());
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        selectedSize = size;
                    } else {
                        selectedSize = null;
                    }
                }
            });
            sizeChips.addView(chip);
        }
        //endregion

        //region handle colors
        for (Color color : colors) {
            Chip chip = new Chip(this);
            chip.setText(color.getName());
            chip.setCheckable(true);
            chip.setTextColor(android.graphics.Color.parseColor(color.getValue()));
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        selectedColor = color;
                    } else {
                        selectedColor = null;
                    }
                }
            });
            colorChips.addView(chip);
        }
        //endregion

        //region handle add to card
        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSize == null) {
                    Snackbar.make(mainView, "please selected a size", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (selectedColor == null) {
                    Snackbar.make(mainView, "please selected a color", Snackbar.LENGTH_LONG).show();
                    return;
                }
                try {

                    CardDBHandler dbHandler = new CardDBHandler(ProductDetailsActivity.this);
                    CardItem cardItem = new CardItem();
                    cardItem.setProduct(product);
                    cardItem.setSize(selectedSize);
                    cardItem.setColor(selectedColor);
                    cardItem.setQuantity(1);
                    dbHandler.addToBasket(cardItem);
                } catch (Exception e) {
                    Toast.makeText(ProductDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                finish();
                ProductDetailsActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        //endregion
    }

    private void bindViews() {
        price = findViewById(R.id.product_price);
        name = findViewById(R.id.product_name);
        image = findViewById(R.id.product_image);
        description = findViewById(R.id.product_description);
        sizeChips = findViewById(R.id.chip_product_sizes);
        colorChips = findViewById(R.id.chip_product_colors);
        btnAddToCard = findViewById(R.id.btn_add_to_card);
        mainView = findViewById(R.id.product_detail_view);
    }

    private void fillColors() {
        colors = MockDataHandler.getColors();
    }

    private void fillSizes() {
        sizes = MockDataHandler.getSizes();
    }
}