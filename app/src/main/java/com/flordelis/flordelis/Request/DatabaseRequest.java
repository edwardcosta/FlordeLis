package com.flordelis.flordelis.Request;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.flordelis.flordelis.Model.Product;
import com.flordelis.flordelis.Model.User;
import com.flordelis.flordelis.Utils.StaticValues.ProductValues;
import com.flordelis.flordelis.Utils.StaticValues.UserValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sala on 29/01/2018.
 */

public class DatabaseRequest {
    public static void getUser(String userID, final UserCallback userCallback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(UserValues.USER_DATABASE_REFERENCE).document(userID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    userCallback.userCallback(task.getResult().toObject(User.class));
                } else {
                    userCallback.userCallback(null);
                }
            }
        });
    }

    public static void getProductQuery(String query, final ProductCallback productCallback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE)
                .whereEqualTo("situation",query)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            new GetProductsAsync(task.getResult(),productCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            productCallback.productCallback(null);
                        }
                    }
                });
    }

    public static void getProduct(String productID, final ProductCallback productCallback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ProductValues.PRODUCT_DATABASE_REFERENCE).document(productID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            new GetProductsAsync(task.getResult(),productCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            productCallback.productCallback(null);
                        }
                    }
                });
    }

    public interface UserCallback {
        void userCallback(User user);
    }
    public interface ProductCallback {
        void productCallback(List<Product> products);
    }

    private static class GetProductsAsync extends AsyncTask<Void,Void,List<Product>>{

        private ProductCallback pc;
        private QuerySnapshot qs;
        private DocumentSnapshot ds;

        GetProductsAsync(DocumentSnapshot ds, ProductCallback pc){
            this.pc = pc;
            this.ds = ds;
        }

        GetProductsAsync(QuerySnapshot qs, ProductCallback pc){
            this.pc = pc;
            this.qs = qs;
        }

        @Override
        protected List<Product> doInBackground(Void... documentSnapshots) {
            List<Product> productList = new ArrayList<>();
            if(qs != null) {
                for (DocumentSnapshot ds : qs) {
                    productList.add(ds.toObject(Product.class));
                }
            } else if(ds != null) {
                    productList.add(ds.toObject(Product.class));
            }
            Collections.sort(productList);

            return productList;
        }

        @Override
        protected void onPostExecute(List<Product> list) {
            super.onPostExecute(list);
            pc.productCallback(list);
        }
    }
}
