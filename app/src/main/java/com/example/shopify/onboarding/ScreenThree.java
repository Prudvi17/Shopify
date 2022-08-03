package com.example.shopify.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.example.shopify.R;
import com.example.shopify.databinding.FragmentScreenThreeBinding;
import com.example.shopify.others.Utils;

import org.jetbrains.annotations.NotNull;

public class ScreenThree extends Fragment {
    FragmentScreenThreeBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScreenThreeBinding.inflate(inflater, container, false);


        View view = binding.getRoot();

        binding.start.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_viewPagerFragment_to_registerFragment);
            Utils.onBoardingDone(requireContext());
        });

        return view;
    }
}