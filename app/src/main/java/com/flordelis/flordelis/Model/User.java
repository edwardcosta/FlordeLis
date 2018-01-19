package com.flordelis.flordelis.Model;

/**
 * Created by Sala on 19/01/2018.
 */

public class User {

    private String displayName;
    private String email;
    private boolean admin = false;
    private String telefone;
    private String celular;
    private String status;
    private String mainImage;

    public boolean isAdmin() {
        return this.admin;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getCelular(){
        return this.celular;
    }

    public String getStatus(){
        return this.status;
    }

    public String getMainImage(){
        return this.mainImage;
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

    public void setMainImage(String mainimage){
        this.mainImage = mainimage;
    }

    public void setCelular(String celular){
        this.celular = celular;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
