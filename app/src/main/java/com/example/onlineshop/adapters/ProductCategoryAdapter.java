package com.example.onlineshop.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.models.ProductCategory;

import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {

    private List<ProductCategory> dataList;
    private Activity activity;

    public ProductCategoryAdapter(List<ProductCategory> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.product_category_layout, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCategory category = dataList.get(position);

        TextView title = holder.title;
        ImageView image = holder.image;

        title.setText(category.getName());
        image.setImageDrawable(activity.getResources().getDrawable(category.getImage(), activity.getTheme()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_category_image);
            title = itemView.findViewById(R.id.product_category_title);
        }
    }
}
