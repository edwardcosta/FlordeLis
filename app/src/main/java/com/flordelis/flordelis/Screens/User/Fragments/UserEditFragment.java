package com.flordelis.flordelis.Screens.User.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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

        return parentView;
    }

    private void onDeleteButton(){

    }

    private void onSaveButton(){

    }
}
