package com.flordelis.flordelis.Screens.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.R;

/**
 * Created by Sala on 23/01/2018.
 */

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final SimpleDraweeView simpleDraweeView = findViewById(R.id.user_actitivy_user_image);

        postponeEnterTransition();
        simpleDraweeView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        simpleDraweeView.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                }
        );
    }
}