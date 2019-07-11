package com.example.volumnview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VolumeView view=(VolumeView)findViewById(R.id.volumeView);


        view.setOnChangeListener(new VolumeView.OnChangeListener() {

            @Override
            public void onChange(int count) {
//                Toast.makeText(MainActivity.this,"当前音量："+count,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdd(int count) {

            }

            @Override
            public void onReduce(int count) {

            }
        });
    }
}
