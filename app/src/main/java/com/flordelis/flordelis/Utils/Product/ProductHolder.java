package com.flordelis.flordelis.Utils.Product;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Utils.SwipeToAction;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;



/**
 * Created by Sala on 19/01/2018.
 */

public class ProductHolder extends SwipeToAction.ViewHolder implements View.OnClickListener {

    private SimpleDraweeView _img;
    private TextView _title;
    private TextView _id;
    private TextView _price;

    public ProductHolder(View v) {
        super(v);

        _img = (SimpleDraweeView) v.findViewById(R.id.card_product_image);
        _title = (TextView) v.findViewById(R.id.card_product_title);
        _id = (TextView) v.findViewById(R.id.card_product_id);
        _price = (TextView) v.findViewById(R.id.card_product_price);

    }

    @Override
    public void onClick(View view) {
    }

    public void initLayout(){
        Product product = (Product) data;
        if(product != null) {
            List<String> images = product.getImages();
            if (images != null && !images.isEmpty())
                _img.setImageURI(images.get(0));
            if (product.getProductName() != null && !product.getProductName().isEmpty())
                _title.setText(product.getProductName());
            if(product.getId() != null && !product.getId().isEmpty())
                _id.setText(product.getId());
            if(product.getSellPrice() != null && !product.getSellPrice().isEmpty()){
                String price = NumberFormat.getCurrencyInstance().format(Float.parseFloat(product.getSellPrice()));
                _price.setText(price);
            }

        }
    }
}
