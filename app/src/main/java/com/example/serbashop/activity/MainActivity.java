package com.example.serbashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.serbashop.R;
import com.example.serbashop.fragment.HomeFragment;
import com.example.serbashop.fragment.KeranjangFragment;
import com.example.serbashop.fragment.ProfileFragment;
import com.example.serbashop.fragment.TokoFragment;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout itemBeranda, itemToko, itemKeranjang, itemProfile;
    private ImageView ivHomeNav, ivTokoNav, ivKeranjangNav, ivProfileNav;

    private TextView tvHomeNav, tvTokoNav, tvKeranjangNav, tvProfileNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemBeranda = findViewById(R.id.item_beranda);
        itemToko = findViewById(R.id.item_toko);
        itemKeranjang = findViewById(R.id.item_keranjang);
        itemProfile = findViewById(R.id.item_profile);

        ivHomeNav = findViewById(R.id.IV_HomeNav);
        ivTokoNav = findViewById(R.id.IV_TokoNav);
        ivKeranjangNav = findViewById(R.id.IV_keranjangNav);
        ivProfileNav = findViewById(R.id.IV_Profile);

        tvHomeNav = findViewById(R.id.TV_homeNav);
        tvTokoNav = findViewById(R.id.TV_TokoNav);
        tvKeranjangNav = findViewById(R.id.TV_keranjangNav);
        tvProfileNav = findViewById(R.id.TV_profile);

        FragmentManager fragmentManager = getSupportFragmentManager();

        HomeFragment homeFragment = new HomeFragment();
        TokoFragment tokoFragment = new TokoFragment();
        KeranjangFragment keranjangFragment = new KeranjangFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());

        if(!(fragment instanceof HomeFragment)){
            fragmentManager.beginTransaction()
                    .add(R.id.frame_container, homeFragment)
                    .commit();
        }

        setNavigationSelection(R.id.item_beranda);

        itemBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
                setNavigationSelection(R.id.item_beranda);
            }
        });

        itemToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TokoFragment());
                setNavigationSelection(R.id.item_toko);
            }
        });

        itemKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new KeranjangFragment());
                setNavigationSelection(R.id.item_keranjang);
            }
        });

        itemProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ProfileFragment());
                setNavigationSelection(R.id.item_profile);
            }
        });

        String fragmentToLoad = getIntent().getStringExtra("fragmentToLoad");
        if (fragmentToLoad != null && fragmentToLoad.equals("keranjangFragment")) {
            loadFragment(new KeranjangFragment());
            setNavigationSelection(R.id.item_keranjang);
        } else if (fragmentToLoad != null && fragmentToLoad.equals("PembayaranFragment")) {
            loadFragment(new ProfileFragment());
            setNavigationSelection(R.id.item_profile);
        } else {
            loadFragment(new HomeFragment());
            setNavigationSelection(R.id.item_beranda);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    private void setNavigationSelection(int itemId) {
        ivHomeNav.setImageResource(R.drawable.home_unselect);
        ivTokoNav.setImageResource(R.drawable.store_unselect);
        ivKeranjangNav.setImageResource(R.drawable.basket_unselect);
        ivProfileNav.setImageResource(R.drawable.profile_unselect);

        if (itemId == R.id.item_beranda) {
            ivHomeNav.setImageResource(R.drawable.baseline_home_24);
        } else if (itemId == R.id.item_toko) {
            ivTokoNav.setImageResource(R.drawable.store_select);
        } else if (itemId == R.id.item_keranjang) {
            ivKeranjangNav.setImageResource(R.drawable.basket_select);
        } else if (itemId == R.id.item_profile) {
            ivProfileNav.setImageResource(R.drawable.profile_selected);
        } else {
            throw new IllegalArgumentException("Unexpected itemId: " + itemId);
        }
    }
}