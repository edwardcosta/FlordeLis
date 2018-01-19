package com.flordelis.flordelis.Utils.Product;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;

import java.util.List;

import co.dift.ui.SwipeToAction;

/**
 * Created by Sala on 19/01/2018.
 */

public class ProductHolder extends SwipeToAction.ViewHolder implements View.OnClickListener {

    private SimpleDraweeView _img;
    private TextView _title;

    public ProductHolder(View v) {
        super(v);

        _img = (SimpleDraweeView) v.findViewById(R.id.card_product_image);
        _title = (TextView) v.findViewById(R.id.card_product_title);
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
        }
    }
}
