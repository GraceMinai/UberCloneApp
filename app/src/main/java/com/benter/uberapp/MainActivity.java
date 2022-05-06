package com.benter.uberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    public EditText firstName, secondName, userName, emailAddress, phoneNumber;
    public Button submit;
    public TextView register;
    public GoogleMap map;
    public Location location;


    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firstName = findViewById(R.id.firstName_id);
        secondName = findViewById(R.id.secondName_id);
        userName = findViewById(R.id.userName_id);
        emailAddress = findViewById(R.id.emailAddress_id);
        phoneNumber = findViewById(R.id.phoneNumber_id);
        submit = findViewById(R.id.submit_id);
        register = findViewById(R.id.register_id);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            //if the permission is already granted the MapsActivity will start
            startActivity(new Intent(MainActivity.this, Map.class));
            //Finish the activity
            finish();
            //Calling return
            return;
        }


        //Save data in database on click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();

            }


        });
    }

    private void uploadData() {
        String nFirstName = firstName.getText().toString().trim();
        String nSecondName = secondName.getText().toString().trim();
        String nUserName = userName.getText().toString().trim();
        String nEmailAddress = emailAddress.getText().toString().trim();
        String nPhoneNumber = phoneNumber.getText().toString().trim();


        if (!TextUtils.isEmpty(nFirstName)
                && !TextUtils.isEmpty(nSecondName)
                && !TextUtils.isEmpty(nUserName)
                && !TextUtils.isEmpty(nPhoneNumber)
                && !TextUtils.isEmpty(nEmailAddress)
        ) {
            String userKey = databaseReference.push().getKey();
            UserClass userClass = new UserClass(nFirstName, nSecondName, nEmailAddress, nPhoneNumber, nUserName);
            databaseReference.child(userKey).setValue(userClass);
            firstName.setText("");
            secondName.setText("");
            userName.setText("");
            emailAddress.setText("");
            phoneNumber.setText("");

            //Asking for permission
            Dexter.withContext(MainActivity.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse)
                        {
                            startActivity(new Intent(MainActivity.this, Map.class));
                            finish();

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse)
                        {
                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken token)
                        {
                            token.continuePermissionRequest();

                        }
                    }).check();




        }
        else
        {
            Toast.makeText(this, "please fill in all the spaces and then try again", Toast.LENGTH_SHORT).show();
        }


    }


}











