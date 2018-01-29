package com.flordelis.flordelis.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Sala on 29/01/2018.
 */

public class CheckPermissions {
    public static String[] INTERNET = {Manifest.permission.INTERNET};
    public static String[] WRITE_EXTERNAL_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static String[] READ_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] GET_ACCOUNTS = {Manifest.permission.GET_ACCOUNTS};
    public static String[] READ_CONTACTS = {Manifest.permission.READ_CONTACTS};
    public static String[] CAMERA = {Manifest.permission.CAMERA};


    public static boolean checkPermissions(Activity activity, String... permissao){
        if (!possuiPermissoes (activity, permissao))
        {
            ActivityCompat.requestPermissions (activity, permissao, 1);
        }
        return true;
    }

    private static boolean possuiPermissoes (Context context, String... permissoes)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissoes != null)
        {
            for (String permissao : permissoes)
            {
                if (ActivityCompat.checkSelfPermission(context, permissao) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
