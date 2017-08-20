package i.subodh.sgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SgameActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ANDRO-S";
    private static final int[] idBtn = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8 };

    Button [] btnX = new Button[9];
    private int [] cntrArr = new int[9];
    private int ClickCount = 0;
    Button btnReset;
    Button btnHelp;
    private TextView txtClickCount;

    /*
    Set the Control Array Randomly
     */
    void setCntrArr(){
        Log.d(TAG,"Resetting the control array");
        //Set all to -1
        for (int i =0; i < cntrArr.length; i++)
            cntrArr[i] = -1;
        //Set randomly
        Random random = new Random();
        for (int i=0 ; i < cntrArr.length;i++){
            int rand = random.nextInt(9);

            while (cntrArr[rand] != -1 ){
                rand = rand +1;
                if (rand >= cntrArr.length){
                    rand = 0;
                }
            }
            cntrArr[rand] = i;
        }
        clickCounter("RESET");
    }
    /*
    Verify the end game
     */
    boolean isGameOver(){
        for (int i=0; i<cntrArr.length; i++){
            if (cntrArr[i] != i)
                return false;
        }
        Toast.makeText(this, "Wow ! U did it...", Toast.LENGTH_SHORT).show();
        return true;
    }
    /*
    Display the button label numbers as per the Control Array
     */
    void displayCntrArr(){
        Log.d(TAG, "Setting up Display of Buttons");
        for (int i=0; i < cntrArr.length; i++){
            btnX[i].setText(""+ (cntrArr[i] + 1));
            if (cntrArr[i]   == 8 )
                btnX[i].setVisibility(View.INVISIBLE);
            else
                btnX[i].setVisibility(View.VISIBLE);
        }
    }
    /*
    Handle the game clicks
     */
    void userClicks(int keyCode){
        Log.d(TAG, "Handing User click");

        // Check the right side space
        if (keyCode % 3 !=2 )
            if(cntrArr[keyCode+1] == 8 ){
                cntrArr[keyCode+1] = cntrArr[keyCode];
                cntrArr[keyCode] = 8;
                clickCounter("INC");
            }
        //Check the Left side space
        if (keyCode % 3 != 0)
            if(cntrArr[keyCode-1] == 8){
                cntrArr[keyCode-1] = cntrArr[keyCode];
                cntrArr[keyCode] = 8;
                clickCounter("INC");
            }
        //Check the bottom side
        if (keyCode < 6 )
            if (cntrArr[keyCode + 3] == 8) {
                cntrArr[keyCode + 3] = cntrArr[keyCode];
                cntrArr[keyCode] = 8;
                clickCounter("INC");
            }
        //Check the upper side
        if (keyCode > 2 )
            if (cntrArr[keyCode - 3] == 8) {
                cntrArr[keyCode - 3] = cntrArr[keyCode];
                cntrArr[keyCode] = 8;
                clickCounter("INC");
            }
        //redisplay the game_board
        displayCntrArr();
    }
    /*
    Set/reset and read the Count Cliks as needed
     */
    int clickCounter(String operation){
        Log.d(TAG, "In the clickCounter function");
        switch (operation){
            case "INC":
                ClickCount++;
                break;
            case "RESET":
                ClickCount = 0;
                break;
        }

        // Now is time to update the counter display
        Log.d(TAG, "Setting up Display of Click counter");
        txtClickCount.setText("Click Count : "+ ClickCount);
        //Always return the current count ?
        return ClickCount;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting in onCreate");
        setContentView(R.layout.activity_sgame);


        Log.d(TAG, "Starting to setup button array");
        for (int i=0; i < idBtn.length; i++){
            btnX[i] = (Button) findViewById(idBtn[i]);
            btnX[i].setOnClickListener(this);
        }
        Log.d(TAG, "done initializing button array");

        Toast.makeText(this, "Welcome to S-Game", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Initial Button set up");

        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);
        txtClickCount = (TextView) findViewById(R.id.txtClickCount);

        setCntrArr();
        displayCntrArr();


    }
    @Override
    public void onClick(View v){
        Log.d(TAG, "Inside the On click hander");
        for (int i =0 ; i<idBtn.length ; i++){
            if (v.getId() == idBtn[i]){
                userClicks(i);
            }
        }
        if (isGameOver()){
            //TODO Display the Winner mesage
            setCntrArr();
            displayCntrArr();
        }

        switch(v.getId()){
             case R.id.btnReset:
                 //TODO call Dailog and handle it
                 Toast.makeText(this, "You choose to Reset S-Game", Toast.LENGTH_SHORT).show();
                 Log.d(TAG, "Setting up a new S-Game");
                 setCntrArr();
                 displayCntrArr();
                 break;
            case R.id.btnHelp:
                //TODO display the Game instructions
                Toast.makeText(this, "Sorry No Help available", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Help Button Clicked");
        }
    }
}
