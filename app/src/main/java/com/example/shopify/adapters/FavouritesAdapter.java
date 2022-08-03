package com.example.shopify.adapters;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.shopify.data.Item;
import com.example.shopify.ui.fragments.favorites.FavoritesFragmentDirections;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class FavouritesAdapter extends FirebaseRecyclerAdapter<Item, FavouritesAdapter.ViewHolder> {

    public FavouritesAdapter(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getItemName());
        holder.itemPrice.setText("$ " + model.getItemPrice());
        holder.phoneNum.setText(model.getSellerPhoneNum());
        holder.location.setText(model.getLocation());
        holder.description.setText("Posted by " + model.getSellerName() + " on " + model.getDatePosted());
        /*Picasso.get()
                .load(model.getItemImage())
                .placeholder(R.drawable.lottie_loading)
                .into(holder.itemImage);*/

        Glide.with(holder.itemView)
                .load(model.getItemImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .fitCenter()
                .into(holder.itemImage);
        holder.imgRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(view.getContext(), "success"+model.getItemId(),Toast.LENGTH_SHORT).show();
              DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
               databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("favorite_items").child(model.getItemId()).removeValue();
            }
        });

        holder.cardView.setOnClickListener(v -> {
            Item item = new Item(model.getSellerName(),
                    model.getSellerId(),
                    model.getSellerLastSeen(),
                    model.getSellerPhoneNum(),
                    model.getItemImage(),
                    model.getItemImage2(),
                    model.getItemImage3(),
                    model.getItemName(),
                    model.getItemPrice(),
                    model.getDatePosted(),
                    model.getLocation(),
                    model.getAddress(),
                    model.getItemSoldOut(),
                    model.getBidEnabled(),
                    model.getItemDescription(),
                    model.getCategory(),
                    model.getCondition(),
                    model.getItemUniqueId(),
                    model.getItemId());

            NavDirections action = FavoritesFragmentDirections.actionFavoritesFragment2ToDetailsFragment(item);
            Navigation.findNavController(v).navigate(action);
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false));
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage,imgRemoveCart;
        TextView itemName, itemPrice, description, location, phoneNum;
        CardView cardView;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgRemoveCart = itemView.findViewById(R.id.imgRemoveCart);
            this.itemImage = itemView.findViewById(R.id.item_image);
            this.itemName = itemView.findViewById(R.id.item_name);
            this.itemPrice = itemView.findViewById(R.id.item_price);
            this.cardView = itemView.findViewById(R.id.favourites_cardView);
            this.description = itemView.findViewById(R.id.textViewSomeDescription);
            this.location = itemView.findViewById(R.id.textViewLocation);
            this.phoneNum = itemView.findViewById(R.id.textViewPhoneNum);
            this.progressBar = itemView.findViewById(R.id.FavoritesRowProgressBar);
        }
    }
}