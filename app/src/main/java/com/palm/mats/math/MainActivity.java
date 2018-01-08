package com.palm.mats.math;

import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Game game;
    ArrayList<Operator> operatorList;
    TextView tv_x, tv_y, tv_z, tv_operand, tv_equals;
    String userInput = new String();
    RatingBar rb;

    Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;

    private SoundPool soundPool;

    private AudioManager audioManager;

    // Maximumn sound stream.
    private static final int MAX_STREAMS = 5;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private boolean loaded;
    private int soundIdWin;
    private int soundIdLoose;

    private int soundClickId;
    private float volume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void initSound() {

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // For Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // for Android SDK < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        this.soundIdWin = this.soundPool.load(this, R.raw.win,1);
        this.soundIdLoose = this.soundPool.load(this, R.raw.loose,1);

        this.soundClickId = this.soundPool.load(this, R.raw.click,1);


    }

    private void init() {

        initSound();
        tv_x = findViewById(R.id.edTxtX);
        tv_y = findViewById(R.id.edTxtY);
        tv_z = findViewById(R.id.edTxtZ);
        tv_operand = findViewById(R.id.tv_operand);
        tv_equals = findViewById(R.id.tv_equals);

        rb = findViewById(R.id.ratingBar2);

        b0 = findViewById(R.id.button0);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);

        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);

        operatorList =  new ArrayList<Operator>();
        operatorList.add(Operator.MINUS);
        operatorList.add(Operator.PLUS);


        game = new Game(10, 1, operatorList);
        newExpression();


    }

    private void newExpression() {

        game.newExpression();
        userInput="";
        updateGui();

    }

    public void updateGui(){

        switch (game.getGameMode()) {

            case FIND_X_TERM:
                tv_x.setText(userInput);
                tv_y.setText(Integer.toString(game.getY()));
                tv_z.setText(Integer.toString(game.getZ()));
                break;
            case FIND_Y_TERM:
                tv_x.setText(Integer.toString(game.getX()));
                tv_y.setText(userInput);
                tv_z.setText(Integer.toString(game.getZ()));
                break;
            case FIND_Z_TERM:
                tv_x.setText(Integer.toString(game.getX()));
                tv_y.setText(Integer.toString(game.getY()));
                tv_z.setText(userInput);
                break;
        }

        tv_operand.setText(game.getOperand());

        // Kolla om antal tecken inmatat är lika många som tecken i det förväntade svaret
        if ((tv_x.getText().length() == Integer.toString(game.getX()).length()) &&
                (tv_y.getText().length() == Integer.toString(game.getY()).length()) &&
                (tv_z.getText().length() == Integer.toString(game.getZ()).length())) {

            // Kolla om x,y,z är som förväntat i expresson
            if ((tv_x.getText().toString().equals(Integer.toString(game.getX()))) &&
                    (tv_y.getText().toString().equals(Integer.toString(game.getY()))) &&
                    (tv_z.getText().toString().equals(Integer.toString(game.getZ())))) {

                this.soundPool.play(this.soundIdWin,1, 1, 1, 0, 1f);

             //   Toast toast = Toast.makeText(MainActivity.this,"Rätt!", Toast.LENGTH_SHORT);
                Toast toast = new Toast(this);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LayoutInflater inflater = getLayoutInflater();
                View winLayout = inflater.inflate(R.layout.win, (ViewGroup)findViewById(R.id.winlayout));
                toast.setView(winLayout);
                TextView tv_win = (TextView)  winLayout.findViewById(R.id.TextViewWin) ;
                tv_win.setText(game.toString());
                toast.show();


                final Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                fadeOut.setDuration(1000);
                fadeOut.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // start fadeIn when fadeOut ends (repeat)
                        newExpression();
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }
                });

                tv_x.startAnimation(fadeOut);
                tv_y.startAnimation(fadeOut);
                tv_z.startAnimation(fadeOut);
                tv_operand.startAnimation(fadeOut);
                tv_equals.startAnimation(fadeOut);


                game.increaseCorrect();
                rb.setRating(game.getRate());

            } else
            {
                Toast toast = new Toast(this);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LayoutInflater inflater = getLayoutInflater();
                View winapperance = inflater.inflate(R.layout.loose, (ViewGroup)findViewById(R.id.looselayout));
                toast.setView(winapperance);
                toast.show();

                this.soundPool.play(this.soundIdLoose,1, 1, 1, 0, 1f);

                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                userInput="";
                updateGui();
            }

        }

        // Kolla om antal tecken inmatat är för stort  jämfört med det förväntade svaret
        else if ((tv_x.getText().length() > Integer.toString(game.getX()).length()) ||
                (tv_y.getText().length() > Integer.toString(game.getY()).length()) ||
                (tv_z.getText().length() > Integer.toString(game.getZ()).length())) {

            Toast toast = new Toast(this);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LayoutInflater inflater = getLayoutInflater();
            View winapperance = inflater.inflate(R.layout.loose, (ViewGroup)findViewById(R.id.looselayout));
            toast.setView(winapperance);
            toast.show();
            this.soundPool.play(this.soundIdLoose,1, 1, 1, 0, 1f);

            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            userInput="";
            updateGui();
        }


    }

    @Override
    public void onClick(View view) {

        this.soundPool.play(this.soundClickId,1, 1, 1, 0, 1f);

        switch (view.getId()) {

            case R.id.button0:
                userInput += "0";
                break;

            case R.id.button1:
                userInput += "1";
                break;

            case R.id.button2:
                userInput += "2";
                break;

            case R.id.button3:
                userInput += "3";
                break;

            case R.id.button4:
                userInput += "4";
                break;

            case R.id.button5:
                userInput += "5";
                break;

            case R.id.button6:
                userInput += "6";
                break;

            case R.id.button7:
                userInput += "7";
                break;

            case R.id.button8:
                userInput += "8";
                break;

            case R.id.button9:
                userInput += "9";
                break;

        }

        updateGui();

    }
}
