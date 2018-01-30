package com.flordelis.flordelis.Screens.Product.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.Model.User;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Utils.StaticValues.ProductValues;
import com.flordelis.flordelis.Utils.StaticValues.UserValues;
import com.flordelis.flordelis.Utils.TimeStamp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.NumberFormat;

/**
 * Created by Sala on 26/01/2018.
 */

public class ProductEditFragment extends Fragment {

    private View parentView;

    private Product product;

    private EditText _name;
    private TextView _id;
    private EditText _buyed_price;
    private EditText _sell_price;
    private Spinner _situation;
    private AutoCompleteTextView _color;
    private Spinner _size_type_chooser;
    private LinearLayout _size_type;
    private CheckBox _bbSize;
    private Spinner _size;
    private Spinner _numeration_size;
    private Spinner _provider;
    private EditText _description;
    private Button _change_img;
    private Button _delete;
    private Button _save;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable("product");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_edit_product,container,false);

        _name = parentView.findViewById(R.id.fragment_edit_product_name);
        _id = parentView.findViewById(R.id.fragment_edit_product_id);
        _buyed_price = parentView.findViewById(R.id.fragment_edit_product_buyed_price);
        _sell_price = parentView.findViewById(R.id.fragment_edit_product_sell_price);
        _situation = parentView.findViewById(R.id.fragment_edit_user_situation);
        _color = parentView.findViewById(R.id.fragment_edit_product_color);
        _size_type_chooser = parentView.findViewById(R.id.fragment_edit_product_size_type_chooser_spinner);
        _size_type = parentView.findViewById(R.id.fragment_edit_product_size_choice_layout);
        _bbSize = parentView.findViewById(R.id.fragment_edit_product_bbsize);
        _size = parentView.findViewById(R.id.fragment_edit_product_size_spinner);
        _numeration_size = parentView.findViewById(R.id.fragment_edit_product_numeration_spinner);
        _provider = parentView.findViewById(R.id.fragment_edit_product_provider);
        _description = parentView.findViewById(R.id.fragment_edit_product_description);
        _change_img = parentView.findViewById(R.id.fragment_edit_product_change_image);
        _delete = parentView.findViewById(R.id.fragment_edit_product_delete);
        _save = parentView.findViewById(R.id.fragment_edit_product_save);

        if(product != null){
            _name.setText(product.getProductName());
            _id.setText(product.getId());
            String buyed_price = NumberFormat.getCurrencyInstance().format(Float.parseFloat(product.getBuyedPrice()));
            _buyed_price.setText(buyed_price);
            String sell_price = NumberFormat.getCurrencyInstance().format(Float.parseFloat(product.getSellPrice()));
            _sell_price.setText(sell_price);
            //_situation.setText(product.getSituation());
            _color.setText(product.getColor());
            //_size.setText(product.getSize());
            //_provider.setText(product.getProviderName());
            _description.setText(product.getDescription());
        }

        return parentView;
    }
}
