package com.mils.ecornerimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mils.ecornerimageview.view.ECornerImageView;

public class MainActivity extends AppCompatActivity {

    private ECornerImageView eImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eImg = (ECornerImageView)findViewById(R.id.eimg);
        //eImg.setCorner(false,true,true,false);
        //eImg.setRadius(40);
        eImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
