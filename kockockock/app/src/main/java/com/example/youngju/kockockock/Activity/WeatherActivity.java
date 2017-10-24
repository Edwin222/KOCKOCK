package com.example.youngju.kockockock.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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


import static android.R.attr.max;
import static android.R.attr.path;
import static android.R.attr.start;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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
        String latitude = path.getList().get(0).getLatitude();    //나중에 이것도 받아와야 함 지금은 임시로
        String longitude = path.getList().get(0).getLongitude();   //이것두
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
        for(int i=0; i<7; i++){
            String w = weatherinfo_Array[i].getSkyCode().toLowerCase();
            int number = Integer.parseInt(w.substring(5));

            if( w.charAt(4) == 's' && number <= 6)
                w  = w + "_1";
            if(w.charAt(4) == 'w' && ( (number >= 1 && number <=3) || number == 9 || number == 12 ) )
                w = w + "_1";

            //w = w + ".png";

            weatherImage_SrcName_array.add(w);
        }


        String packageName = this.getPackageName();
        ArrayList<ImageView> imageView_array = new ArrayList<ImageView>();

        // 날씨 이미지 뷰 찾아서 arrayList에 넣기
        for(int i=0; i< 7; i++)
            imageView_array.add( (ImageView) findViewById( getResources().getIdentifier( "dayImageView" + (i+1) , "id", packageName ) ) );

        // 이미지뷰에 날씨 설정하기
        for(int i=0; i<7; i++)
            imageView_array.get(i).setImageResource( getResources().getIdentifier( weatherImage_SrcName_array.get(i), "drawable", packageName) );


        //오늘의 온도 정보 텍스트 설정
        TextView todayTempText = (TextView) findViewById(R.id.todayTeamperText);
        todayTempText.setText( "최고 온도:" + weatherinfo_Array[0].getMaxT() +  "/ 최저 온도:" + weatherinfo_Array[0].getMinT());

        //이번주 온도 정보 텍스트 설정
        for(int i=1; i<7; i++){
            TextView dayText = (TextView) findViewById( getResources().getIdentifier( "dayText" + (i+1), "id", packageName) );
            dayText.setText( "날짜:" + dayAfter(travelInfo.getStartTime(), i));
            dayText = (TextView) findViewById(getResources().getIdentifier( "tempText" + (i+1), "id", packageName));

            String maxT;
            String minT;

            //0~2번째 날씨까지는 온도가 소수로 나와서 정수로 바꿔주는거
            if(i<3) {
                int dotIdx = weatherinfo_Array[i].getMaxT().indexOf('.', 0);
                maxT = weatherinfo_Array[i].getMaxT().substring(0, dotIdx);
                dotIdx = weatherinfo_Array[i].getMinT().indexOf('.', 0);
                minT = weatherinfo_Array[i].getMinT().substring(0, dotIdx);

                maxT = maxT + "℃";
                minT = minT + "℃";
            }else{
                maxT = weatherinfo_Array[i].getMaxT();
                minT = weatherinfo_Array[i].getMinT();
            }

            ///////////////////////////////////////////////
            //색깔 바꾸기
            /*
            SpannableStringBuilder ssb = new SpannableStringBuilder(str);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#5F00FF")), 6, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            */
            //
            //////////////////////////////////////////////

            dayText.setText( maxT + "/" + minT);
        }
    }

    private String dayAfter(Date startTime, int afterDay){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, startTime.getYear()-100);    //getYear함수 값이상함
        cal.set(Calendar.MONTH, startTime.getMonth()+1);
        cal.set(Calendar.DATE, startTime.getDate());

        cal.add(Calendar.DATE, afterDay);

        return Integer.toString(cal.get(Calendar.MONTH)) + "/" + cal.get(Calendar.DATE);
    }
}
