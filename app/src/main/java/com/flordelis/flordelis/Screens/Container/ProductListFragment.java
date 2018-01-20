package com.flordelis.flordelis.Screens.Container;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Utils.Product.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;

/**
 * Created by Sala on 19/01/2018.
 */

public class ProductListFragment extends Fragment {

    private View parentView;

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeToAction swipeToAction;
    private FloatingActionButton fab;

    private List<Product> products = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Product p1 = new Product();
        p1.setProductName("Produto Teste 1");
        List<String> img1 = new ArrayList<>();
        img1.add("www.redparts.com.br/wp-content/uploads/produto_teste.jpg");
        p1.setImages(img1);

        Product p2 = new Product();
        p2.setProductName("Produto Teste 2");
        List<String> img2 = new ArrayList<>();
        img2.add("http://www.infostore.com.br/Assets/Produtos/SuperZoom/produto_teste_635859432774890946.jpg");
        p2.setImages(img2);

        Product p3 = new Product();
        p3.setProductName("Produto Teste 3");

        products.add(p1);
        products.add(p2);
        products.add(p3);

        productAdapter = new ProductAdapter(this.getContext(), products);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            public boolean swipeLeft(Product itemData) {
                Snackbar mySnackbar = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                        "Deletado", Snackbar.LENGTH_LONG);
                mySnackbar.setAction("Desfazer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                                "Desfeito", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
                mySnackbar.show();
                return false;
            }

            @Override
            public boolean swipeRight(Product itemData) {
                Snackbar mySnackbar = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                        "Vendido", Snackbar.LENGTH_LONG);
                mySnackbar.setAction("Desfazer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(parentView.findViewById(R.id.fragment_coordinator_layout),
                                "Desfeito", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
                mySnackbar.show();
                return false;
            }

            @Override
            public void onClick(Product itemData) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.content,new ProductFragment())
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onLongClick(Product itemData) {

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

    private void onRefreshContent(){
        productAdapter = new ProductAdapter(this.getContext(), products);
        recyclerView.setAdapter(productAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }
}
