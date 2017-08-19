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
    int [] cntrArr = new int[9];
    Button btnReset;
    Button btnHelp;
    TextView txtClickCount;

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

        setCntrArr();
        displayCntrArr();
        Log.d(TAG, "Initial Button set up");

        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        btnHelp = (Button) findViewById(R.id.Help);
        btnHelp.setOnClickListener(this);
        txtClickCount = (TextView) findViewById(R.id.txtClickCount);

    }
    @Override
    public void onClick(View v){
        Log.d(TAG, "Inside the On click hander");
        switch(v.getId()){
            case R.id.btn0:
                // Add function that handles

            case R.id.btnReset:
                //TODO call Dailog and handle it
                setCntrArr();
                displayCntrArr();
        }
    }
}
