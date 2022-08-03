package com.example.shopify.ui.fragments.terms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.shopify.R;
import com.example.shopify.databinding.FragmentTermsConditionBinding;

//this is shows the terms of our app
public class TermsConditionFragment extends Fragment {

    private FragmentTermsConditionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        binding = FragmentTermsConditionBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.finished.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_termsConditionFragment_to_registerFragment);
        });
        return view;
    }
}