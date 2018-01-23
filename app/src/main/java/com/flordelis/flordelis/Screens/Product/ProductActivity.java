package com.flordelis.flordelis.Screens.Product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;

/**
 * Created by Sala on 23/01/2018.
 */

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final SimpleDraweeView simpleDraweeView = findViewById(R.id.activity_product_image);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String string = bundle.getString("product_image");
            Product product = (Product) bundle.getSerializable("product");
            simpleDraweeView.setTransitionName(string);
            if(product != null && product.getImages() != null && !product.getImages().isEmpty())
                simpleDraweeView.setImageURI(product.getImages().get(0));
        }

        postponeEnterTransition();
        simpleDraweeView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        simpleDraweeView.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                }
        );
    }
}
