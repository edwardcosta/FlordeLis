package com.flordelis.flordelis.Screens.Product;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;

import org.w3c.dom.Text;

/**
 * Created by Sala on 23/01/2018.
 */

public class ProductActivity extends AppCompatActivity {

    private Product product;
    private SimpleDraweeView _img;
    private TextView _name;
    private TextView _id;
    private TextView _buyed_price;
    private TextView _sell_price;
    private TextView _situation;
    private TextView _quantity;
    private TextView _color;
    private TextView _size;
    private TextView _provider;
    private TextView _description;
    private TextView _added_by;
    private TextView _added_date;
    private TextView _edited_by;
    private TextView _edited_date;
    private TextView _deleted_by;
    private TextView _deleted_date;
    private TextView _sold_by;
    private TextView _sold_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        _img = findViewById(R.id.activity_product_image);
        _name = findViewById(R.id.activity_product_name);
        _id = findViewById(R.id.activity_product_id);
        _buyed_price = findViewById(R.id.activity_product_buyed_price);
        _sell_price = findViewById(R.id.activity_product_sell_price);
        _situation = findViewById(R.id.activity_product_situation);
        _quantity = findViewById(R.id.activity_product_quantity);
        _color = findViewById(R.id.activity_product_color);
        _size = findViewById(R.id.activity_product_size);
        _provider = findViewById(R.id.activity_product_provider);
        _description = findViewById(R.id.activity_product_description);
        _added_by = findViewById(R.id.activity_product_added_by);
        _added_date = findViewById(R.id.activity_product_added_date);
        _edited_by = findViewById(R.id.activity_product_edited_by);
        _edited_date = findViewById(R.id.activity_product_edited_date);
        _deleted_by = findViewById(R.id.activity_product_deleted_by);
        _deleted_date = findViewById(R.id.activity_product_deleted_date);
        _sold_by = findViewById(R.id.activity_product_sold_by);
        _sold_date = findViewById(R.id.activity_product_sold_date);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String string = bundle.getString("product_image");
            product = (Product) bundle.getSerializable("product");
            _img.setTransitionName(string);
            if(product != null && product.getImages() != null && !product.getImages().isEmpty())
                _img.setImageURI(product.getImages().get(0));
        }

        postponeEnterTransition();
        _img.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        _img.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(product != null){
            _name.setText(product.getProductName());
            _id.setText(product.getId());
            _buyed_price.setText(product.getBuyedPrice());
            _sell_price.setText(product.getSalePrice());
            _situation.setText(product.getSituation());
            _quantity.setText(String.valueOf(product.getQuantity()));
            _color.setText(product.getColor());
            _size.setText(product.getSize());
            _provider.setText(product.getProviderName());
            _description.setText(product.getDescription());
            _added_by.setText(product.getAddedBy());
            _added_date.setText(String.valueOf(product.getDatetimeCreated()));
            _edited_by.setText(product.getEditedBy());
            _edited_date.setText(String.valueOf(product.getDatetimeEdited()));
            _deleted_by.setText(product.getDeletedBy());
            _deleted_date.setText(String.valueOf(product.getDatetimeDeleted()));
            _sold_by.setText(product.getSoldBy());
            _sold_date.setText(String.valueOf(product.getDatetimeSold()));
        }
    }
}
