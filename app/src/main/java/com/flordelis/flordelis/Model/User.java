package com.flordelis.flordelis.Model;

import java.io.Serializable;

/**
 * Created by Sala on 19/01/2018.
 */

public class User implements Serializable {

    private String displayName;
    private String email;
    private String telefone;
    private String backImage;

    public String getTelefone() {
        return this.telefone;
    }

    public String getBackImage(){
        return this.backImage;
    }

    public String getDisplayName(){
        return displayName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public void setBackImage(String mainimage){
        this.backImage = mainimage;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
