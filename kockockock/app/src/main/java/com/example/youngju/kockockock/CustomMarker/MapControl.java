package com.example.youngju.kockockock.CustomMarker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.youngju.kockockock.System.DataContainer.RegionContainer;
import com.example.youngju.kockockock.System.DataContainer.RegionManager;
import com.example.youngju.kockockock.System.DataUnit.Region;
import com.example.youngju.kockockock.System.DataUnit.TravelInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YoungJu on 2017-09-22.
 */

public class MapControl implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, Serializable{

    private GoogleMap mMap = null;
    private RegionManager regionManager = null; // arr of whole markers
    private ArrayList<Region> selectedRegion = null; // arr of all choosed markers in selected type
    private ArrayList<Region> regionContainer = null; // arr of all markers in map
    private ArrayList<Marker> markerArrayList = null;
    private CustomMarker customMarker = null;
    private Context context;
    private Region end;
    private Region beg;
    private int type;
    private double cameraX, cameraY;

    public MapControl(Context context, GoogleMap mMap, TravelInfo travelInfo, int type) {
        Log.d("test", "mapcontrol constructor : context -> " + context);
        this.context = context;
        mMap.setOnMarkerClickListener(this);
        this.mMap = mMap;
        this.type = type;
        selectedRegion = new ArrayList<Region>();
        regionManager = new RegionManager(travelInfo.getMetro(), travelInfo.getCity());
        onMapReady(mMap);
        setCamera();
    }

    public void setSelectedRegion(RegionContainer regions) {
        for (Region r : regions) {
            if (!selectedRegion.contains(r)) selectedRegion.add(r);
            Log.d("test", "MapControl setSelectedRegion : add region -> " + r.getName());
        }
        setMaker(type);
    }

    public RegionContainer storeSelectedRegion() {
        RegionContainer stor= new RegionContainer();
        stor.add(beg);
        for (Region r: selectedRegion) stor.add(r);
        stor.add(end);
        return  stor;
    }

    public Region getBeg() {
        return beg;
    }

    public Region getEnd() {
        return end;
    }

    public RegionContainer getSelectedRegion() {
        RegionContainer regionContainer1 = new RegionContainer();
        /*selectedRegion.get(0).setChoice(Region.C_BEGINPOINT);
        selectedRegion.get(selectedRegion.size()-1).setChoice(Region.C_ENDPOINT);*/
        for (Region r : selectedRegion) {
            Log.d("test", "MapControl getSelectedRegion : region -> " + r.getName());
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
        ArrayList<Region> checkalready = new ArrayList<Region>();
        int cnt = 0;
        String name = "name";

        this.type = type;
        regionContainer = regionManager.getRegionByType(type);

        if (selectedRegion != null) {
            for (Region region : selectedRegion) {
                if (!checkalready.contains(region)) {
                    Marker marker = customMarker.addMarker(region);
                    marker.setTag(region);
                    markerArrayList.add(marker);
                    checkalready.add(region);
                }
            }
            Log.d("test","MapControl: setMarker selectedRegion size:"+selectedRegion.size());
        }

        if (regionContainer != null) {
            for (Region region : regionContainer) {
                if (!checkalready.contains(region)) {
                    Marker marker = customMarker.addMarker(region);
                    marker.setTag(region);
                    markerArrayList.add(marker);
                    checkalready.add(region);
                }
            }
            Log.d("test","MapControl: setMarker regionContainer size:"+regionContainer.size());
        }

        if(beg!=null) {
            Marker marker = customMarker.addMarker(beg);
            marker.setTag(beg);
            markerArrayList.add(marker);
            checkalready.add(beg);
        }

        if(end!=null) {
            Marker marker = customMarker.addMarker(end);
            marker.setTag(end);
            markerArrayList.add(marker);
            checkalready.add(end);
        }

    }

    public void setCamera() {
        cameraX = 0;
        cameraY = 0;

        RegionContainer totalregion = new RegionContainer();

        try {
            for (Region r : regionContainer) {
                totalregion.add(r);
            }
        } catch (Exception e) {
        }

        try {
            for (Region r : selectedRegion) {
                totalregion.add(r);
            }
        } catch (Exception e) {
        }

        try {
            for (Region r : totalregion) {
                cameraX += Double.parseDouble(r.getLatitude());
                cameraY += Double.parseDouble(r.getLongitude());
                Log.d("test", "setCamera: add -> x: " + Double.parseDouble(r.getLatitude()) + " y: " + Double.parseDouble(r.getLongitude()));
            }
            double sz = totalregion.size();
            Log.d("test", "setCamera: total size->" + sz);
            cameraX = cameraX / sz;
            cameraY = cameraY / sz;
        } catch (Exception e) {
        }

        if (cameraX == 0 && cameraY == 0) {
            cameraX = 38.0;
            cameraY = 127.0;
        }

        Log.d("test", "setCamera: final point -> x: " + cameraX + " y: " + cameraY);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cameraX, cameraY)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    public void onMapReady(GoogleMap map) {
        Log.d("test", "mapcontrol onMapReady ");
        mMap = map;
        markerArrayList = new ArrayList<Marker>();
        customMarker = new CustomMarker(context, mMap);
        setMaker(type);
        mMap.setOnMarkerClickListener(this);
    }

    public boolean onMarkerClick(Marker marker) {
        Region region = (Region) marker.getTag();

        if (region.getChosenStatus() == Region.C_NOTSELECTED) {
            region.setChoice(Region.C_SELECTED);
            selectedRegion.add(region);
            Toast.makeText(context,""+region.getName()+" 경로에 추가되었습니다.",Toast.LENGTH_LONG).show();

        } else if (region.getChosenStatus() == Region.C_SELECTED) {
            selectedRegion.remove(region);
            Log.d("test","MapControl: beg:"+beg);
            Log.d("test","MapControl: end:"+end);
            region.setChoice(Region.C_BEGINPOINT);
            selectedRegion.add(region);
            if(beg!=null) {
                beg.setChoice(Region.C_SELECTED);
                if(!selectedRegion.contains(beg))selectedRegion.add(beg);
            }
            beg=region;

            Toast.makeText(context,""+region.getName()+" 출발지로 지정하였습니다.",Toast.LENGTH_LONG).show();
        } else if (region.getChosenStatus() == Region.C_BEGINPOINT) {
            beg=null;
            if(end!=null) {
                end.setChoice(Region.C_SELECTED);
                if(!selectedRegion.contains(end)) selectedRegion.add(end);
            }
            region.setChoice(Region.C_ENDPOINT);
            end=region;
            Toast.makeText(context,""+region.getName()+" 도착지로 지정하였습니다.",Toast.LENGTH_LONG).show();

        } else if (region.getChosenStatus() == Region.C_ENDPOINT) {
            selectedRegion.remove(region);
            region.setChoice(Region.C_NOTSELECTED);
            end=null;
            if(region.getType()==type) regionContainer.add(region);
            Toast.makeText(context,""+region.getName()+" 경로에서 삭제하였습니다.",Toast.LENGTH_LONG).show();
        }

        clearMarker();
        setMaker(type);

        return false;
    }


}
