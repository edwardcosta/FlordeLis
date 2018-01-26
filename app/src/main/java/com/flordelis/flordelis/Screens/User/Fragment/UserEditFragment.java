package com.flordelis.flordelis.Screens.User.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Screens.Authentication.LoginActivity;
import com.flordelis.flordelis.Utils.StaticValues.UserValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sala on 26/01/2018.
 */

public class UserEditFragment extends Fragment {

    private View parentView;

    private FirebaseFirestore db;
    private FirebaseUser user;

    private SimpleDraweeView _img;
    private SimpleDraweeView _back_img;
    private EditText _name;
    private EditText _email;
    private EditText _phone;
    private Button _change_img;
    private Button _change_back_img;
    private Button _delete;
    private Button _save;

    private Uri img;
    private Uri back_img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        _img = getActivity().findViewById(R.id.activity_user_image);
        _back_img = getActivity().findViewById(R.id.activity_user_back_image);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_edit_user,container,false);

        _name = parentView.findViewById(R.id.fragment_edit_user_name);
        _email = parentView.findViewById(R.id.fragment_edit_user_email);
        _phone = parentView.findViewById(R.id.fragment_edit_user_phone);
        _change_img = parentView.findViewById(R.id.fragment_edit_user_change_image);
        _change_back_img = parentView.findViewById(R.id.fragment_edit_user_change_back_image);
        _delete = parentView.findViewById(R.id.fragment_edit_user_delete);
        _save = parentView.findViewById(R.id.fragment_edit_user_save);

        _change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Trocar imagem de perfil",Toast.LENGTH_SHORT).show();
            }
        });

        _change_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Trocar imagem do fundo",Toast.LENGTH_SHORT).show();
            }
        });

        _delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteButton();
            }
        });

        _save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButton();
            }
        });

        return parentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _img.setOnClickListener(null);
        _back_img.setOnClickListener(null);
    }

    private void onDeleteButton(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Deletar Conta")
                .setMessage("Deletar a conta não pode ser desfeito. Deseja continuar?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(true)
                .create();
        alertDialog.show();
    }

    private void onSaveButton(){
        final String name = _name.getText().toString().trim();
        final String email = _email.getText().toString().trim();
        final String phone = _phone.getText().toString().trim();

        Map<String,Object> data = new HashMap<>();
        data.put("displayName",name);
        data.put("phone",phone);
        data.put("email",email);
        data.put("backImage",back_img.toString());

        db.collection(UserValues.USER_DATABASE_REFERENCE).document(user.getUid())
                .set(data, SetOptions.merge());

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(img)
                .build();
        user.updateEmail(email);
        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}
