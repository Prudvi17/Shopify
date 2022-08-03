package com.example.shopify.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "bid_history")
public class Bid implements Parcelable {

    public static final Creator<Bid> CREATOR = new Creator<Bid>() {
        @Override
        public Bid createFromParcel(Parcel source) {
            return new Bid(source);
        }

        @Override
        public Bid[] newArray(int size) {
            return new Bid[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    int id;
    private String sellerName;
    private String sellerId;
    private String bidAmount;
    @Ignore
    public Bid() {
    }

    protected Bid(Parcel in) {
        this.id = in.readInt();
        this.sellerName = in.readString();
        this.sellerId = in.readString();
        this.bidAmount = in.readString();
    }


    @Ignore
    public Bid(String sellerName, String sellerId,String bidAmount) {
        this.sellerName = sellerName;
        this.sellerId = sellerId;
        this.bidAmount = bidAmount;
    }

    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.sellerName);
        dest.writeString(this.sellerId);
       dest.writeString(this.bidAmount);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.sellerName = source.readString();
        this.sellerId = source.readString();
        this.bidAmount = source.readString();
    }


    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public int getId() {
        return id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
