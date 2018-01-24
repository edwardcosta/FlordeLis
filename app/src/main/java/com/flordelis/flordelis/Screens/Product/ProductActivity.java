package com.flordelis.flordelis.Screens.Product;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.auth.AuthUI;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Screens.Authentication.LoginActivity;
import com.flordelis.flordelis.Screens.User.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * Created by Sala on 23/01/2018.
 */

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Product product;
    private SimpleDraweeView _img;
    private TextView _name;
    private TextView _id;
    private TextView _buyed_price;
    private TextView _sell_price;
    private TextView _situation;
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

        toolbar = (Toolbar) findViewById(R.id.activity_product_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.activity_product_appbar).bringToFront();

        _img = findViewById(R.id.activity_product_image);
        _name = findViewById(R.id.activity_product_name);
        _id = findViewById(R.id.activity_product_id);
        _buyed_price = findViewById(R.id.activity_product_buyed_price);
        _sell_price = findViewById(R.id.activity_product_sell_price);
        _situation = findViewById(R.id.activity_product_situation);
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
            String buyed_price = NumberFormat.getCurrencyInstance().format(Float.parseFloat(product.getBuyedPrice()));
            _buyed_price.setText(buyed_price);
            String sell_price = NumberFormat.getCurrencyInstance().format(Float.parseFloat(product.getSellPrice()));
            _sell_price.setText(sell_price);
            _situation.setText(product.getSituation());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit_product:
                Toast.makeText(this, "Editar Produto clicado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
