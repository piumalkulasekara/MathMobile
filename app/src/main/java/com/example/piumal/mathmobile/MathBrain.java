package com.example.piumal.mathmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by piumal on 3/11/17.
 */
public class MathBrain extends Activity implements View.OnClickListener {
    private Button contButton;
    private int game_type;
    private int marks;
    private int attempts;
    private int questionNum;
    public static String isHintsOn;
    public static int userAtempts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Set up Listeners for main menu
        View newGameButton = findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(this);

        View continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);

        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);

        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);

        contButton = (Button) findViewById(R.id.continue_button);
        continueButton.setEnabled(false);
        getSavedData();
        getPrefs();
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.game, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(this, Prefs.class));
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about_button:
                Intent i_about = new Intent(this, About.class);
                startActivity(i_about);
                break;
            case R.id.new_game_button:
                getPrefs();
                Intent i_newGame = new Intent(this, DifficultyLevels.class);
                startActivity(i_newGame);
                break;
            case R.id.continue_button:
                Intent i_continue = new Intent(this, Game.class);
                i_continue.putExtra("game_type", game_type);
                i_continue.putExtra("questionCount", questionNum);
                i_continue.putExtra("totalMarks", marks);
                i_continue.putExtra("attemptCount", attempts);
                startActivity(i_continue);
            case R.id.exit_button:
                android.os.Process.killProcess(android.os.Process.myPid());

            default:
                break;

        }
    }

    private void getSavedData() {
        try {
            Properties prop = new Properties();
            prop.load(openFileInput("Game.properties"));
            game_type = Integer.parseInt(prop.getProperty("game_type"));
            questionNum = Integer.parseInt(prop.getProperty("questionNum"));
            marks = Integer.parseInt(prop.getProperty("result"));
            attempts = Integer.parseInt(prop.getProperty("attempts"));
            Log.d("myResults", "load game type = " + game_type + " questionNum = " + questionNum + " Result = "+ marks + " attemps = " + attempts);
            if(questionNum < 10){
                contButton.setEnabled(true);
            }
        } catch (Exception e) {
            Log.d("myResults", "no file");
            String data = "game_type=2\nquestionNum=0\nmarks=0\nattempts=1";
            try {
                FileOutputStream outputStream = openFileOutput("Game.properties", Context.MODE_PRIVATE);
                outputStream.write(data.getBytes());
                outputStream.close();
            } catch (Exception e1) {
                e1.printStackTrace();
                Log.d("myResults", "create file error");
            }
            e.printStackTrace();
        }
    }



    public void getPrefs() {
        try {
            Properties prop = new Properties();
            prop.load(openFileInput("Game.prefs"));
            isHintsOn = prop.getProperty("hints");
            if (isHintsOn.equals("on")){
                userAtempts = 4;
            }else
                userAtempts = 1;
            Log.d("myResults", "Hints " + isHintsOn + "Atemptes " + userAtempts);

        } catch (Exception e) {
            Log.d("myResults", "no file");
            String data = "hints = off";
            try {
                FileOutputStream outputStream = openFileOutput("Game.prefs", Context.MODE_PRIVATE);
                outputStream.write(data.getBytes());
                outputStream.close();
                Log.d("myResult", data);
            } catch (Exception e1) {
                e1.printStackTrace();
                Log.d("myResults", "create file error");
            }
            e.printStackTrace();
        }


    }

    //when back button click
    @Override
    public void onBackPressed(){
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
