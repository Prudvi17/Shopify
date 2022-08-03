package com.example.shopify.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Sell implements Parcelable {

    public static final Creator<Sell> CREATOR = new Creator<Sell>() {
        @Override
        public Sell createFromParcel(Parcel source) {
            return new Sell(source);
        }

        @Override
        public Sell[] newArray(int size) {
            return new Sell[size];
        }
    };
    private String category;
    private String location;
    private String address;
    private String itemName;
    private String itemPrice;
    private String condition;
    private Boolean itemStarred;
    private Boolean itemSoldOut;
    private Boolean isBidEnabled;
    private String sellerPhoneNum;
    private String datePosted;
    private ArrayList<Uri> imagesList;
    private String itemImage;
    private String itemImage2;
    private String itemImage3;
    private String itemDescription;
    private String sellerName;
    private String sellerId;
    private String sellerLastSeen;
    private String itemUniqueId;
    private String itemId;

    public Sell(String category, String location,String address, String itemName,
                String itemPrice, String condition, String sellerPhoneNum,
                String datePosted, String image1, String image2, String image3, Boolean isStarred,Boolean isBidEnabled, String itemDescription, String sellerName, String sellerId, String sellerLastSeen, String itemUniqueId,String itemId) {
        this.category = category;
        this.location = location;
        this.address = address;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.condition = condition;
        this.sellerPhoneNum = sellerPhoneNum;
        this.datePosted = datePosted;
        this.itemImage = image1;
        this.itemImage2 = image2;
        this.itemImage3 = image3;
        this.itemStarred = isStarred;
        this.itemSoldOut = false;
        this.isBidEnabled = isBidEnabled;
        this.itemDescription = itemDescription;
        this.sellerName = sellerName;
        this.sellerId = sellerId;
        this.sellerLastSeen = sellerLastSeen;
        this.itemUniqueId = itemUniqueId;
        this.itemId = itemId;
    }

    public Sell(String category, String location,String address, ArrayList<Uri> imagesList) {
        this.category = category;
        this.location = location;
        this.address = address;
        this.imagesList = imagesList;
    }

    protected Sell(Parcel in) {
        this.category = in.readString();
        this.location = in.readString();
        this.itemName = in.readString();
        this.itemPrice = in.readString();
        this.condition = in.readString();
        this.sellerPhoneNum = in.readString();
        this.datePosted = in.readString();
        this.imagesList = in.createTypedArrayList(Uri.CREATOR);
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerLastSeen() {
        return sellerLastSeen;
    }

    public void setSellerLastSeen(String sellerLastSeen) {
        this.sellerLastSeen = sellerLastSeen;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemImage2() {
        return itemImage2;
    }

    public void setItemImage2(String itemImage2) {
        this.itemImage2 = itemImage2;
    }

    public String getItemImage3() {
        return itemImage3;
    }

    public void setItemImage3(String itemImage3) {
        this.itemImage3 = itemImage3;
    }

    public Boolean getItemStarred() {
        return itemStarred;
    }

    public void setItemStarred(Boolean itemStarred) {
        this.itemStarred = itemStarred;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSellerPhoneNum() {
        return sellerPhoneNum;
    }

    public void setSellerPhoneNum(String sellerPhoneNum) {
        this.sellerPhoneNum = sellerPhoneNum;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public ArrayList<Uri> getImagesList() {
        return imagesList;
    }

    public void setImagesList(ArrayList<Uri> imagesList) {
        this.imagesList = imagesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.location);
        dest.writeString(this.itemName);
        dest.writeString(this.itemPrice);
        dest.writeString(this.condition);
        dest.writeString(this.sellerPhoneNum);
        dest.writeString(this.datePosted);
        dest.writeTypedList(this.imagesList);
    }

    public void readFromParcel(Parcel source) {
        this.category = source.readString();
        this.location = source.readString();
        this.address = source.readString();
        this.itemName = source.readString();
        this.itemPrice = source.readString();
        this.condition = source.readString();
        this.sellerPhoneNum = source.readString();
        this.datePosted = source.readString();
        this.imagesList = source.createTypedArrayList(Uri.CREATOR);
    }

    public String getItemUniqueId() {
        return itemUniqueId;
    }

    public void setItemUniqueId(String itemUniqueId) {
        this.itemUniqueId = itemUniqueId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getItemSoldOut() {
        return itemSoldOut;
    }

    public void setItemSoldOut(Boolean itemSoldOut) {
        this.itemSoldOut = itemSoldOut;
    }

    public Boolean getBidEnabled() {
        return isBidEnabled;
    }

    public void setBidEnabled(Boolean bidEnabled) {
        isBidEnabled = bidEnabled;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
