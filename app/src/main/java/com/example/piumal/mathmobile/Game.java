package com.example.piumal.mathmobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by piumal on 3/11/17.
 */

public class Game extends Activity implements View.OnClickListener {

    EditText input_text;
    String question;
    int minTextLength;
    int gameType;
    int numberCount;
    char[] logicalOperations = new char[]{'+','-','*','/'};
    int correctAnswer;
    String givenAnswer = "";
    CountDownTimer timer;
    TextView timerText;
    int questionCount;
    int attemptCount;
    int totalMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        //Get the game details (specially if a game is saved)
        Bundle bundle=getIntent().getExtras();
        gameType = bundle.getInt("game_type");
        questionCount = bundle.getInt("questionCount");
        totalMarks = bundle.getInt("totalMarks");
        attemptCount = bundle.getInt("attemptCount");

        //To get the number count of the logical expression
        numberCount = gameType;

        input_text = (EditText) findViewById(R.id.input_textbox);

        //To place the cursor at the end of text
        input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                input_text.setSelection(input_text.getText().length());

            }
        });



        //Set timer
        setTimer();

        //Set question
        setQuestion();

        //Set up listeners for grid buttons
        View btn_Val0 = findViewById(R.id.btnVal0);
        btn_Val0.setOnClickListener(this);

        View btn_Val1 = findViewById(R.id.btnVal1);
        btn_Val1.setOnClickListener(this);

        View btn_Val2 = findViewById(R.id.btnVal2);
        btn_Val2.setOnClickListener(this);

        View btn_Val3 = findViewById(R.id.btnVal3);
        btn_Val3.setOnClickListener(this);

        View btn_Val4 = findViewById(R.id.btnVal4);
        btn_Val4.setOnClickListener(this);

        View btn_Val5 = findViewById(R.id.btnVal5);
        btn_Val5.setOnClickListener(this);

        View btn_Val6 = findViewById(R.id.btnVal6);
        btn_Val6.setOnClickListener(this);

        View btn_Val7 = findViewById(R.id.btnVal7);
        btn_Val7.setOnClickListener(this);

        View btn_Val8 = findViewById(R.id.btnVal8);
        btn_Val8.setOnClickListener(this);

        View btn_Val9 = findViewById(R.id.btnVal9);
        btn_Val9.setOnClickListener(this);

        View btn_enter = findViewById(R.id.btnValEntr);
        btn_enter.setOnClickListener(this);

        View btn_del = findViewById(R.id.btnValDel);
        btn_del.setOnClickListener(this);

        View btn_minus = findViewById(R.id.btnValMinus);
        btn_minus.setOnClickListener(this);

    }

    //On click events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVal0:
                removeQus();
                addText("0");
                break;

            case R.id.btnVal1:
                removeQus();
                addText("1");
                break;

            case R.id.btnVal2:
                removeQus();
                addText("2");
                break;

            case R.id.btnVal3:
                removeQus();
                addText("3");
                break;

            case R.id.btnVal4:
                removeQus();
                addText("4");
                break;

            case R.id.btnVal5:
                removeQus();
                addText("5");
                break;

            case R.id.btnVal6:
                removeQus();
                addText("6");
                break;

            case R.id.btnVal7:
                removeQus();
                addText("7");
                break;

            case R.id.btnVal8:
                removeQus();
                addText("8");
                break;

            case R.id.btnVal9:
                removeQus();
                addText("9");
                break;

            case R.id.btnValDel:
                removeText();
                break;

            case R.id.btnValMinus:
                removeQus();
                addText("-");
                break;

            case R.id.btnValEntr:
                processAnswer();
                break;

            default:
                break;

        }
    }

    //Adding input to the text box
    public void addText(String input){
        givenAnswer = givenAnswer + input;
        input_text.setText(question + givenAnswer);
    }

    //Remove "?" from the question
    public void removeQus(){
        String q = input_text.getText().toString();
        if(q.charAt(q.length()-1) == '?'){
            input_text.setText(q.substring(0, q.length()-1));
        }
    }

    //Removing last character from the text box
    public void removeText(){
        String textString = input_text.getText().toString();
        if( textString.length() > minTextLength ) {
            input_text.setText(textString.substring(0, textString.length() - 1 ));
        }
        if( givenAnswer.length() > 0 ) {
            givenAnswer = givenAnswer.substring(0, givenAnswer.length()-1);
        }

        question = input_text.getText().toString();
    }

    // To get a random number between 0 and 9
    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    //To select a logical operation randomly
    public char randOperation(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return logicalOperations[randomNum];
    }

    //To get a mathematical expression according to the game type
    public String getMathematicalExpression(int numOfElements){
        String expression = "";
        char prevOperation = '\u0000';

        for (int i = 0; i < numOfElements; i++){
            //To avoid coming "0" after "/"
            if (prevOperation == '/'){
                expression = expression + randInt(1, 9);
            }else
                expression = expression + randInt(0, 9);

            if (i != numOfElements-1){
                char currOperation = randOperation(1, 3);
                expression = expression + currOperation;
                prevOperation = currOperation;
            }

        }
        correctAnswer = getAnswer(expression);
        return (expression + " = ") ;

    }

    //To get random number count from the max number count
    public int getNumberCount(int numberCount){
        if (gameType != 6){
            numberCount = randInt(2, numberCount);
        }else if (gameType == 6){
            numberCount = randInt(4, numberCount);
        }
        return numberCount;
    }

    //To set a new question
    public void setQuestion(){
        //Get the Mathematical Expression according to the game type
        numberCount = getNumberCount(gameType);
        question = getMathematicalExpression(numberCount);

        //Set the Mathematical Expression to the text box
        input_text.setText(question + "?");

        //To get the deleteable limit of the text
        minTextLength = input_text.getText().toString().length() - 1;

        //Setting attempts for the new question
        attemptCount = MathBrain.userAtempts;

        startTimer();
    }

    //check whether the answer is empty
    public boolean isEmpty(){
        if (givenAnswer == ""){
            return true;
        }else
            return false;
    }

    //check whether the answer is invalid
    private boolean isInvalid() {
        boolean ok = false;
        try{
            int answer = Integer.parseInt(givenAnswer);
        }catch(NumberFormatException e){
            createNormalToast(e.getMessage(), 0);
            ok = true;
        }
        return ok;
    }

    //To process the given answer
    public boolean processAnswer(){
        boolean ok = true;

        while (questionCount < 10){
            if (isEmpty()){
                ok = false;
                break;
            }
            else if(isInvalid()){
                ok = false;
                break;
            }else{
                int answer = Integer.parseInt(givenAnswer);
                attemptCount--;
                //correct answer
                if (checkAnswer(answer)){
                    setQuestionAfterInterval();
                    questionCount++;
                    break;
                    //wrong answer
                }else if (!checkAnswer(answer)){
                    Log.d("myResult" , "wrong answer");
                    //hints on
                    if (attemptCount > 0){
                        Log.d("myResult" , "hints on");
                        if (answer > correctAnswer){
                            createToast("Greater");
                        }else
                            createToast("Less");

                        String s = input_text.getText().toString();
                        s = s.substring(0, minTextLength);
                        input_text.setText(s + "?");
                        givenAnswer = "";

                        break;
                        //hints off
                    }else if (attemptCount <= 0){
                        attemptCount = MathBrain.userAtempts;
                        createToast("Wrong");
                        setQuestionAfterInterval();
                        givenAnswer = "";
                        questionCount++;
                        break;
                    }
                }
            }
        }
        if (questionCount == 10){
            clearGame();
            displayMarks();

        }
        return ok;
    }

    //Calculate the answer
    public int getAnswer(String expression){
        Calculator cal = new Calculator();
        return (cal.calculatorEquation(expression));

    }

    //Check answer and display correct or wrong warning messages according to the answer
    public boolean checkAnswer(int answer){
        boolean isCorrect = false;
        int remainingTime = Integer.parseInt(timerText.getText().toString());
        if(isRightAnswer(answer)){
            isCorrect = true;
            if (remainingTime < 10){
                totalMarks = totalMarks + (100/(10 - remainingTime));
            }else
                totalMarks = totalMarks + (100);

            createToast("Correct");
            givenAnswer = "";
        }else{
            isCorrect = false;

        }
        return isCorrect;
    }

    //Check whether the answer is right or wrong
    public boolean isRightAnswer(int answer){
        if (answer == correctAnswer){
            return true;
        }else
            return false;
    }

    //To create special Toasts
    public void createToast(String text){
        boolean isLong = false;
        LayoutInflater inflater = getLayoutInflater();
        View layout = null;

        if (text.equals("Correct")){
            layout = inflater.inflate(R.layout.custom_toast_correct,(ViewGroup) findViewById(R.id.toast_layout_root_correct));
            TextView textCorrect = (TextView) layout.findViewById(R.id.textCorrect);
            textCorrect.setText(text);
            textCorrect.setTextSize(20);
        }else if (text.equals("Wrong")){
            layout = inflater.inflate(R.layout.custom_toast_wrong,(ViewGroup) findViewById(R.id.toast_layout_root_wrong));
            TextView textWrong = (TextView) layout.findViewById(R.id.textWrong);
            textWrong.setText(text);
            textWrong.setTextSize(20);
        }else{
            layout = inflater.inflate(R.layout.custom_toast_hints,(ViewGroup) findViewById(R.id.toast_layout_root_hints));
            TextView textMarks = (TextView) layout.findViewById(R.id.textHints);
            textMarks.setText(text);
            textMarks.setTextSize(17);
            isLong = true;
        }

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (!isLong){
            toast.setDuration(Toast.LENGTH_SHORT);
        }else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setView(layout);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }}, 1000);
    }

    //To create normal Toasts
    @SuppressLint("ShowToast")
    public void createNormalToast(String text, int duration){
        Context context = getApplicationContext();
        Toast toast= Toast.makeText(context,text,Toast.LENGTH_SHORT);
//        Toast toast = Toast.makeText(context, text,0);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    //Set question after displaying warning message
    public void setQuestionAfterInterval(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setQuestion();
            }
        }, 2000);
    }

    //Start timer
    public void startTimer(){
        timer.start();
    }

    //Stop timer
    public void stopTimer(){
        timer.cancel();
    }

    //Set the timer
    public void setTimer(){
        timer = new CountDownTimer(11000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerText = (TextView)findViewById(R.id.timer);
                timerText.setText(""+millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                createNormalToast("Time Out", 0);
                setQuestion();
                givenAnswer = "";
                questionCount = questionCount + 1;
                if (questionCount == 10){
                    timer.cancel();
                    clearGame();
                    displayMarks();
                }
            }
        };
    }

    //To calculate ad display final marks
    public void displayMarks(){
        int avgeMarks;
        avgeMarks = (totalMarks/10);
        timer.cancel();
        saveGame();
        ContextThemeWrapper cw = new ContextThemeWrapper( this, R.style.AlertDialogTheme );
        new AlertDialog.Builder(cw)
                .setTitle("Math Fun")
                .setMessage("Your Final Marks        " + avgeMarks + "%")
                .setCancelable(false)
                .setIcon(R.drawable.game_icon)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        navigater();
                    }
                }).show();
    }

    //To show save dialog to save the game
    public void showSaveDialog(){
        ContextThemeWrapper cw = new ContextThemeWrapper( this, R.style.AlertDialogTheme );
        new AlertDialog.Builder(cw)
                .setTitle("Save Game")
                .setMessage("Do you want to save the game?")
                .setCancelable(false)
                .setIcon(R.drawable.game_icon)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveGame();
                        navigater();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                navigater();
            }
        }).show();
    }

    //Navigate to the main form
    public void navigater(){
        Intent i = new Intent(getApplicationContext(), MathBrain.class);
        startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //To save the game details
    public void saveGame(){
        String data = "game_type="+ gameType +"\nquestionNum="+ questionCount + "\nresult=" + totalMarks +"\nattempts="+ attemptCount;
        try {
            Log.d("myResults", "Save game type ="+ gameType +" questionNum ="+ questionCount +" result = "+ totalMarks + " attempts="+ attemptCount);
            FileOutputStream outputStream = openFileOutput("Game.properties", Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    //clear the game
    public void clearGame(){
        timerText.setText("");
        input_text.setText("");
        stopTimer();
    }

    //when back button click
    @Override
    public void onBackPressed(){
        if (questionCount > 0){
            showSaveDialog();
        }else
            navigater();

    }
}
