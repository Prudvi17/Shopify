package com.example.shopify.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.shopify.others.ItemClickListener;
import com.example.shopify.ui.fragments.home.HomeFragmentDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;

//this is the adapter in which we  show all the items at our home fragment
public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ViewHolder> {

    public ArrayList<Item> items;
    ItemClickListener itemClickListener;
    Context context;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("favorite_items");


    public AllItemsAdapter(ArrayList<Item> itemList, ItemClickListener itemClickListener, Context context) {
        items = itemList;
        this.itemClickListener = itemClickListener;
        this.context = context;
        Log.e("TAG", "AllItemsAdapter: " );
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("TAG", "onCreateViewHolder: " );
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_recycler_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("TAG", "onBindViewHolder: " );

        databaseReference.orderByChild("itemName").equalTo(items.get(position).getItemName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //  Timber.d("Already favorite");
                    holder.add_item_to_favorites.setVisibility(View.INVISIBLE);
                    holder.starredItem.setVisibility(View.VISIBLE);
                } else {
                    // Timber.d("Not a favorite");
                    // itemClickListener.itemClick(item, position);
                    holder.add_item_to_favorites.setVisibility(View.VISIBLE);
                    holder.starredItem.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Timber.d(error.getMessage());
            }
        });


        holder.item_name.setText(items.get(position).getItemName());
        holder.item_price.setText("$ " + items.get(position).getItemPrice());
        holder.condition.setText(items.get(position).getCondition());
        /*Picasso.get()
                .load(items.get(position).getItemImage2())
                .fit().centerInside()
                .placeholder(R.drawable.lottie_loading)
                .into(holder.item_image);*/

        Glide.with(holder.itemView)
                .load(items.get(position).getItemImage())
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
                .into(holder.item_image);


        Item item = new Item(items.get(position).getSellerName(),
                items.get(position).getSellerId(),
                items.get(position).getSellerLastSeen(),
                items.get(position).getSellerPhoneNum(),
                items.get(position).getItemImage(),
                items.get(position).getItemImage2(),
                items.get(position).getItemImage3(),
                items.get(position).getItemName(),
                items.get(position).getItemPrice(),
                items.get(position).getDatePosted(),
                items.get(position).getLocation(),
                items.get(position).getAddress(),
                items.get(position).getItemSoldOut(),
                items.get(position).getBidEnabled(),
                items.get(position).getItemDescription(),
                items.get(position).getCategory(),
                items.get(position).getCondition(),
                items.get(position).getItemUniqueId(),
                items.get(position).getItemId());

        holder.card.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(item);
            Navigation.findNavController(v).navigate(action);
        });
    holder.starredItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("favorite_items").child(items.get(position).getItemId()).removeValue();
            holder.add_item_to_favorites.setVisibility(View.VISIBLE);
            holder.starredItem.setVisibility(View.INVISIBLE);
        }
    });
        holder.add_item_to_favorites.setOnClickListener(v -> {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("favorite_items");
            databaseReference.orderByChild("itemName").equalTo(items.get(position).getItemName()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                    } else {
                        Timber.d("Not added in cart");
                        itemClickListener.itemClick(item, position);
                        holder.add_item_to_favorites.setVisibility(View.INVISIBLE);
                        holder.starredItem.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Timber.d(error.getMessage());
                }
            });


            //itemClickListener.itemClick(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView item_name;
        TextView item_price;
        ImageView add_item_to_favorites;
        CardView card;
        TextView condition;
        ImageView starredItem;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.itemImageImg);
            item_name = itemView.findViewById(R.id.itemNameTxt);
            item_price = itemView.findViewById(R.id.itemPriceTxt);
            add_item_to_favorites = itemView.findViewById(R.id.favoriteItemImg);
            card = itemView.findViewById(R.id.item_card_layout);
            starredItem = itemView.findViewById(R.id.starredfavoriteItemImg);
            progressBar = itemView.findViewById(R.id.allItemsRowProgressBar);
            condition = itemView.findViewById(R.id.itemCondition);
        }
    }
}
