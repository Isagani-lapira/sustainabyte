package com.example.sustainabyte;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public TextView tv_username, tv_email;
    private ImageView iv_userIcon;
    private String userId;

    private final String[] menuItems = {
            "My Account",
            "Settings",
            "About Us",
            "Logout"
    };

    @SuppressLint({"SetTextI18n", "QueryPermissionsNeeded"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Initialize TextViews
        initialize(view);

        ListView listView = view.findViewById(R.id.profileListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item_layout, R.id.textViewListItem, menuItems);
        listView.setAdapter(adapter);

        // Set item click listener
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedItem = menuItems[position];
//            Toast.makeText(requireContext(), "Clicked on " + selectedItem, Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
                    // Open my account page
                    startActivity(new Intent(requireContext(), MyAccountActivity.class));
                    break;
                case 1:
                    // Open settings page
                    startActivity(new Intent(requireContext(), SettingsActivity.class));
                    break;
                case 2:
                    // Open about us page
                    startActivity(new Intent(requireContext(), AboutUsActivity.class));
                    break;
                case 3:
                    // Logout
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(requireContext(), MainActivity.class));
                    requireActivity().finish();
                    break;
            }
        });

        // Access cached user data
        String username = UserDataManager.getInstance().getUsername();
        String email = UserDataManager.getInstance().getEmail();
        String icon = UserDataManager.getInstance().getIcon();

        tv_username.setText("@" + username);
        tv_email.setText(email);

        // Load user icon
        if (icon != null) {
            byte[] decodedBytes = Base64.decode(icon, Base64.DEFAULT);
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            Glide.with(requireContext())
                    .load(imageBitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .into(iv_userIcon);
        }

        // Set click listener for updating user icon
        iv_userIcon.setOnClickListener(v -> {
            // Intent to open camera
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(requireContext(), "No camera app available", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initialize(View view) {
        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);
        iv_userIcon = view.findViewById(R.id.userIcon);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    // Set circular bitmap to ImageView using Glide
                    Glide.with(this)
                            .load(imageBitmap)
                            .apply(RequestOptions.circleCropTransform())
                            .into(iv_userIcon);

                    // Convert Bitmap to Base64 encoded string
                    String imageBase64 = bitmapToBase64(imageBitmap);

                    // Store the Base64 string in Firebase Realtime Database
                    DatabaseReference userIconRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(getUserId())).child("icon");
                    userIconRef.setValue(imageBase64);

                    // Update cached user icon data
                    UserDataManager.getInstance().setIcon(imageBase64);
                }
            }
        }
    }

    private String getUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }

    private String bitmapToBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
