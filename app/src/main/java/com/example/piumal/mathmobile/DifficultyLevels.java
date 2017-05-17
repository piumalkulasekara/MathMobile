package com.example.piumal.mathmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

/**
 * Created by piumal on 3/11/17.
 */
public class DifficultyLevels extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.difficulty_levels);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.game_icon);

        //Set up Listners for game types menu buttons
        View novoiceButton = findViewById(R.id.novice_button);
        novoiceButton.setOnClickListener(this);

        View easyButton = findViewById(R.id.easy_button);
        easyButton.setOnClickListener(this);

        View mediumButton = findViewById(R.id.medium_button);
        mediumButton.setOnClickListener(this);

        View guruButton = findViewById(R.id.guru_button);
        guruButton.setOnClickListener(this);
    }

    /* Game Types
    *  2: Novoice
    *  3: Easy
    *  4: Medium
    *  5: Guru*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.novice_button:
                startNewGame(2);
                break;
            case R.id.easy_button:
                startNewGame(3);
                break;
            case R.id.medium_button:
                startNewGame(4);
                break;
            case R.id.guru_button:
                startNewGame(6);
                break;
            default:
                break;
        }
    }

    //To Start a new Game according to the game type
    private void startNewGame(int gameType) {
        Intent new_game = new Intent(this, Game.class);
        new_game.putExtra("game_type", gameType);
        new_game.putExtra("questionCount", 0);
        new_game.putExtra("totalMarks", 0);
        new_game.putExtra("atemptCount", MathBrain.userAtempts);
        startActivity(new_game);
    }
}
