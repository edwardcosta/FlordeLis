package com.flordelis.flordelis.Screens.User.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Screens.Authentication.LoginActivity;
import com.flordelis.flordelis.Utils.CheckPermissions;
import com.flordelis.flordelis.Utils.ImagePicker;
import com.flordelis.flordelis.Utils.StaticValues.UserValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

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

        _name.setText(user.getDisplayName());
        _email.setText(user.getEmail());
        _phone.setText(user.getPhoneNumber());

        _change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckPermissions.checkPermissions(getActivity(),CheckPermissions.READ_EXTERNAL_STORAGE) &&
                        CheckPermissions.checkPermissions(getActivity(),CheckPermissions.WRITE_EXTERNAL_STORAGE) &&
                        CheckPermissions.checkPermissions(getActivity(),CheckPermissions.CAMERA) ) {
                    ImagePicker.croperinoImagePicker(getActivity());
                    //ImagePicker.defaultImagePicker(getActivity());
                }
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
        SweetAlertDialog delete = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
        delete.setTitleText("Deseja deletar a conta?");
        delete.setContentText("Deletar a conta nao pode ser desfeito. Deseja continuar?");
        delete.setConfirmText("Sim").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(final SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.setTitleText("Deletando Conta")
                        .setContentText("")
                        .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        sweetAlertDialog.setTitleText("Conta deletada")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                });
            }
        });
        delete.setCancelText("Não").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        delete.setCancelable(true);
        delete.show();
    }

    private void onSaveButton(){
        final String name = _name.getText().toString().trim();
        final String email = _email.getText().toString().trim();
        final String phone = _phone.getText().toString().trim();

        final SweetAlertDialog save = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        save.setTitleText("Salvando");
        save.show();

        Map<String,Object> data = new HashMap<>();
        data.put("displayName",name);
        data.put("phone",phone);
        data.put("email",email);

        db.collection(UserValues.USER_DATABASE_REFERENCE).document(user.getUid())
                .set(data, SetOptions.merge());

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateEmail(email);
        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    save.setTitleText("Salvo")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    getFragmentManager().popBackStack();
                                }
                            })
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                } else {
                    save.setTitleText("Erro")
                    .setContentText("Não foi possível atualizar o perfil")
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImagePicker.croperinoActivityResult(requestCode, resultCode, data, getActivity(), new ImagePicker.ImageCallBack() {
            @Override
            public void imageCallback(Uri imageURI, boolean hasImage) {
                _img.setImageURI(imageURI);
            }
        });
    }
}
