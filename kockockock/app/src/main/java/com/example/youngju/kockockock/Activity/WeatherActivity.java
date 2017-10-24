package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youngju.kockockock.R;
import com.example.youngju.kockockock.System.APIDatabase.APIGetter;
import com.example.youngju.kockockock.System.DataUnit.Path;
import com.example.youngju.kockockock.System.DataUnit.TravelInfo;
import com.example.youngju.kockockock.System.DataUnit.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;


import static android.R.attr.path;
import static android.R.attr.start;

public class WeatherActivity extends AppCompatActivity {

    private Intent intent;
    private Path path;
    private TravelInfo travelInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Button back=(Button)findViewById(R.id.back_to_com_from_weather);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WeatherActivity.this,CompletePage.class);
                startActivity(intent);
            }
        });


        intent=getIntent();
        path=(Path)intent.getSerializableExtra("Path");
        travelInfo = path.getTravelInfo();

        APIGetter apiGetter = new APIGetter(APIGetter.SKPAPI_WEATHER);
        String latitude = "126.755";    //나중에 이것도 받아와야 함 지금은 임시로
        String longitude = "37.4435";   //이것두
        apiGetter.addParam(latitude, longitude);

        try {
            apiGetter.start();
            apiGetter.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        WeatherInfo weatherinfo_Array[] = (WeatherInfo[]) apiGetter.getResult();
        for( WeatherInfo w : weatherinfo_Array){
            Log.d("WEATHERACTIVITY", w.toString());
        }

        //각 weather code를 upperCase해서 넣기
        //이미지 이름이 소문자로 밖에 안되므로
        ArrayList<String> weatherImage_SrcName_array = new ArrayList<String>();
        for(int i=0; i<7; i++)
            weatherImage_SrcName_array.add( weatherinfo_Array[i].getSkyCode().toLowerCase() );


        String packageName = this.getPackageName();
        ArrayList<ImageView> imageView_array = new ArrayList<ImageView>();

        // 날씨 이미지 뷰 찾아서 arrayList에 넣기
        for(int i=0; i< 7; i++)
            imageView_array.add( (ImageView) findViewById( getResources().getIdentifier( "@id/dayImageView" + (i+1) , "id", packageName ) ) );

        // 이미지뷰에 날씨 설정하기
        for(int i=0; i<7; i++)
            imageView_array.get(i).setImageResource( getResources().getIdentifier( "@drawable/" + weatherImage_SrcName_array.get(i), "drawable", packageName) );


        TextView todayTempText = (TextView) findViewById(R.id.todayTeamperText);
        todayTempText.setText( "최고 온도:" + weatherinfo_Array[0].getMaxT() + "도 / 최저 온도:" + weatherinfo_Array[0].getMinT() + "도");

        for(int i=1; i<7; i++){
            TextView dayText = (TextView) findViewById( getResources().getIdentifier( "@id/dayText" + (i+1), "id", packageName) );
            dayText.setText( "날짜 : " + dayAfter(travelInfo.getStartTime(), i) + "온도: " + weatherinfo_Array[i].getMaxT() + "/" + weatherinfo_Array[i].getMinT() );
        }
    }

    private String dayAfter(Date startTime, int afterDay){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, startTime.getYear());
        cal.set(Calendar.MONTH, startTime.getMonth());
        cal.set(Calendar.DATE, startTime.getDay());

        cal.add(Calendar.DATE, afterDay);

        return Integer.toString( cal.get(Calendar.YEAR) ) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DATE);
    }
}
