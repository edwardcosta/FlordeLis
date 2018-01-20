package com.flordelis.flordelis.Screens.Container;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.flordelis.flordelis.R;

/**
 * Created by Sala on 20/01/2018.
 */

public class UserFragment extends Fragment {

    private View parentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_user,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        return parentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
