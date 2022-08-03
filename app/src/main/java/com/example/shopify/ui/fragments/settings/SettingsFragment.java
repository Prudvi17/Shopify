package com.example.shopify.ui.fragments.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopify.R;
import com.example.shopify.adapters.SettingsAdapter;
import com.example.shopify.data.Item;
import com.example.shopify.databinding.FragmentSettingsBinding;
import com.example.shopify.others.ItemClickListener;
import com.example.shopify.ui.fragments.home.HomeFragment;
import com.example.shopify.viewmodels.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

//below fragmetn shows the setting of current user where they can change their own name number and email also they see the history of their items
public class SettingsFragment extends Fragment implements ItemClickListener{
    FragmentSettingsBinding binding;
    SettingsAdapter adapter;
    String userid, firstName, lastName, phoneNum, email;
    FirebaseUser user;

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userid = user.getUid();

        getUserDetails();

        binding.progressBar.setVisibility(View.INVISIBLE);
        initAdapter(HomeFragment.itemsSelfList);
        binding.yourPostRecyclerview.setAdapter(adapter);
       /* viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(MainViewModel.class);

        viewModel.allItems().observe(requireActivity(), items -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            initAdapter(items);
            binding.yourPostRecyclerview.setAdapter(adapter);
        });*/

        binding.editTextView.setOnClickListener(v -> setViews());

        binding.saveTextView.setOnClickListener(v -> {

            updateData();
            binding.editUserName1.setVisibility(View.GONE);
            binding.editUserName2.setVisibility(View.GONE);
            binding.saveTextView.setVisibility(View.GONE);
            binding.editTextView.setVisibility(View.VISIBLE);
            binding.userName.setVisibility(View.VISIBLE);
            binding.profileImage.setVisibility(View.VISIBLE);
            binding.userEmail.setVisibility(View.VISIBLE);
            binding.phoneNum.setVisibility(View.VISIBLE);
            binding.editPhoneNum.setVisibility(View.GONE);
            binding.editUserName1.setText("");
            binding.editUserName2.setText("");
            binding.editPhoneNum.setText("");
        });

