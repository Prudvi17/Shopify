package com.example.shopify.ui.fragments.home;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopify.R;
import com.example.shopify.adapters.AllItemsAdapter;
import com.example.shopify.data.Item;
import com.example.shopify.databinding.FragmentHomeBinding;
import com.example.shopify.others.ItemClickListener;
import com.example.shopify.viewmodels.MainViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import timber.log.Timber;

/// in below fragment we show the all users item which are for selling.
public class HomeFragment extends Fragment implements ItemClickListener,MaterialSearchBar.OnSearchActionListener,
        Toolbar.OnMenuItemClickListener, androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener {

    FragmentHomeBinding binding;
    ArrayList<Item> itemsList = new ArrayList<>();
    public static ArrayList<Item> itemsSelfList = new ArrayList<>();
    private View view;
    String userid;
    FirebaseUser user;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userid = user.getUid();

            view = binding.getRoot();

        return view;
    }

    public void search(String itemName) {
        ArrayList<Item> filterItemsList = new ArrayList<>();
        Log.e("TAG", "search: " );
        for (Item item : itemsList) {
            if (item.getItemName().toLowerCase().contains(itemName.toLowerCase())) {
                filterItemsList.add(item);
            }
        }
        Log.e("TAG", "search: " +filterItemsList.size());
        initializeRecycler(filterItemsList);
        //search(filterItemsList);
    }

    AllItemsAdapter adapter;
    private void initializeRecycler(ArrayList<Item> itemsList) {
        adapter = new AllItemsAdapter(itemsList, this, requireActivity());
        binding.allItemsRecyclerview.setAdapter(adapter);
    }
    private void search(ArrayList<Item> itemsList) {
        adapter.items = itemsList;
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.filter_menu) {
           // Toast.makeText(getActivity().getApplicationContext(), "Not Yet Implemented",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment2_to_filterFragment);
            return true;
        } else if (item.getItemId() == R.id.logoutMenu) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment2_to_loginFragment);
            return true;
        } else
            return false;
    }


    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    @Override
    public void itemClick(Item item, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("favorite_items");
        databaseReference.child(item.getItemId()).setValue(item).addOnSuccessListener(aVoid -> {

            Snackbar snackbar = Snackbar.make(requireView(), item.getItemName() + " added to cart successfully", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", view -> snackbar.dismiss());
            snackbar.show();
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("TAG", "setUserVisibleHint: " );
    }

    @Override
    public void onPause() {
        super.onPause();
      //  allowRefresh = true;
    }

    public boolean allowRefresh = false;
    @Override
    public void onResume() {
        super.onResume();

        Timber.d("onCreateView");
        binding.allItemsRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.floatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && binding.floatingActionButton.isShown()) {
                    binding.floatingActionButton.hide();
                }
            }
        });

        binding.toolbar.setOnMenuItemClickListener(this);
        binding.searchBar.setOnSearchActionListener(this);
        binding.searchBar.inflateMenu(R.menu.main_menu);
        binding.searchBar.getMenu().setOnMenuItemClickListener(this);

        //beloaw button for add new selling item.
        binding.floatingActionButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment2_to_sellFragmentOne));

        binding.searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(editable.toString());
            }
        });


        binding.buttonTryAgain.setOnClickListener(v -> {
            Timber.d("Try Again Clicked");
            binding.imageView2.setVisibility(View.INVISIBLE);
            binding.textView.setVisibility(View.INVISIBLE);
            binding.imageViewLoadingFailed.setVisibility(View.GONE);
            binding.textViewLoadingFailed.setVisibility(View.GONE);
            binding.buttonTryAgain.setVisibility(View.GONE);

        });

        MainViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()).create(MainViewModel.class);
        viewModel.fetchItems().observe(getViewLifecycleOwner(), items -> {
            if ((items.isEmpty() || items == null)) {

                binding.imageView2.setVisibility(View.VISIBLE);
                binding.textView.setVisibility(View.VISIBLE);
                binding.shimmerFrameLayout.setVisibility(View.GONE);


            } else {
                itemsList = items;
                itemsSelfList.clear();
                for (int i = 0; i < itemsList.size(); i++) {
                    if(itemsList.get(i).getSellerId().equals(userid)){
                        itemsSelfList.add(itemsList.get(i));
                    }
                }
                Timber.d(items.toString());
                binding.shimmerFrameLayout.setVisibility(View.GONE);
                binding.allItemsRecyclerview.setVisibility(View.VISIBLE);
                binding.imageView2.setVisibility(View.INVISIBLE);
                binding.textView.setVisibility(View.INVISIBLE);
                binding.imageViewLoadingFailed.setVisibility(View.GONE);
                binding.textViewLoadingFailed.setVisibility(View.GONE);
                binding.buttonTryAgain.setVisibility(View.GONE);
                Collections.reverse(items);
                initializeRecycler(items);
            }
        });

        viewModel.timeout.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                binding.imageViewLoadingFailed.setVisibility(View.VISIBLE);
                binding.textViewLoadingFailed.setVisibility(View.VISIBLE);
                binding.buttonTryAgain.setVisibility(View.VISIBLE);
                binding.shimmerFrameLayout.setVisibility(View.GONE);
            } else {
                binding.imageView2.setVisibility(View.INVISIBLE);
                binding.textView.setVisibility(View.INVISIBLE);
                binding.imageViewLoadingFailed.setVisibility(View.GONE);
                binding.textViewLoadingFailed.setVisibility(View.GONE);
                binding.buttonTryAgain.setVisibility(View.GONE);
            }
        });

        viewModel.connectedToInternet.observe(getViewLifecycleOwner(), aBoolean -> {
            if (!aBoolean) {
                Snackbar snackbar = Snackbar.make(requireView(), "It seems you are not connected to the Internet", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Dismiss", view1 -> snackbar.dismiss());
                snackbar.show();
            }
        });


    }
}