package com.example.onlineshop.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.ProductDetailsActivity;
import com.example.onlineshop.R;
import com.example.onlineshop.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> dataList;
    private Activity activity;

    public ProductAdapter(Activity activity, List<Product> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.product_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = dataList.get(position);

        TextView title = holder.title;
        ImageView image = holder.image;
        TextView price = holder.price;

        title.setText(product.getName());
        price.setText(product.getPrice() + "$");
        image.setImageDrawable(activity.getResources().getDrawable(product.getImage()));
        //image.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(),product.getImage(),null));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productIntent = new Intent(activity, ProductDetailsActivity.class);
                productIntent.putExtra("product", product);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair(title, "product_name");
                pairs[1] = new Pair(price, "product_price");
                pairs[2] = new Pair(image, "product_image");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
                activity.startActivity(productIntent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            price = itemView.findViewById(R.id.product_price);

        }
    }
}