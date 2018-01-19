package com.flordelis.flordelis.Utils.Product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;

import java.util.List;

/**
 * Created by Sala on 19/01/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_product,parent,false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);
        ProductHolder productHolder = (ProductHolder) holder;
        productHolder.data = product;
        productHolder.initLayout();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
