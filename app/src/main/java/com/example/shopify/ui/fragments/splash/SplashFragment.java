package com.example.shopify.ui.fragments.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.example.shopify.R;
import com.example.shopify.databinding.FragmentSplashBinding;
import com.example.shopify.others.CheckInternet;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

//below is the first screen when our app starts
public class SplashFragment extends Fragment {
    FragmentSplashBinding binding;
    FirebaseAuth mFirebaseAuth;
    Animation top_anim, bottom_anim;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        top_anim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation);
        bottom_anim = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation);
// we initialize firebase database for getting data of our database
        mFirebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return binding.content;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if (!(CheckInternet.isConnected(requireContext()))) {

                Snackbar snackbar = Snackbar.make(getView(), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onStart();
                    }
                });
                snackbar.show();
                Timber.d("No Internet");
            } else {
                FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mFirebaseUser != null && onBoardingFinished()) {
                    if (mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment2);
                    } else {
                        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
                    }
                } else if (onBoardingFinished()) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_viewPagerFragment);
                }
            }
        }, 3000);
    }

    private boolean onBoardingFinished() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Finished", false);
    }
}