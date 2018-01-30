package com.flordelis.flordelis.Screens.Product;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.auth.AuthUI;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.Model.User;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Screens.Authentication.LoginActivity;
import com.flordelis.flordelis.Screens.Product.Fragment.ProductEditFragment;
import com.flordelis.flordelis.Screens.Product.Fragment.ProductFragment;
import com.flordelis.flordelis.Screens.User.Fragment.UserEditFragment;
import com.flordelis.flordelis.Screens.User.Fragment.UserFragment;
import com.flordelis.flordelis.Screens.User.UserActivity;
import com.flordelis.flordelis.Utils.StaticValues.ProductValues;
import com.flordelis.flordelis.Utils.StaticValues.UserValues;
import com.flordelis.flordelis.Utils.TimeStamp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * Created by Sala on 23/01/2018.
 */

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private MenuItem _editProduct;

    private Product product;
    private SimpleDraweeView _img;

    private Fragment fragment;
    private FragmentManager fragmentManager;

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

        fragmentManager = getSupportFragmentManager();

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String string = bundle.getString("product_image");
            product = (Product) bundle.getSerializable("product");
            _img.setTransitionName(string);
            if(product != null && product.getImages() != null && !product.getImages().isEmpty())
                _img.setImageURI(product.getImages().get(0));
            Bundle _bundle = new Bundle();
            _bundle.putSerializable("product",product);

            final FragmentTransaction fragmentTransaction;
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment = fragmentManager.findFragmentById(R.id.activity_product_fragment);

            if (fragment != null) {
                fragmentTransaction.remove(fragment);
            }

            fragment = new ProductFragment();
            fragment.setArguments(_bundle);
            fragmentTransaction.add(R.id.activity_product_fragment, fragment).commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.findFragmentById(R.id.activity_product_fragment) instanceof ProductEditFragment){
            fragmentManager.popBackStack();
            _editProduct.setVisible(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit_product:
                _editProduct = item;
                _editProduct.setVisible(false);
                fragment = new ProductEditFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("product",product);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_product_fragment,fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
