package com.flordelis.flordelis.Request;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Sala on 29/01/2018.
 */
public class StorageRequest {
    private static final String url = "gs://flor-de-lis-store-2baa7.appspot.com";

    public static StorageReference getProductsImagesReference(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReferenceFromUrl(url).child("products").child("images");
    }

    public static StorageReference getPathProductImages(String productId){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReferenceFromUrl(url).child("products").child("images").child(productId);
    }

    public static void uploadFromImageView(SimpleDraweeView imageView, StorageReference storageReference,
                                           final UploadImageCallBack uploadImageCallBack){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                uploadImageCallBack.uploadImageCallBack(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                uploadImageCallBack.uploadImageCallBack(downloadUrl);
            }
        });

    }

    public interface UploadImageCallBack{
        void uploadImageCallBack(Uri uri);
    }
}
