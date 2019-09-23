package com.hansung.android.restaurant;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

//지도를 보여주는 코드
public class Map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION=1;
    private FusedLocationProviderClient mFusedLocationClient;
    Location mCurrentLocation;
    GoogleMap mGoogleMap = null;
    private DBHelper DbHelper;
    private DBHelper3 mDbHelper3;
    double distance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //====================API 23이상부터는 권한을 반드시 요청====================================================
        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else{
            getLastLocation();
        }

        Button btn1 = (Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddress();

            }
        });

        DbHelper = new DBHelper(this);
        mDbHelper3 = new DBHelper3(this);
    }

    //================================옵션 메뉴 보여주는 코드=========================================
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //==============================맵의 액션바=====================================
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //========================현재 위치(파란색)와 등록된 맛집을 보여주는 코드==========================
            case R.id.quick_action1:
                getLastLocation(); //현재 위치
                All();
                return true;

            //==================================MAP 거리 수에 따라 등록된 맛집 보여주는 코드===============================
            case R.id.map_1km:
                item.setChecked(true);
                map_1km();
                return true;

            case R.id.map_2km:
                item.setChecked(true);
                map_2km();
                return true;

            case R.id.map_3km:
                item.setChecked(true);
                map_3km();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //====================API 23이상부터는 권한을 반드시 요청====================================================
    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions(int requestCode) {
        ActivityCompat.requestPermissions(
                Map.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                requestCode    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    //==================================현재 위치를 빨간색으로 보여주는 코드===============================
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        Task task = mFusedLocationClient.getLastLocation();       // Task<Location> 객체 반환
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                Log.i("TAG","onSuccess");

                if (location != null) {
                    mCurrentLocation = location;
                    LatLng location1 = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1,15));
                    mGoogleMap.addMarker(
                            new MarkerOptions().
                                    position(location1)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    );
                } else
                    Toast.makeText(getApplicationContext(), "No Location detected", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
//================================맵이 실행되자마자 준비하는 코드=====================================
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setOnMarkerClickListener(new MyMarkerClickListener());

    }

    //=========================위도 경도를 저장하고 출력해서 결과에 보여주는 코드===============================
    public void getAddress() {
        EditText inputedit = (EditText) findViewById(R.id.edit_text);
        TextView addressTextView = (TextView) findViewById(R.id.result);
        TextView addressTextView2 = (TextView) findViewById(R.id.result2);
        String input = inputedit.getText().toString();
        try {
            //==================Geocoding : 위치좌표를 주소로, 주소를 위치자표로 ==============================
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input,1);
            if (addresses.size() >0) {
                Address address = addresses.get(0);
                addressTextView.setText(String.format("%s", address.getLatitude()));
                addressTextView2.setText(String.format("%s",address.getLongitude()));

                LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(location).
                                title(input)
                );
                mGoogleMap.setOnMarkerClickListener(this);
            }
        } catch (IOException e) {
            Log.e("LocationService", "Failed in using Geocoder",e);
        }

    }

    @Override//=================================마커를 클릭했을 때 저장하겠냐는 말 나오는 코드=================================
    public boolean onMarkerClick(Marker marker) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("맛집 등록");
        alert.setMessage("새로운 맛집으로 하시겠습니까 ? ");
        alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent new_intent = new Intent(getApplicationContext(), RestaurantRegister.class);
                startActivity(new_intent);
                insertRecord();
            }
        });
        alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();
        return true;
    }


    //==============================맛집 등록에 위도 경도를 저장시켜주기======================================
    private void insertRecord() {
        EditText inputedit = (EditText) findViewById(R.id.edit_text);
        TextView addressTextView = (TextView) findViewById(R.id.result);
        TextView addressTextView2 = (TextView) findViewById(R.id.result2);
        String input = inputedit.getText().toString();

        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input,1);
            if (addresses.size() >0) {
                Address address = addresses.get(0);
                addressTextView.setText(String.format("%s", address.getLatitude()));
                addressTextView2.setText(String.format("%s",address.getLongitude()));

                LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(location).
                                title(input)
                );
                mGoogleMap.setOnMarkerClickListener(this);
            }
        } catch (IOException e) {
            Log.e("LocationService", "Failed in using Geocoder",e);
        }

        long nOfRows = mDbHelper3.insertUserByMethod(addressTextView.getText().toString(), addressTextView2.getText().toString());
        if (nOfRows > 0) {
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
        }
        //오류나면 data-data-databases 에서 db삭제하고 다시해보기
    }

//============================map 거리에 따라 표시해주기============================
    //다지우고
    //거리계산
    //거리이내 장소만
    //마커표시

    private void All() {
        //https:stackoverflow.com/questions/17983865/making-a-location-object-in-android-with-latitude-and-longitude-values
        Location location = new Location("");

        location.setLatitude(mCurrentLocation.getLatitude());
        location.setLongitude(mCurrentLocation.getLongitude());
        mGoogleMap.clear();

        Cursor cursor = DbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location2 = new Location("");
            location2.setLatitude(x);
            location2.setLongitude(y);
            distance = location.distanceTo(location2);

            if(distance<1000000000){
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current).
                        title(cursor.getString(2)));

                LatLng latLng = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .alpha(0.6f)
                                .title(cursor.getString(2))
                );
            }
        }
    }

    private void map_1km() {

        Location location = new Location("");

        location.setLatitude(mCurrentLocation.getLatitude());
        location.setLongitude(mCurrentLocation.getLongitude());
        mGoogleMap.clear();

        Cursor cursor = DbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location2 = new Location("");
            location2.setLatitude(x);
            location2.setLongitude(y);
            distance = location.distanceTo(location2);

            if(distance<1000){
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current).
                        title(cursor.getString(2)));

                LatLng latLng = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .alpha(0.6f)
                                .title(cursor.getString(2))
                );
            }
        }
    }

    private void map_2km() {

        Location location = new Location("");

        location.setLatitude(mCurrentLocation.getLatitude());
        location.setLongitude(mCurrentLocation.getLongitude());
        mGoogleMap.clear();

        Cursor cursor = DbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location2 = new Location("");
            location2.setLatitude(x);
            location2.setLongitude(y);
            distance = location.distanceTo(location2);

            if(distance<2000){
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current).
                        title(cursor.getString(2)));

                LatLng latLng = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .alpha(0.6f)
                                .title(cursor.getString(2))
                );
            }
        }

    }

    private void map_3km() {

        Location location = new Location("");

        location.setLatitude(mCurrentLocation.getLatitude());
        location.setLongitude(mCurrentLocation.getLongitude());
        mGoogleMap.clear();

        Cursor cursor = DbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()) { // 위도랑 경도 찾음
            double x = cursor.getDouble(5);
            double y = cursor.getDouble(6);

            Location location2 = new Location("");
            location2.setLatitude(x);
            location2.setLongitude(y);
            distance = location.distanceTo(location2);

            if(distance<3000){
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                mGoogleMap.addMarker( new MarkerOptions().
                        position(current).
                        title(cursor.getString(2)));

                LatLng latLng = new LatLng(x, y);
                mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .alpha(0.6f)
                                .title(cursor.getString(2))
                );
            }
        }

    }

    class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Cursor cursor = DbHelper.getAllUsersByMethod();

            if (cursor.moveToFirst()) {
                        Intent intent = new Intent(getApplicationContext(), Restaurant.class);
                        startActivity(intent);
                        return false;
            }
            return false;
        }
    }
}
