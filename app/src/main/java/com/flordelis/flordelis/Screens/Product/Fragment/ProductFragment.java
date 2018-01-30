package com.flordelis.flordelis.Screens.Product.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.Model.User;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Utils.StaticValues.ProductValues;
import com.flordelis.flordelis.Utils.StaticValues.UserValues;
import com.flordelis.flordelis.Utils.TimeStamp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;

/**
 * Created by Sala on 26/01/2018.
 */

public class ProductFragment extends Fragment {
    
    private View parentView;
    
    private Product product;

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
    private LinearLayout _edited_layout;
    private TextView _edited_by;
    private TextView _edited_date;
    private LinearLayout _deleted_layout;
    private TextView _deleted_by;
    private TextView _deleted_date;
    private LinearLayout _sold_layout;
    private TextView _sold_by;
    private TextView _sold_date;

    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        product = (Product) getArguments().getSerializable("product");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_product,container,false);

        _name = parentView.findViewById(R.id.fragment_product_name);
        _id = parentView.findViewById(R.id.fragment_product_id);
        _buyed_price = parentView.findViewById(R.id.fragment_product_buyed_price);
        _sell_price = parentView.findViewById(R.id.fragment_product_sell_price);
        _situation = parentView.findViewById(R.id.fragment_product_situation);
        _color = parentView.findViewById(R.id.fragment_product_color);
        _size = parentView.findViewById(R.id.fragment_product_size);
        _provider = parentView.findViewById(R.id.fragment_product_provider);
        _description = parentView.findViewById(R.id.fragment_product_description);
        _added_by = parentView.findViewById(R.id.fragment_product_added_by);
        _added_date = parentView.findViewById(R.id.fragment_product_added_date);
        _edited_layout = parentView.findViewById(R.id.fragment_product_edited_layout);
        _edited_by = parentView.findViewById(R.id.fragment_product_edited_by);
        _edited_date = parentView.findViewById(R.id.fragment_product_edited_date);
        _deleted_layout = parentView.findViewById(R.id.fragment_product_deleted_layout);
        _deleted_by = parentView.findViewById(R.id.fragment_product_deleted_by);
        _deleted_date = parentView.findViewById(R.id.fragment_product_deleted_date);
        _sold_layout = parentView.findViewById(R.id.fragment_product_sold_layout);
        _sold_by = parentView.findViewById(R.id.fragment_product_sold_by);
        _sold_date = parentView.findViewById(R.id.fragment_product_sold_date);

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
            db.collection(UserValues.USER_DATABASE_REFERENCE).document(product.getAddedBy())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        User user = task.getResult().toObject(User.class);
                        _added_by.setText(user.getDisplayName());
                    }
                }
            });
            _added_date.setText(TimeStamp.getDateCurrentTimeZone(product.getDatetimeCreated()));
            if(product.getDatetimeEdited() != 0){
                _edited_layout.setVisibility(View.VISIBLE);
                _edited_by.setText(product.getEditedBy());
                db.collection(UserValues.USER_DATABASE_REFERENCE).document(product.getEditedBy())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            User user = task.getResult().toObject(User.class);
                            _edited_by.setText(user.getDisplayName());
                        }
                    }
                });
                _edited_date.setText(TimeStamp.getDateCurrentTimeZone(product.getDatetimeEdited()));
            }
            switch (product.getSituation()) {
                case ProductValues.PRODUCT_SITUATION_SOLD:
                    _sold_layout.setVisibility(View.VISIBLE);
                    _sold_by.setText(product.getSoldBy());
                    db.collection(UserValues.USER_DATABASE_REFERENCE).document(product.getSoldBy())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                User user = task.getResult().toObject(User.class);
                                _sold_by.setText(user.getDisplayName());
                            }
                        }
                    });
                    _sold_date.setText(TimeStamp.getDateCurrentTimeZone(product.getDatetimeSold()));
                    break;
                case ProductValues.PRODUCT_SITUATION_DELETED:
                    _deleted_layout.setVisibility(View.VISIBLE);
                    _deleted_by.setText(product.getDeletedBy());
                    db.collection(UserValues.USER_DATABASE_REFERENCE).document(product.getDeletedBy())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                User user = task.getResult().toObject(User.class);
                                _deleted_by.setText(user.getDisplayName());
                            }
                        }
                    });
                    _deleted_date.setText(TimeStamp.getDateCurrentTimeZone(product.getDatetimeDeleted()));
                    break;
                default:
                    break;
            }
        }
        
        return parentView;
    }
}
