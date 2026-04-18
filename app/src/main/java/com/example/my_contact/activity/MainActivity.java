package com.example.my_contact.activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.my_contact.R;
import com.example.my_contact.fragment.ContactsFragment;
import com.example.my_contact.fragment.KeypadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle edge-to-edge system bars (from your original code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Load the Contacts screen by default when the app opens
        if (savedInstanceState == null) {
            loadFragment(new ContactsFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_contacts);
        }

        // Listen for clicks on the bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();

                // Check which icon was clicked
                if (itemId == R.id.nav_contacts) {
                    selectedFragment = new ContactsFragment();
                } else if (itemId == R.id.nav_keypad) {
                    selectedFragment = new KeypadFragment();
                }
                // Add the other screens here later:
                // else if (itemId == R.id.nav_favourites) { ... }
                // else if (itemId == R.id.nav_recents) { ... }

                // Switch to the selected screen
                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });
    }

    // Helper method to replace the current fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}