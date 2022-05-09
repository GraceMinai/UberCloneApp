package com.benter.uberapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;


public class Map<locationRequest> extends  AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    private GoogleMap map;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Location userLastKnownLocation;
    private Location location;
    private final float DEFAULT_ZOOM = 14;

    TextView txt_Destination;
    Spinner spinner;
    Button btn_ride, btn_confirmRide;
    Dialog showDetailsDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //hiding the toolbar
        getSupportActionBar().hide();


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_id);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        spinner = findViewById(R.id.myspinner);
        txt_Destination = findViewById(R.id.tv_destination);
        btn_ride = findViewById(R.id.btn_requestRide);





        //Initialize pop up dialog
        showDetailsDialog = new Dialog(this);

        //Array Adapter of destinations
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.available_destinations, android.R.layout.simple_list_item_1);
        //Seting a dropdown layout for the adapter
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //setting the adapter to the spinner
        spinner.setAdapter(arrayAdapter);
        //Allowing the user to select a destination on the list
        spinner.setOnItemSelectedListener(this);



    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //requesting the user to enable gps
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);


        SettingsClient settingsClient = LocationServices.getSettingsClient(Map.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(Map.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();

            }


        });




    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task)
                    {
                        if (task.isSuccessful())
                        {
                            userLastKnownLocation = task.getResult();

                            if (userLastKnownLocation !=null){
                                LatLng currentUserLocation = new LatLng(userLastKnownLocation.getLatitude(),userLastKnownLocation.getLongitude());
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLocation,DEFAULT_ZOOM));

                            }
                            else
                            {
                                //Proceed checking the location again if task is not successful

                            }
                        }



                    }
                });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        //Displaying the selected Destination



        if (adapterView.getSelectedItemPosition() == 0)
        {
            map.clear();
            txt_Destination.setText("Destination: " +adapterView.getItemAtPosition(position).toString());
            MarkerOptions kondeleMarker = new MarkerOptions();
            kondeleMarker.position(new LatLng(-0.08386913210141354, 34.77418514003305));
            kondeleMarker.title("Kondele");
            map.addMarker(kondeleMarker);
            map.animateCamera(CameraUpdateFactory.newLatLng(kondeleMarker.getPosition()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(kondeleMarker.getPosition(), DEFAULT_ZOOM));
        }
        else if(adapterView.getSelectedItemPosition() == 1)
        {
            map.clear();
            txt_Destination.setText("Destination: " +adapterView.getItemAtPosition(position).toString());
            MarkerOptions dungaMarker = new MarkerOptions();
            dungaMarker.position(new LatLng(-0.1445493739390495, 34.73672356437532));
            dungaMarker.title("Dunga Beach");
            map.addMarker(dungaMarker);
            map.animateCamera(CameraUpdateFactory.newLatLng(dungaMarker.getPosition()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(dungaMarker.getPosition(), DEFAULT_ZOOM));

        }
        else if (adapterView.getSelectedItemPosition() ==2)
        {
            map.clear();
            txt_Destination.setText("Destination: " +adapterView.getItemAtPosition(position).toString());
            MarkerOptions airportMarker = new MarkerOptions();
            airportMarker.position(new LatLng(-0.07969779187047789, 34.72814396025931));
            airportMarker.title("Airport");
            map.addMarker(airportMarker);
            map.animateCamera(CameraUpdateFactory.newLatLng(airportMarker.getPosition()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(airportMarker.getPosition(), DEFAULT_ZOOM));

        }
        else if(adapterView.getSelectedItemPosition() == 3)
        {
            map.clear();
            txt_Destination.setText("Destination: " +adapterView.getItemAtPosition(position).toString());
            MarkerOptions acaciaHotelMarker = new MarkerOptions();
            acaciaHotelMarker.position(new LatLng(-0.10746239268646703, 34.75176324331799));
            acaciaHotelMarker.title("Acacia Hotel");
            map.addMarker(acaciaHotelMarker);
            map.animateCamera(CameraUpdateFactory.newLatLng(acaciaHotelMarker.getPosition()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(acaciaHotelMarker.getPosition(), DEFAULT_ZOOM));

        }
        else if(adapterView.getSelectedItemPosition() == 4)
        {
            map.clear();
            txt_Destination.setText("Destination: " +adapterView.getItemAtPosition(position).toString());
            MarkerOptions kisumuCBDMarker = new MarkerOptions();
            kisumuCBDMarker.position(new LatLng(-0.10488122925494761, 34.753320083411914));
            kisumuCBDMarker.title("Kisumu CBD");
            map.addMarker(kisumuCBDMarker);
            map.animateCamera(CameraUpdateFactory.newLatLng(kisumuCBDMarker.getPosition()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(kisumuCBDMarker.getPosition(), DEFAULT_ZOOM));

        }


        //For showing amount
        btn_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailsDialog.setContentView(R.layout.amount_popup);
                showDetailsDialog.getWindow().setBackgroundDrawableResource(R.color.purple_700);
                //showing the popup
                showDetailsDialog.show();

                //Showing details
                TextView txt_popUp_Destination = (TextView) showDetailsDialog.findViewById(R.id.popup_user_destination);
                TextView txt_popupRideTime = (TextView) showDetailsDialog.findViewById(R.id.popup_rideTime);
                TextView txt_popUpAmount = (TextView) showDetailsDialog.findViewById(R.id.popup_amount);

                //Showing destination
                txt_popUp_Destination.setText(adapterView.getItemAtPosition(position).toString());

                //Showing amount and time taken to reach the destination
                if (adapterView.getSelectedItemPosition() == 0)
                {
                    txt_popUpAmount.setText("KES 160");
                    //Show time
                    txt_popupRideTime.setText("14 minutes");
                }
                else if (adapterView.getSelectedItemPosition() == 1)
                {
                    txt_popUpAmount.setText("KES 280");
                    //Show time
                    txt_popupRideTime.setText("43 minutes");
                }
                else if(adapterView.getSelectedItemPosition() == 2)
                {
                    txt_popUpAmount.setText("KES 310");
                    //Show time
                    txt_popupRideTime.setText("25 minutes");
                }
                else if(adapterView.getSelectedItemPosition() == 3)
                {
                    txt_popUpAmount.setText("KES 140");
                    //Show time
                    txt_popupRideTime.setText("6 minutes");
                }
                else if (adapterView.getSelectedItemPosition() == 4)
                {
                    txt_popUpAmount.setText("KES 140");
                    //Show time
                    txt_popupRideTime.setText("5 minutes");
                }

                //Confirming the ride, showing the drivers car on the map
                btn_confirmRide = (Button) showDetailsDialog.findViewById(R.id.btn_confirmRide);
                btn_confirmRide.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        showDriversCar();
                    }
                });



            }
        });






    }

    //For showing Drivers Car on the map
    private void showDriversCar()
    {
        MarkerOptions driverMarker = new MarkerOptions();
        driverMarker.position(new LatLng(-0.10050298017708915, 34.75856508342977));
        driverMarker.title("Your driver");
        driverMarker.icon(getBitmapDescriptor(R.drawable.ic_driverscar));
        map.addMarker(driverMarker);
        map.animateCamera(CameraUpdateFactory.newLatLng(driverMarker.getPosition()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(driverMarker.getPosition(), DEFAULT_ZOOM));
        showDetailsDialog.dismiss();



    }

    //For converting vector assets into Bitmap so that we can safely use them on the map as Markers
    private BitmapDescriptor getBitmapDescriptor(@DrawableRes int resId)
    {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), resId, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        txt_Destination.setText("Select Your Destination");


    }
}














