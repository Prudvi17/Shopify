package com.example.shopify.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.shopify.R;
import com.example.shopify.data.Bid;
import com.example.shopify.data.Item;
import com.example.shopify.ui.activity.MainActivity;
import com.example.shopify.ui.fragments.favorites.FavoritesFragmentDirections;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import timber.log.Timber;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.ViewHolder> {

    ArrayList<Bid> modelList;
    Context context;
    public BidAdapter(Context context, ArrayList<Bid> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_bids_recycler_row, parent, false));
    }
    public void showDialog(Activity activity, Bid bid){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_custom_profile);

        TextView text = (TextView) dialog.findViewById(R.id.user_name1);
        text.setText(bid.getSellerName());
        TextView txtPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        txtPrice.setText(bid.getBidAmount()+" $");

        AppCompatImageView phoneButton = (AppCompatImageView) dialog.findViewById(R.id.phoneButton);
        AppCompatImageView messageButton = (AppCompatImageView) dialog.findViewById(R.id.messageButton);
        AppCompatImageView whatsappButton = (AppCompatImageView) dialog.findViewById(R.id.whatsappButton);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(bid.getSellerId());
        String phone_no = "";
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone_no = snapshot.child("phone_No").getValue().toString();
                phoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sms = "Hey I have seen you are selling Can we do business";//The message you want to text to the phone

                        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone_no, null));
                        smsIntent.putExtra("sms_body", sms);
                        context.startActivity(smsIntent);
                        dialog.dismiss();
                    }
                });
                messageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone_no, null));
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                whatsappButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone =phone_no;
                        String url = "https://api.whatsapp.com/send?phone=" + phone;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url + "&text=" + "Hey I have seen you are selling  Can we do business"));
                        context.startActivity(intent);
                        Timber.d(phone);
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dialog.show();

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bid model = modelList.get(position);
        holder.bidPrice.setText("$ "+model.getBidAmount());
        holder.username.setText(model.getSellerName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(MainActivity.activity,model);
            }
        });
        /*Picasso.get()
                .load(model.getItemImage())
                .placeholder(R.drawable.lottie_loading)
                .into(holder.itemImage);*/
    }

    @Override
    public int getItemCount() {
        return this.modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, bidPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.username = itemView.findViewById(R.id.userName);
            this.bidPrice = itemView.findViewById(R.id.bidPrice);
        }
    }
}