        return view;
    }

    private void getUserDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName = Objects.requireNonNull(snapshot.child("f_Name").getValue()).toString();
                lastName = Objects.requireNonNull(snapshot.child("l_Name").getValue()).toString();
                email = Objects.requireNonNull(snapshot.child("e_Mail").getValue()).toString();
                phoneNum = Objects.requireNonNull(snapshot.child("phone_No").getValue()).toString();

                binding.userName.setText(firstName + " " + lastName);
                binding.userEmail.setText(email);
                binding.phoneNum.setText(phoneNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initAdapter(List<Item> items) {
        List<Item> myItems = new ArrayList();
       /* SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
        for (int i = 0; i <items.size(); i++) {
            if(items.get(i).getSellerId().equals(sharedPreferences.getString("USERID", "default"))){
                myItems.add(items.get(i));
            }
        }*/
        adapter = new SettingsAdapter(items, this);
    }

    @SuppressLint("SetTextI18n")
    public void updateData() {
        String fName1 = binding.editUserName1.getText().toString();
        String fName2 = binding.editUserName2.getText().toString();
        String phone = binding.editPhoneNum.getText().toString();

        if (TextUtils.isEmpty(fName1) && TextUtils.isEmpty(fName2) && TextUtils.isEmpty(phone)) {
            Toast.makeText(requireContext(), "User details cannot be blank", Toast.LENGTH_LONG).show();
            Timber.d(firstName + " " + lastName + " " + phoneNum);

        } else if (!TextUtils.isEmpty(fName1) && !TextUtils.isEmpty(fName2) && !TextUtils.isEmpty(phone)) {
            databaseReference.child(userid).child("f_Name").setValue(fName1);
            databaseReference.child(userid).child("l_Name").setValue(fName2);
            databaseReference.child(userid).child("phone_No").setValue(phone);
            binding.userName.setText(fName1 + " " + fName2);
            binding.phoneNum.setText(phone);
            firstName = fName1;
            lastName = fName2;
            phoneNum = phone;
            Timber.d(fName1 + " " + fName2 + " " + phone);
        } else if (!TextUtils.isEmpty(fName1) && TextUtils.isEmpty(fName2) && TextUtils.isEmpty(phone)) {
            databaseReference.child(userid).child("f_Name").setValue(fName1);
            binding.userName.setText(fName1 + " " + lastName);
            binding.phoneNum.setText(phoneNum);
            firstName = fName1;
        } else if (!TextUtils.isEmpty(fName1) && !TextUtils.isEmpty(fName2) && TextUtils.isEmpty(phone)) {
            databaseReference.child(userid).child("f_Name").setValue(fName1);
            databaseReference.child(userid).child("l_Name").setValue(fName2);
            binding.userName.setText(fName1 + " " + fName2);
            binding.phoneNum.setText(phoneNum);
            firstName = fName1;
            lastName = fName2;

        } else if (!TextUtils.isEmpty(fName1) && TextUtils.isEmpty(fName2) && !TextUtils.isEmpty(phone)) {
            databaseReference.child(userid).child("f_Name").setValue(fName1);
            databaseReference.child(userid).child("phone_No").setValue(phone);
            binding.userName.setText(fName1 + " " + lastName);
            binding.phoneNum.setText(phone);
            firstName = fName1;
            phoneNum = phone;

        } else if (TextUtils.isEmpty(fName1) && !TextUtils.isEmpty(fName2) && !TextUtils.isEmpty(phone)) {
            databaseReference.child(userid).child("l_Name").setValue(fName2);
            databaseReference.child(userid).child("phone_No").setValue(phone);
            binding.userName.setText(firstName + " " + fName2);
            binding.phoneNum.setText(phone);
            lastName = fName2;
            phoneNum = phone;

        } else if (!TextUtils.isEmpty(fName2) && TextUtils.isEmpty(fName1) && TextUtils.isEmpty(phone)) {
            databaseReference.child(userid).child("l_Name").setValue(fName2);
            binding.userName.setText(firstName + " " + fName2);
            binding.phoneNum.setText(phoneNum);
            lastName = fName2;
        } else if (!TextUtils.isEmpty(phone) && TextUtils.isEmpty(fName1) && TextUtils.isEmpty(fName2)) {
            databaseReference.child(userid).child("phone_No").setValue(phone);
            binding.userName.setText(firstName + " " + lastName);
            binding.phoneNum.setText(phone);
            phoneNum = phone;
        }

    }

    public void setViews() {
        binding.userName.setVisibility(View.INVISIBLE);
        binding.editUserName1.setVisibility(View.VISIBLE);
        binding.editUserName2.setVisibility(View.VISIBLE);
        binding.editTextView.setVisibility(View.INVISIBLE);
        binding.saveTextView.setVisibility(View.VISIBLE);
        binding.profileImage.setVisibility(View.INVISIBLE);
        binding.userEmail.setVisibility(View.INVISIBLE);
        binding.phoneNum.setVisibility(View.INVISIBLE);
        binding.editPhoneNum.setVisibility(View.VISIBLE);
    }

    @Override
    public void itemClick(Item item, int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
              //  viewModel.updateSoldItem(false, position);
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("all_items").orderByChild("itemUniqueId").equalTo(item.getItemUniqueId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot i : snapshot.getChildren()) {
                                Timber.d(Objects.requireNonNull(i.getValue()).toString());
                                i.getRef().removeValue();
                                adapter.notifyItemRemoved(position);
                                adapter.notifyDataSetChanged();
                                HomeFragment.itemsSelfList.remove(position);
                                Timber.d("%s removed from advertisements", i.getValue().toString());
                            }
                        } else {
                            Timber.d("Does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Timber.d(error.getMessage());
                    }
                });

              //  viewModel.updateSoldItem(true, position);
                Timber.d("Item remove from advertisements");
                Toast.makeText(requireContext(), "Item remove from advertisements", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private void setUIMode(boolean isChecked) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            saveToSharedPrefs(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            saveToSharedPrefs(false);
        }
    }

    private void saveToSharedPrefs(boolean isChecked) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ui_mode", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ISCHECKED", isChecked);
        editor.apply();
    }
}