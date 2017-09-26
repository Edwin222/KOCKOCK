package com.example.youngju.kockockock.CustomMarker;

import android.content.Context;
import android.util.Log;

import com.example.youngju.kockockock.System.DataContainer.RegionContainer;
import com.example.youngju.kockockock.System.DataContainer.RegionManager;
import com.example.youngju.kockockock.System.DataUnit.Region;
import com.example.youngju.kockockock.System.DataUnit.TravelInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by YoungJu on 2017-09-22.
 */

public class MapControl implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    GoogleMap mMap = null;
    RegionManager regionManager = null;
    ArrayList<Region> selectedRegion = null;
    ArrayList<Region> regionContainer = null;
    ArrayList<Marker> markerArrayList = null;
    CustomMarker customMarker = null;
    Context context;
    int type;
    double cameraX,cameraY;

    public MapControl(Context context, GoogleMap mMap, TravelInfo travelInfo, int type) {
        Log.d("test","mapcontrol constructor : context -> " + context);
        this.context = context;
        mMap.setOnMarkerClickListener(this);
        this.mMap = mMap;
        this.type=type;
        selectedRegion=new ArrayList<Region>();
        regionManager = new RegionManager(travelInfo.getMetro(), travelInfo.getCity());
        onMapReady(mMap);
    }

    public void setSelectedRegion(RegionContainer regions){
        for(Region r:regions) {
            selectedRegion.add(r);
            Log.d("test","MapControl setSelectedRegion : add region -> " + r.getName());
        }
        setMaker(type);
    }

    public RegionContainer getSelectedRegion(){
        RegionContainer regionContainer1 = new RegionContainer();
        selectedRegion.get(0).setChoice(Region.C_BEGINPOINT);
        selectedRegion.get(selectedRegion.size()-1).setChoice(Region.C_ENDPOINT);
        for (Region r: selectedRegion){
            Log.d("test","MapControl getSelectedRegion : region -> " + r.getName());
            regionContainer1.add(r);
        }

        return regionContainer1;
    }

    public void drawLine() {

    }

    public void clearMarker() {
        if (markerArrayList == null) return;
        for (Marker m : markerArrayList) {
            m.remove();
        }
    }

    public void setMaker(int type) {
        int cnt = 0;
        String name = "name";

        this.type = type;
        regionContainer = regionManager.getRegionByType(type);

        Log.d("test", "regionContainer size:" + regionContainer.size());

        if (regionContainer != null) {
            for (Region region : regionContainer) {
                Marker marker = customMarker.addMarker(region);
                marker.setTag(region);
                markerArrayList.add(marker);
            }
        } else
            Log.d("test","mapcontrol setMarker : regionContainer(ArrayList<Resion>) is null");

        if (selectedRegion != null) {
            for (Region region : selectedRegion) {
                Marker marker = customMarker.addMarker(region);
                marker.setTag(region);
                markerArrayList.add(marker);
            }
        } else
            Log.d("test","mapcontrol setMarker : selectedRegion(ArrayList<Resion>) is null");

        setCamera();

    }

    public void setCamera(){
        cameraX=0;
        cameraY=0;

        RegionContainer totalregion=new RegionContainer();

        try {
            for (Region r : regionContainer) {
                totalregion.add(r);
            }
        }catch(Exception e ) {}

        try {
            for (Region r : selectedRegion) {
                totalregion.add(r);
            }
        }catch(Exception e ) {}

        try{
            for (Region r: totalregion) {
                cameraX+=Double.parseDouble(r.getLatitude());
                cameraY+=Double.parseDouble(r.getLongitude());
                Log.d("test", "setCamera: add -> x: " + Double.parseDouble(r.getLatitude()) + " y: " + Double.parseDouble(r.getLongitude()));
            }
            double sz=totalregion.size();
            Log.d("test","setCamera: total size->" + sz);
            cameraX=cameraX/sz;
            cameraY=cameraY/sz;
        }catch (Exception e) {}

        if(cameraX==0 && cameraY==0 ) {
            cameraX=38.0;
            cameraY=127.0;
        }

        Log.d("test", "setCamera: final point -> x: " + cameraX + " y: " + cameraY);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cameraX, cameraY)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    public void onMapReady(GoogleMap map) {
        Log.d("test","mapcontrol onMapReady ");
        mMap = map;
        markerArrayList = new ArrayList<Marker>();
        customMarker = new CustomMarker(context, mMap);
        setMaker(type);
        mMap.setOnMarkerClickListener(this);
    }


    public boolean onMarkerClick(Marker marker) {
        Region region = (Region) marker.getTag();
        boolean check = true;

        if (region.getChosenStatus() == Region.C_NOTSELECTED) {
            region.setChoice(Region.C_SELECTED);
            selectedRegion.add(region);
            Log.d("test","selected region:" +  region.getName() );
            regionContainer.remove(region);
        } else if (region.getChosenStatus() == Region.C_SELECTED) {
            selectedRegion.remove(region);
            region.setChoice(Region.C_BEGINPOINT);
            selectedRegion.add(region);
        } else if(region.getChosenStatus()==Region.C_BEGINPOINT){
            selectedRegion.remove(region);
            region.setChoice(Region.C_ENDPOINT);
            selectedRegion.add(region);
        } else if(region.getChosenStatus()==Region.C_ENDPOINT){
            region.setChoice(Region.C_NOTSELECTED);
            selectedRegion.remove(region);
            Log.d("test","unselected region:" +  region.getName() );

            if (type == region.getType())
                regionContainer.add(region);
            else check=false;
        }

        if(check) {
            Marker marker1 = customMarker.addMarker(region);
            marker1.setTag(region);
            markerArrayList.add(marker1);
        }
        markerArrayList.remove(marker);
        marker.remove();

        return false;
    }


}
