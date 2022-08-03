package com.example.shopify.ui.fragments.details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.shopify.R;
import com.example.shopify.adapters.BidAdapter;
import com.example.shopify.adapters.FavouritesAdapter;
import com.example.shopify.data.Bid;
import com.example.shopify.data.Item;
import com.example.shopify.data.User;
import com.example.shopify.databinding.FragmentDetailsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;


///this fragment shows the details of our selected item.which we open
public class DetailsFragment extends Fragment {
    FragmentDetailsBinding binding;
    DatabaseReference databaseReference;
    String userId;
    String userName;
    FirebaseUser user;
    Item data;
    private Boolean clicked = false;
    private String myNumber;

    private ArrayList<Bid> listBids = new ArrayList();
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // beloaw code ffor gettting users details from database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();

        //below code is for get username of that item belogs
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User usr = snapshot.getValue(User.class);
                
                userName = usr.getF_Name()+" "+usr.getL_Name();
                //userName = snapshot.child("f_name").getValue().toString()+snapshot.child("l_name").getValue().toString();;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        assert getArguments() != null;



        data = DetailsFragmentArgs.fromBundle(getArguments()).getItemDetailsArgs();
        Timber.d(data.getItemName());

        binding.userName1.setText(data.getSellerName());
        binding.tvLastseen1.setText("Seller");
        binding.favPhoneNumber1.setText(data.getSellerPhoneNum());
        // if bid section enabled then it will show the bid section
        if(data.getBidEnabled()){
            binding.rlBidSection.setVisibility(View.VISIBLE);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_items")
                    .child(data.getItemId()).child("bids");
            Log.e("TAG", "onCreateView: "+data.getItemId() );
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Bid> listBids = new ArrayList();
                    if(snapshot.hasChildren()){
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Bid bid = child.getValue(Bid.class);
                            if(bid.getSellerId().equals(userId)){
                                binding.etBidAmount.setText(bid.getBidAmount());
                                listBids.add(bid);
                            }else {
                                listBids.add(bid);
                            }
                        }
                    }else{

                    }
                    Log.e("TAG", "onDataChange: "+listBids.size() );

                    //call the adapter from below function
                    assignBidAdapter(listBids);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            binding.rlBidSection.setVisibility(View.GONE);
        }
        myNumber = data.getSellerPhoneNum();

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(data.getItemImage(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage2(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage3(), ScaleTypes.CENTER_CROP));

        Timber.d("image 1 " + data.getItemImage() + " \n image 2 " + data.getItemImage2() + " \n image 3 " + data.getItemImage3());

        binding.btnBidNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etBidAmount.getText().toString().trim().isEmpty()){
                    binding.etBidAmount.setError("Please Enter Amount");
                }else{
                    sendBid(binding.etBidAmount.getText().toString());
                }
            }
        });
        binding.imageSliderFav1.setImageList(imageList);

        binding.favoritesItemName1.setText(data.getItemName());
        binding.favItemPrice1.setText("$ " + data.getItemPrice());
        binding.favDatePosted1.setText("Uploaded on " + data.getDatePosted());
        binding.favLocation1.setText(data.getLocation());
        binding.address.setText(data.getAddress());
        binding.favDescription1.setText(data.getItemDescription());

        binding.imageSliderFav1.setItemClickListener(position -> {

            Item item = new Item(data.getItemImage(), data.getItemImage2(), data.getItemImage3());
            NavDirections navDirections = DetailsFragmentDirections.actionDetailsFragmentToPictureBrowserFragment(item);
            Navigation.findNavController(requireView()).navigate(navDirections);
        });

        binding.showContacts.setOnClickListener(v -> onContactsButtonClicked());


        //below code will redirect to mesage for sending message to seller
        binding.messageButton.setOnClickListener(v -> {

            String sms = "Hey I have seen you are selling " + data.getItemName() + ". Can we do business";//The message you want to text to the phone

            Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", myNumber, null));
            smsIntent.putExtra("sms_body", sms);
            startActivity(smsIntent);
        });

        // below code will call the seller from clicking
        binding.phoneButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", myNumber, null));
            startActivity(intent);
        });

        //below code send the message to whatsapp direct
        binding.whatsappButton.setOnClickListener(v -> {
            String phone =  "+1"+ myNumber;
            String url = "https://api.whatsapp.com/send?phone=" + phone;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url + "&text=" + "Hey I have seen you are selling " + data.getItemName() + ". Can we do business"));
            startActivity(intent);
            Timber.d(phone);
        });


        return view;
    }

    BidAdapter adapter;
    private void assignBidAdapter(ArrayList<Bid> listBids) {
        this.listBids = listBids;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new BidAdapter(getActivity(),listBids);
        binding.rvBids.setLayoutManager(layoutManager);
        binding.rvBids.setAdapter(adapter);
    }

    private void sendBid(String text) {
        Log.e("TAG", "sendBid: "+data.getItemId() );
        Bid bid = new Bid(userName,user.getUid(),text);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_items").child(data.getItemId()).child("bids");
        databaseReference.child(bid.getSellerId()).setValue(bid).addOnSuccessListener(aVoid -> {
            Snackbar snackbar = Snackbar.make(requireView(),  "Bid Successfully added", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", view -> snackbar.dismiss());
            snackbar.show();
        });
    }

    private void onContactsButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        isButtonClicked(clicked);

        clicked = !clicked;
    }

    private void setVisibility(Boolean clicked) {
        if (!clicked) {
            binding.messageButton.setVisibility(View.VISIBLE);
            binding.phoneButton.setVisibility(View.VISIBLE);
            binding.whatsappButton.setVisibility(View.VISIBLE);
        } else {
            binding.messageButton.setVisibility(View.INVISIBLE);
            binding.phoneButton.setVisibility(View.INVISIBLE);
            binding.whatsappButton.setVisibility(View.INVISIBLE);
        }

    }

    private void setAnimation(Boolean clicked) {
        Animation fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);
        if (!clicked) {
            binding.messageButton.startAnimation(fromBottom);
            binding.phoneButton.startAnimation(fromBottom);
            binding.whatsappButton.startAnimation(fromBottom);
        } else {
            binding.messageButton.startAnimation(toBottom);
            binding.phoneButton.startAnimation(toBottom);
            binding.whatsappButton.startAnimation(toBottom);
        }
    }

    private void isButtonClicked(Boolean clicked) {
        if (!clicked) {
            binding.phoneButton.setClickable(true);
            binding.messageButton.setClickable(true);
            binding.whatsappButton.setClickable(true);
        } else {
            binding.phoneButton.setClickable(false);
            binding.messageButton.setClickable(false);
            binding.whatsappButton.setClickable(false);
        }
    }

}