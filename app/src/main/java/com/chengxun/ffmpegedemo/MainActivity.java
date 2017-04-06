package com.chengxun.ffmpegedemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FFmpeg fFmpeg;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFF();
        initView();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.edit);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    private void initFF() {
        fFmpeg = FFmpeg.getInstance(this);
        try {
            fFmpeg.loadBinary(new LoadBinaryResponseHandler());
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        String[] split = editText.getText().toString().split(" ");
        try {
            fFmpeg.execute(split,new ExecuteBinaryResponseHandler(){
                @Override
                public void onSuccess(String message) {
                    Log.i("TAG", "onSuccess" + message);
                }

                @Override
                public void onProgress(String message) {
                    Log.i("TAG", "onProgress" + message);
                }

                @Override
                public void onFailure(String message) {
                    Log.i("TAG", "onFailure" + message);
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }
    }
}
