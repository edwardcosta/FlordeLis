package com.flordelis.flordelis.Screens.User.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Sala on 26/01/2018.
 */

public class UserFragment extends Fragment {

    private View parentView;

    private FirebaseUser user;

    private TextView _name;
    private TextView _email;
    private TextView _phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_user,container,false);

        _name = parentView.findViewById(R.id.fragment_user_name);
        _email = parentView.findViewById(R.id.fragment_user_email);
        _phone = parentView.findViewById(R.id.fragment_user_phone);

        if(user != null){
            _name.setText(user.getDisplayName());
            _email.setText(user.getEmail());
            _phone.setText(user.getPhoneNumber());
        }

        return parentView;
    }
}
