package com.flordelis.flordelis.Screens.Main.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Request.DatabaseRequest;
import com.flordelis.flordelis.Screens.Product.ProductActivity;
import com.flordelis.flordelis.Utils.Product.ProductAdapter;
import com.flordelis.flordelis.Utils.StaticValues.ProductValues;
import com.flordelis.flordelis.Utils.SwipeToAction;
import com.flordelis.flordelis.Utils.TimeStamp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sala on 19/01/2018.
 */

public class ProductListFragment extends Fragment {

    private View parentView;
    private Spinner spinner;

    private FirebaseFirestore db;
    private FirebaseUser user;

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeToAction swipeToAction;
    private FloatingActionButton fab;

    private List<Product> products = new ArrayList<>();
    private List<Product> queryColor = new ArrayList<>();
    private List<Product> queryProduct = new ArrayList<>();
    private List<Product> queryID = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        spinner = getActivity().findViewById(R.id.activity_main_spinner);

        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        onQuery(ProductValues.PRODUCT_SITUATION_ACTIVE);
                        break;
                    case 1:
                        onQuery(ProductValues.PRODUCT_SITUATION_SOLD);
                        break;
                    case 2:
                        onQuery(ProductValues.PRODUCT_SITUATION_DELETED);
                        break;
                    default:
                        onQuery(ProductValues.PRODUCT_SITUATION_ACTIVE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_list,container,false);

        recyclerView = (RecyclerView) parentView.findViewById(R.id.activity_main_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.fragment_list_swipetorefresh);
        fab = (FloatingActionButton) parentView.findViewById(R.id.fragment_fab);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(productAdapter);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Product>() {
            @Override
            public boolean swipeLeft(Product itemData, SwipeToAction.ViewHolder viewHolder) {
                products.remove(itemData);
                final Product _itemData = itemData;
                final SwipeToAction.ViewHolder _viewHolder = viewHolder;
                Map<String, Object> data = new HashMap<>();
                data.put("situation", ProductValues.PRODUCT_SITUATION_DELETED);
                data.put("datetimeDeleted", TimeStamp.getTimestamp());
                data.put("deletedBy",user.getUid());
                db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                        .document(_itemData.getId())
                        .set(data, SetOptions.merge());
                onRefreshContent();
                Snackbar mySnackbar = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                        "Deletado", Snackbar.LENGTH_LONG);
                mySnackbar.setAction("Desfazer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        products.add(_itemData);
                        Map<String, Object> data = new HashMap<>();
                        data.put("situation", ProductValues.PRODUCT_SITUATION_ACTIVE);
                        data.put("datetimeDeleted", 0);
                        data.put("deletedBy","");
                        db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                                .document(_itemData.getId())
                                .set(data, SetOptions.merge());
                        onRefreshContent();
                        Snackbar snackbar1 = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                                "Desfeito", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
                mySnackbar.show();
                return false;
            }

            @Override
            public boolean swipeRight(Product itemData, SwipeToAction.ViewHolder viewHolder) {
                products.remove(itemData);
                final Product _itemData = itemData;
                final SwipeToAction.ViewHolder _viewHolder = viewHolder;
                Map<String, Object> data = new HashMap<>();
                data.put("situation", ProductValues.PRODUCT_SITUATION_SOLD);
                data.put("datetimeSold", TimeStamp.getTimestamp());
                data.put("soldBy",user.getUid());
                db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                        .document(_itemData.getId())
                        .set(data, SetOptions.merge());
                onRefreshContent();
                Snackbar mySnackbar = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                        "Vendido", Snackbar.LENGTH_LONG);
                mySnackbar.setAction("Desfazer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        products.add(_itemData);
                        Map<String, Object> data = new HashMap<>();
                        data.put("situation", ProductValues.PRODUCT_SITUATION_ACTIVE);
                        data.put("datetimeSold", 0);
                        data.put("soldBy","");
                        db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                                .document(_itemData.getId())
                                .set(data, SetOptions.merge());
                        onRefreshContent();
                        Snackbar snackbar1 = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                                "Desfeito", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
                mySnackbar.show();
                return false;
            }

            @Override
            public void onClick(Product itemData, View view) {
                SimpleDraweeView _productImg = view.findViewById(R.id.card_product_image);
                _productImg.setLegacyVisibilityHandlingEnabled(true);
                _productImg.setTransitionName("product_image" + itemData.getId());
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("product_image",_productImg.getTransitionName());
                intent.putExtra("product",itemData);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(getActivity(), _productImg, _productImg.getTransitionName());
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onLongClick(Product itemData, View view) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshContent();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar1 = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                        "FAB clicado", Snackbar.LENGTH_SHORT);
                snackbar1.show();
            }
        });

        return parentView;
    }

    private void onQuery(String query){
        swipeRefreshLayout.setRefreshing(true);
        products.clear();
        DatabaseRequest.getProductQuery(query, new DatabaseRequest.ProductCallback() {
            @Override
            public void productCallback(List<Product> products) {
                ProductListFragment.this.products = products;
                onRefreshContent();
            }
        });
    }

    private void onRefreshContent(){
        productAdapter = new ProductAdapter(this.getContext(), products);
        recyclerView.setAdapter(productAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onSearch(String _search){
        String search = _search.trim();

        if(!search.isEmpty()) {
            int strlen = search.length();
            String frontString = search.substring(0,strlen - 1);
            String finalString = search.substring(strlen - 1, search.length());

            char c = finalString.charAt(0);
            c++;

            String endString = frontString + c;

            queryColor.clear();
            queryID.clear();
            queryProduct.clear();
            db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                    .whereGreaterThanOrEqualTo("id", search)
                    .whereLessThan("id",endString)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            queryID.add(product);
                        }
                        showSearchResults();
                    }
                }
            });
            db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                    .whereGreaterThanOrEqualTo("productName", search)
                    .whereLessThan("productName",endString)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            queryProduct.add(product);
                        }
                        showSearchResults();
                    }
                }
            });
            db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                    .whereGreaterThanOrEqualTo("color", search)
                    .whereLessThan("color",endString)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            queryColor.add(product);
                        }
                        showSearchResults();
                    }
                }
            });
        }
    }

    public void onSearchCancel(){
        onRefreshContent();
    }

    private void showSearchResults(){
        List<Product> searchResult = new ArrayList<>();
        searchResult.addAll(queryID);
        searchResult.addAll(queryColor);
        searchResult.addAll(queryProduct);

        productAdapter = new ProductAdapter(this.getContext(), searchResult);
        recyclerView.setAdapter(productAdapter);
    }
}
