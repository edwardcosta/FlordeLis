package com.flordelis.flordelis.Screens.Provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flordelis.flordelis.R;

/**
 * Created by Sala on 20/01/2018.
 */

public class ProviderListFragment extends Fragment {

    private View parentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_list,container,false);


        return parentView;
    }
}
