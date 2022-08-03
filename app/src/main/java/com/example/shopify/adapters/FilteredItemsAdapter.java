package com.example.shopify.adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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
import com.example.shopify.ui.fragments.filter.FilteredItemsFragmentDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;

public class FilteredItemsAdapter extends RecyclerView.Adapter<FilteredItemsAdapter.ViewHolder> {

    private final ArrayList<Item> items;
    ItemClickListener itemClickListener;

    public FilteredItemsAdapter(ArrayList<Item> itemList, ItemClickListener itemClickListener) {
        items = itemList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_recycler_row, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (items.get(position).getItemStarred()) {
            holder.starredItem.setVisibility(View.VISIBLE);
            holder.add_item_to_favorites.setVisibility(View.INVISIBLE);
        } else if (!items.get(position).getItemStarred()) {
            holder.starredItem.setVisibility(View.INVISIBLE);
        }

        holder.item_name.setText(items.get(position).getItemName());
        holder.item_price.setText("$ " + items.get(position).getItemPrice());

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

        holder.card.setOnClickListener(v -> {

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

            NavDirections action = FilteredItemsFragmentDirections.actionFilteredItemsFragmentToDetailsFragment(item);
            Navigation.findNavController(v).navigate(action);
            Timber.d("card clicked");
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
            items.get(position).setItemStarred(true);
           // Item item = new Item(items.get(position).getItemImage(), items.get(position).getItemName(), items.get(position).getItemPrice(), items.get(position).getItemStarred());
            itemClickListener.itemClick(items.get(position), position);
            holder.add_item_to_favorites.setVisibility(View.INVISIBLE);
            holder.starredItem.setVisibility(View.VISIBLE);
            Timber.d("clicked");
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
        }
    }
}
