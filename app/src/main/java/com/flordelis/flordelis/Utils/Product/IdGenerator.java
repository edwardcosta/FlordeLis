package com.flordelis.flordelis.Utils.Product;

import android.support.annotation.NonNull;

import java.security.SecureRandom;

/**
 * Created by Sala on 25/01/2018.
 */

public class IdGenerator {
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();
    private static final int len = 6;

    @NonNull
    public static String generateID(){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
