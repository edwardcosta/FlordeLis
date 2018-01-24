package com.flordelis.flordelis.Model;

/*
 *Created by Sala on 19/01/2018.
 */

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable{
    private String id;
    private String addedBy;
    private String editedBy;
    private String deletedBy;
    private String soldBy;
    private String productName;
    private String buyedPrice;
    private String salePrice;
    private int quantity;
    private String color;
    private boolean bbSize;
    private String size;
    private String description;
    private long datetimeCreated;
    private long datetimeEdited;
    private long datetimeSold;
    private long datetimeDeleted;
    private String providerName;

    private String situation;

    private List<String> images;

    public long getDatetimeDeleted() {
        return datetimeDeleted;
    }

    public void setDatetimeDeleted(long datetimeDeleted) {
        this.datetimeDeleted = datetimeDeleted;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public long getDatetimeEdited() {
        return datetimeEdited;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getBuyedPrice() {
        return buyedPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getColor() {
        return color;
    }

    public boolean isBbSize() {
        return bbSize;
    }

    public String getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public long getDatetimeCreated() {
        return datetimeCreated;
    }

    public String getSituation(){
        return situation;
    }

    public String getProviderName(){
        return providerName;
    }

    public long getDatetimeSold(){
        return datetimeSold;
    }

    public void setDatetimeSold(long datetimeSold){
        this.datetimeSold = datetimeSold;
    }

    public void setProviderName(String providerName){
        this.providerName = providerName;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public void setDatetimeEdited(long datetimeEdited) {
        this.datetimeEdited = datetimeEdited;
    }

    public void setSituation(String situation){
        this.situation = situation;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBuyedPrice(String buyedPrice) {
        this.buyedPrice = buyedPrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBbSize(boolean bbSize) {
        this.bbSize = bbSize;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatetimeCreated(long datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }
}
