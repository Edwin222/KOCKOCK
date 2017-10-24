package com.example.youngju.kockockock.CustomMarker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youngju.kockockock.R;
import com.example.youngju.kockockock.System.DataUnit.Region;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

/**
 * Created by YoungJu on 2017-09-22.
 */

public class CustomMarker implements Serializable{
    GoogleMap mMap;
    View marker_root_view;
    TextView tv_marker;
    Context context;

    public CustomMarker(Context context, GoogleMap mMap) {
        this.context = context;
        this.mMap = mMap;

        setCustomMarkerView();
    }

    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private void setCustomMarkerView() {
        marker_root_view = LayoutInflater.from(context).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }

    public Marker addMarker(Region region) {

        double x = Double.parseDouble(region.getLatitude()) + 0  ;
        double y = Double.parseDouble(region.getLongitude()) + 0  ;
        LatLng newRegion = new LatLng(x, y);
        String formatted = region.getName();

        if(formatted.length() > 6) tv_marker.setPadding(0,0,0,0);
        else Log.d("test","CustomMarker : name -> " +formatted+ " " +formatted.length());

        tv_marker.setText(formatted);

        if (region.getChosenStatus() == Region.C_SELECTED) {
            tv_marker.setBackgroundResource(R.drawable.clicked);
            tv_marker.setTextColor(Color.BLACK);
        } else if (region.getChosenStatus() == Region.C_NOTSELECTED){
            tv_marker.setBackgroundResource(R.drawable.unclicked);
            tv_marker.setTextColor(Color.BLACK);
        } else if(region.getChosenStatus()==Region.C_BEGINPOINT) {
            tv_marker.setBackgroundResource(R.drawable.colorbackground);
            tv_marker.setTextColor(Color.BLACK);
        } else if(region.getChosenStatus()==Region.C_ENDPOINT) {
            tv_marker.setBackgroundResource(R.drawable.uncolorbackground);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("title");
        markerOptions.position(newRegion);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_root_view)));

        return mMap.addMarker(markerOptions);
    }

}
