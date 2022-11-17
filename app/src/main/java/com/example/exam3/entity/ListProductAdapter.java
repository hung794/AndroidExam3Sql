package com.example.exam3.entity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.exam3.R;

import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;

public class ListProductAdapter extends BaseAdapter {
    private Activity activity;
    private List<Product> productList;

    public ListProductAdapter(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        view = layoutInflater.inflate(R.layout.item_product,
                parent, false);
        Product product = productList.get(position);
        TextView tvName = view.findViewById(R.id.name);
        TextView tvPrice = view.findViewById(R.id.price);
        tvName.setText(product.getName());
        String price = new DecimalFormat("#,###.00").format(product.getPrice());
        tvPrice.setText(price);
        return view;
    }
}
