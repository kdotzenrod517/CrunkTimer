package com.kdotz.timerapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerText;
    SeekBar seekBar;
    Boolean isActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        timerText.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        isActive = false;
    }

    public void startTimer(View view) {

        if (isActive) {
            resetTimer();
        } else {
            isActive = true;
            seekBar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                public void onTick(long millisecondsUntilDone) {
                    updateTimer((int) (millisecondsUntilDone / 1000));
                }

                @Override
                public void onFinish() {
                    playSound();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int progress) {
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if (secondString.equals("0")) {
            secondString = "00";
        } else if (Integer.valueOf(secondString) < 10) {
            secondString = "0" + secondString;
        }
        timerText.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        timerText = (TextView) findViewById(R.id.timer);
        goButton = (Button) findViewById(R.id.button);

        int max = 600; //60 seconds in a minute
        int startingPos = 30;
        seekBar.setMax(max);
        seekBar.setProgress(startingPos);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void playSound() {
        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.yeeeeeeeah);
        mPlayer.start();
    }
}
