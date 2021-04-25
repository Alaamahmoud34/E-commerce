package com.example.e_commerce;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.service.textservice.SpellCheckerService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText locTxt;
    Button locBtn , NextBtn;
    LocationManager locManager;
    myLocationListner locListener;
    String address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        locTxt=findViewById(R.id.locationTxt);
        locBtn=findViewById(R.id.locationBtn);
        NextBtn=findViewById(R.id.NextBtn);
        locListener =new myLocationListner(this);
        locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        try{
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0, locListener);
        }catch (SecurityException ex)
        {
            Toast.makeText(this,"You are not allowed to access the current location",Toast.LENGTH_LONG).show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(GoogleMapsActivity.this);
                builder.setTitle("Information Confirmation");
                final Bundle bundle=getIntent().getExtras();
                builder.setMessage("Your Address is : "+address+"\n\n"+"Email : "+MainActivity.custName+"\n\n" +
                        ""+"Total Price : "+bundle.getFloat("TotalPrice"));
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB_Helper db=new DB_Helper(getApplicationContext());
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDateTime now = LocalDateTime.now();
                        String messageText="Thanks '"+MainActivity.custName+"' for using E-commerce for your shopping \n\n" +
                                "- Your order will be sent to this address:\n\t'"+address+"'\n\n- Order details:\n\t" +
                                "Price for one\tQuantity\tProduct Name\n\t\t";

                        for(int i=0 ; i<MainActivity.cartBundle.size();i++) {
                            try {
                                db.Add_Order(dtf.format(now), MainActivity.CustID, address,
                                        MainActivity.cartBundle.get(i).getString("ProdName"),
                                        MainActivity.cartBundle.get(i).getInt("ProdQuantity"));

                                messageText+=MainActivity.cartBundle.get(i).getFloat("ProdPrice")+"\t\t"
                                        +MainActivity.cartBundle.get(i).getInt("ProdQuantity")+"\t\t"
                                        +MainActivity.cartBundle.get(i).getString("ProdName")+"\n\n\n\t\t";
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        messageText+="\n\n- Total price: " + bundle.getFloat("TotalPrice");
                        dialog.cancel();
                        MainActivity.cartBundle.clear();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        Toast.makeText(getApplicationContext(),"Your order has been submitted",Toast.LENGTH_LONG).show();

                        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        send_message(messageText);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the fragmentUser will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the fragmentUser has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));

        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc=null;
                try {
                    loc=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(),"you didn't allow to access the current location",Toast.LENGTH_LONG).show();
                }

                if(loc!=null)
                {
                    LatLng myPosition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try {
                        addressList=coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                        if(!addressList.isEmpty())
                        {
                            address="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                                address+=addressList.get(0).getAddressLine(i)+",";
                            mMap.addMarker(new MarkerOptions().position(myPosition).title("My location").snippet(address)).setDraggable(true);
                            locTxt.setText(address);
                        }
                    } catch (IOException e) {
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                }
                else
                    Toast.makeText(getApplicationContext(),"Please wait until your position is determind",Toast.LENGTH_LONG).show();
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener(){
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                try {
                    addressList=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty())
                    {
                        address="";
                        for(int i=0 ; i<=addressList.get(0).getMaxAddressLineIndex();i++)
                            address+=addressList.get(0).getAddressLine(i);
                        locTxt.setText(address);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No address for this location", Toast.LENGTH_LONG).show();
                        locTxt.getText().clear();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Can't get the address , check your network",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void send_message(String text)
    {
        final String username="alaama7moud34@gmail.com";
        final String password="Alaa01117960698";
        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        Session session=Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        try{
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(MainActivity.custName));
            message.setSubject("Order Submission");
            message.setText(text);
            Transport.send(message);
            Toast.makeText(getApplicationContext(),"Email sent successfully",Toast.LENGTH_LONG).show();
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
}