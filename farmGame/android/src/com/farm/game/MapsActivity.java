package com.farm.game;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    //Static variables
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 0;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    public static final int COLLECT_RESOURCE_REQUEST_CODE = 100;
    public static final int DISTANCE_TO_COLLECT_RESOURCE = 10;

    //Activity views
    private ImageButton btnCloseMap;

    private GoogleMap mMap;
    private LatLng currentLatLng;
    private LatLng lastResourceLoadLocation;
    private Marker currentLocationMarker;
    private Marker selectedResourceMarker;
    private HTTPConnection httpConnection;
    private int maxDistanceForUser = 1000;          //Set default value
    private int initialResourcesToGenerate = 100;    //Set default value (MAX 100!)
    private ArrayList<Resource> preLoadedResources;
    private ArrayList<Marker> currentMarkers;

    private boolean initialLocationLoad;

    private LocationManager mLocationManager;

    //LocationListener with defined onChange event
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                if(currentLocationMarker != null){
                    currentLocationMarker.remove();
                }

                //Create LatLng from current user position
                LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                currentLatLng = newLatLng;

                //Add new marker to the map at new LatLng
                currentLocationMarker = mMap.addMarker(new MarkerOptions()
                        .position(newLatLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(generateBitmap("person_walking", 64, 64))));

                //Initial location load moves camera to current position.
                if(initialLocationLoad){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(17)
                            .tilt(40)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    initialLocationLoad = false;
                }

                if(lastResourceLoadLocation != null){
                    if(Geometry.distanceBetweenTwoLatLng(lastResourceLoadLocation, currentLatLng) > maxDistanceForUser){
                        lastResourceLoadLocation = currentLatLng;
                        generateResources(initialResourcesToGenerate, maxDistanceForUser, currentLatLng);
                    } else {
                        //check amount of resources still available and maybe generate new ones
                    }
                } else {
                    lastResourceLoadLocation = currentLatLng;
                    generateResources(initialResourcesToGenerate, maxDistanceForUser, currentLatLng);
                }
            } else {
                Log.e("MapsActivity", "current location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        loadElements();
        initialLocationLoad = true;
        lastResourceLoadLocation = null;

        //Prepare marker list
        this.currentMarkers = new ArrayList<>();

        //Prepare existing resource list
        this.preLoadedResources = new ArrayList<>();


        //load currently present resources
        Intent intent = getIntent();
        String jsonResources = intent.getStringExtra("savedResources");

        if(jsonResources != null && jsonResources.length() > 0){
            try{
                JSONObject object = new JSONObject(jsonResources);
                lastResourceLoadLocation = new LatLng(object.getDouble("latitude"), object.getDouble("longitude"));
                JSONArray resourceArray = object.getJSONArray("resources");

                //Get existing resources from json in game save
                for(int i = 0; i < resourceArray.length(); i++){
                    JSONObject jsonResource = resourceArray.getJSONObject(i);
                    preLoadedResources.add(Resource.fromJSON(jsonResource));
                }
            } catch (JSONException ex){
                Log.e("MapsActivity", "Unable to read existing resources from JSON format.");
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Create LocationManager for user location updates
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Create HttpConnection object for server communication
        this.httpConnection = new HTTPConnection(this);
    }

    //Load view elements in layout
    private void loadElements(){
        this.btnCloseMap = (ImageButton)this.findViewById(R.id.btnCloseMap);

        try{
            InputStream ims = getAssets().open("exit.png");
            Drawable d = Drawable.createFromStream(ims, null);
            this.btnCloseMap.setImageDrawable(d);
        } catch (IOException ex){
            Log.e("MapsActivity", ex.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMaxZoomPreference(20);
        mMap.setMinZoomPreference(17);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //If selected marker is user marker, move camera towards it.
                if(!marker.equals(currentLocationMarker)){

                    //Open Collect Resource Dialog with options based on the distance to the marker.
                    selectedResourceMarker = marker;
                    Intent intent = new Intent(MapsActivity.this, CollectResourceDialog.class);
                    int type = 0;
                    try{
                        type = new JSONObject(marker.getSnippet()).getInt("resourceType");
                    }catch(Exception ex){
                        Log.e("MapsActivity", ex.getMessage());
                    }

                    if(canCollectResource(marker.getPosition())){
                        intent.putExtra("canCollect", true);
                        intent.putExtra("resourceType", type);
                        startActivityForResult(intent, COLLECT_RESOURCE_REQUEST_CODE);
                    } else {
                        intent.putExtra("canCollect", false);
                        intent.putExtra("resourceType", type);
                        startActivityForResult(intent, COLLECT_RESOURCE_REQUEST_CODE);
                    }
                } else {
                    //Move camera
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                            .zoom(17)
                            .tilt(40)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                return true;
            }
        });

        this.startLocationServices();

        //For every resource in game save, set a marker on the map
        if(preLoadedResources.size() > 0){
            for(Resource resource : preLoadedResources){
                currentMarkers.add(mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(resource.getLocation().latitude, resource.getLocation().longitude))
                        .snippet("{\"resourceType\": " + resource.getType().getValue() + "}")
                        .icon(BitmapDescriptorFactory.fromBitmap(generateBitmap(resource.getType().getIcon(), 64,64)))));
            }
        }
    }

    /**
     * Creates a Bitmap for the given resource
     * @param name - resource name in String format (no extension)
     * @param height - Height for the bitmap
     * @param width - Width for the bitmap
     * @return Bitmap of the resource with needed dimensions
     */
    private Bitmap generateBitmap(String name, int height, int width){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(name, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    /**
     * Gets the user's current location and activates the location manager updates for future location updates
     * @return
     */
    private void startLocationServices() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)){
            Toast.makeText(this, "Locatieservices aanzetten",Toast.LENGTH_SHORT);
        }
        else {
            int permissionCoarse = PermissionChecker.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int permissionFine = PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (isNetworkEnabled) {
                if(permissionCoarse == PermissionChecker.PERMISSION_GRANTED && permissionFine == PermissionChecker.PERMISSION_GRANTED){
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                    //location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }

            if (isGPSEnabled) {
                if(permissionCoarse == PermissionChecker.PERMISSION_GRANTED && permissionFine == PermissionChecker.PERMISSION_GRANTED) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                    //location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        }
        //return location;
    }

    /**
     * Calculates the distance between the user and the selected resource and checks whether it can be collected
     * @param resourcePosition LatLng of the selected resource
     * @return which indicates if the resource can be collected
     */
    private boolean canCollectResource(LatLng resourcePosition){
        return (Geometry.distanceBetweenTwoLatLng(this.currentLatLng, resourcePosition) < DISTANCE_TO_COLLECT_RESOURCE);
    }

    /**
     * Saves currently loaded resources and returns to user farm
     */
    public void btnCloseMapOnClick(View view){
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        JSONObject savedResources = new JSONObject();
        JSONArray resources = new JSONArray();
        try{
            savedResources.put("latitude", lastResourceLoadLocation.latitude);
            savedResources.put("longitude", lastResourceLoadLocation.longitude);
            for(Marker m : currentMarkers){
                JSONObject jsonResource = new JSONObject();
                    JSONObject type = new JSONObject(m.getSnippet());
                    jsonResource.put("resourceType", type.getInt("resourceType"));
                    jsonResource.put("latitude", m.getPosition().latitude);
                    jsonResource.put("longitude", m.getPosition().longitude);
                resources.put(jsonResource);
            }
            savedResources.put("resources", resources);
            prefs.putString("savedResources", savedResources.toString());
            prefs.flush();
        } catch(Exception ex){
            Log.e("MapsActivity", "Unable to save loaded resources.");
        }
        this.finish();
    }

    /**
     * Generates resources
     * @param amount - amount of resources to be created (can differ since google api tries to snap to roads)
     * @param maxDistance - maximum distance for the resources to be generated
     * @param position - Location from which the resources need to be generated
     */
    private void generateResources(int amount, int maxDistance, LatLng position){
        ArrayList<LatLng> points = new ArrayList<>();

        //Generate the needed amount of LatLng
        for(int i = 0; i < amount; i++){
            points.add(generateLatLngFromPosition(maxDistance, position));
        }

        //Google Roads API call via HttpConnection to snap generated locations to roads
        this.httpConnection.getPointsSnappedToRoads(points, new Callback() {
            @Override
            public void taskCompleted(int status_code, JSONObject json) {
                try{
                    JSONArray array = json.getJSONArray("snappedPoints");
                    int lastIndex = -1;

                    //Loop snapped locations
                    for(int i = 0; i < array.length(); i++){
                        MyLatLng myLatLng = MyLatLng.fromJSONObject(array.getJSONObject(i));

                        //Two-way roads generate 2 locations so skip one if needed
                        if(myLatLng.getOriginalIndex() != lastIndex){
                            LatLng googleLatLng = new LatLng(
                                    myLatLng.getLocation().latitude,
                                    myLatLng.getLocation().longitude);

                            //Choose a random resource type
                            ResourceType type = ResourceType.getRandomResourceType();

                            //Add new marker with new locations and new resourcetypes to map
                            currentMarkers.add(mMap.addMarker(new MarkerOptions()
                                    .position(googleLatLng)
                                    .icon(BitmapDescriptorFactory.fromBitmap(generateBitmap(type.getIcon(), 64,64)))
                                    .snippet("{\"resourceType\": " + type.getValue() + "}")));
                            lastIndex = myLatLng.getOriginalIndex();
                        }
                    }
                } catch (JSONException ex){
                    Log.e("MapsActivity", ex.getMessage());
                }
            }
        });
    }

    /**
     * Function which generates a random LatLng from a given position
     * @param maxDistance - Maximum distance for the LatLng to be generated
     * @param fromPosition - LatLng from which the new LatLng should be generated
     * @return LatLng with new location
     */
    private LatLng generateLatLngFromPosition(int maxDistance, LatLng fromPosition){
        Random random = new Random();
        int maxDistanceForY = 0;
        int randX = 0, randY = 0;
        randX = random.nextInt(maxDistance + 1 + maxDistance) - maxDistance;
        maxDistanceForY = (int)Math.sqrt(Math.pow(maxDistance, 2) - Math.pow(randX, 2));
        randY = random.nextInt(maxDistanceForY + 1 + maxDistanceForY) - maxDistanceForY;
        return new LatLng(
                fromPosition.latitude + Geometry.convertMetersToLatLngDistance(randY),
                fromPosition.longitude + Geometry.convertMetersToLatLngDistance(randX));
    }

    /**
     * Event called when user closes Collect Resource Dialog
     * Generates new resources
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COLLECT_RESOURCE_REQUEST_CODE) {
            if(resultCode == RESULT_OK && selectedResourceMarker != null){
                selectedResourceMarker.remove();
                this.currentMarkers.remove(selectedResourceMarker);
                selectedResourceMarker = null;


                //Folowing coce just generates one new random location
                /*LatLng newLatLng = generateLatLngFromPosition(maxDistanceForUser, currentLatLng);
                ResourceType type = ResourceType.getRandomResourceType();
                this.currentMarkers.add(mMap.addMarker(new MarkerOptions()
                        .position(newLatLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(generateBitmap(type.getIcon(), 64,64)))));*/
            }
        }
    }
}
