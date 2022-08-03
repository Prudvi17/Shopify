package com.example.shopify.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "posted_item_history")
public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    int id;
    Boolean itemStarred;
    private String sellerName;
    private String sellerId;
    private String sellerLastSeen;
    private String sellerPhoneNum;
    private String itemImage;
    private String itemImage2;
    private String itemImage3;
    private String itemName;
    private String itemPrice;
    private String datePosted;
    private String location;
    private String address;
    private String itemDescription;
    private Boolean itemSoldOut;
    private Boolean isBidEnabled;
    private String category;
    private String condition;
    private double minPrice;
    private double maxPrice;
    private String itemUniqueId;
    private String itemId;

    @Ignore
    public Item() {
    }

    @Ignore
    public Item(String category, String condition, double minPrice, double maxPrice) {
        this.category = category;
        this.condition = condition;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Ignore
    public Item(String itemImage, String itemName, String itemPrice, Boolean itemStarred) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStarred = itemStarred;
    }

    @Ignore
    public Item(String sellerName, String sellerId, String sellerLastSeen, String sellerPhoneNum, String itemImage, String itemImage2, String itemImage3, String itemName, String itemPrice, String datePosted, String location, String address, Boolean itemSoldOut,Boolean isBidEnabled, String itemDescription, String category, String condition, String itemUniqueId,String itemId) {
        this.sellerName = sellerName;
        this.sellerId = sellerId;
        this.sellerLastSeen = sellerLastSeen;
        this.sellerPhoneNum = sellerPhoneNum;
        this.itemImage = itemImage;
        this.itemImage2 = itemImage2;
        this.itemImage3 = itemImage3;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.datePosted = datePosted;
        this.location = location;
        this.address = address;
        this.itemSoldOut = itemSoldOut;
        this.isBidEnabled = isBidEnabled;
        this.itemDescription = itemDescription;
        this.category = category;
        this.condition = condition;
        this.itemUniqueId = itemUniqueId;
        this.itemId = itemId;
    }

    public Item(String itemImage, String itemName, String itemPrice, String datePosted, Boolean itemSoldOut, String itemUniqueId) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.datePosted = datePosted;
        this.itemSoldOut = itemSoldOut;
        this.itemUniqueId = itemUniqueId;
    }

    @Ignore
    public Item(String itemImage, String itemImage2, String itemImage3) {

        this.itemImage = itemImage;
        this.itemImage2 = itemImage2;
        this.itemImage3 = itemImage3;
    }

    protected Item(Parcel in) {
        this.id = in.readInt();
        this.itemStarred = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.sellerName = in.readString();
        this.sellerId = in.readString();
        this.sellerLastSeen = in.readString();
        this.sellerPhoneNum = in.readString();
        this.itemImage = in.readString();
        this.itemImage2 = in.readString();
        this.itemImage3 = in.readString();
        this.itemName = in.readString();
        this.itemPrice = in.readString();
        this.datePosted = in.readString();
        this.location = in.readString();
        this.address = in.readString();
        this.itemDescription = in.readString();
        this.itemSoldOut = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.category = in.readString();
        this.condition = in.readString();
        this.itemId = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getItemSoldOut() {
        return itemSoldOut;
    }

    public void setItemSoldOut(Boolean itemSoldOut) {
        this.itemSoldOut = itemSoldOut;
    }

    public Boolean getItemStarred() {
        return itemStarred;
    }

    public void setItemStarred(Boolean itemStarred) {
        this.itemStarred = itemStarred;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerLastSeen() {
        return sellerLastSeen;
    }

    public void setSellerLastSeen(String sellerLastSeen) {
        this.sellerLastSeen = sellerLastSeen;
    }

    public String getSellerPhoneNum() {
        return sellerPhoneNum;
    }

    public void setSellerPhoneNum(String sellerPhoneNum) {
        this.sellerPhoneNum = sellerPhoneNum;
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

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeValue(this.itemStarred);
        dest.writeString(this.sellerName);
        dest.writeString(this.sellerId);
        dest.writeString(this.sellerLastSeen);
        dest.writeString(this.sellerPhoneNum);
        dest.writeString(this.itemImage);
        dest.writeString(this.itemImage2);
        dest.writeString(this.itemImage3);
        dest.writeString(this.itemName);
        dest.writeString(this.itemPrice);
        dest.writeString(this.datePosted);
        dest.writeString(this.location);
        dest.writeString(this.address);
        dest.writeString(this.itemDescription);
        dest.writeValue(this.itemSoldOut);
        dest.writeString(this.category);
        dest.writeString(this.condition);
        dest.writeString(this.itemId);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.itemStarred = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.sellerName = source.readString();
        this.sellerId = source.readString();
        this.sellerLastSeen = source.readString();
        this.sellerPhoneNum = source.readString();
        this.itemImage = source.readString();
        this.itemImage2 = source.readString();
        this.itemImage3 = source.readString();
        this.itemName = source.readString();
        this.itemPrice = source.readString();
        this.datePosted = source.readString();
        this.location = source.readString();
        this.address = source.readString();
        this.itemDescription = source.readString();
        this.itemSoldOut = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.category = source.readString();
        this.condition = source.readString();
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getItemUniqueId() {
        return itemUniqueId;
    }

    public void setItemUniqueId(String itemUniqueId) {
        this.itemUniqueId = itemUniqueId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
