package i.subodh.sgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class fourXfour extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ANDRO-SGAME-4x4";
    private static final int[] idBtn = {R.id.btx40, R.id.btx41, R.id.btx42, R.id.btx43, R.id.btx44,
            R.id.btx45, R.id.btx46, R.id.btx47, R.id.btx48, R.id.btx49, R.id.btx410,R.id.btx411,
            R.id.btx412,R.id.btx413,R.id.btx414,R.id.btx415  };

    Button[] btnX = new Button[16];
    private int [] cntrArr = new int[16];
    private int ClickCount = 0;
    Button btx4Reset;
    Button btx4Help;
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
            int rand = random.nextInt(cntrArr.length);

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
            if (cntrArr[i]   == cntrArr.length -1  )
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
        if (keyCode % 4 != 3 )
            if(cntrArr[keyCode+1] == cntrArr.length-1 ){
                cntrArr[keyCode+1] = cntrArr[keyCode];
                cntrArr[keyCode] = cntrArr.length-1;
                clickCounter("INC");
            }
        //Check the Left side space
        if (keyCode % 4 != 0)
            if(cntrArr[keyCode-1] == cntrArr.length-1){
                cntrArr[keyCode-1] = cntrArr[keyCode];
                cntrArr[keyCode] = cntrArr.length-1;
                clickCounter("INC");
            }
        //Check the bottom side
        if (keyCode < cntrArr.length - 4 )
            if (cntrArr[keyCode + 4] == cntrArr.length -1) {
                cntrArr[keyCode + 4] = cntrArr[keyCode];
                cntrArr[keyCode] = cntrArr.length-1;
                clickCounter("INC");
            }
        //Check the upper side
        if (keyCode > 3 )
            if (cntrArr[keyCode - 4] == cntrArr.length-1) {
                cntrArr[keyCode - 4] = cntrArr[keyCode];
                cntrArr[keyCode] = cntrArr.length - 1;
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
        setContentView(R.layout.activity_fourxfour);

        Log.d(TAG, "Starting to setup button array");
        for (int i=0; i < idBtn.length; i++){
            btnX[i] = (Button) findViewById(idBtn[i]);
            btnX[i].setOnClickListener(this);
        }
        Log.d(TAG, "done initializing button array");

        Toast.makeText(this, "Welcome to S-Game", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Initial Button set up");

        btx4Reset = (Button) findViewById(R.id.btx4Reset);
        btx4Reset.setOnClickListener(this);
        btx4Help = (Button) findViewById(R.id.btx4Help);
        btx4Help.setOnClickListener(this);
        txtClickCount = (TextView) findViewById(R.id.txtClickCount);

        setCntrArr();
        displayCntrArr();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Inside the On click hander");
        for (int i = 0; i < idBtn.length; i++) {
            if (v.getId() == idBtn[i]) {
                userClicks(i);
            }
        }
        if (isGameOver()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(fourXfour.this);
            dialog.setCancelable(false);
            dialog.setTitle("SGame! Win");
            dialog.setMessage("WoW ! You did it in " + ClickCount + ", lets play again... ");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    setCntrArr();
                    displayCntrArr();
                }
            });

            final AlertDialog alert = dialog.create();
            alert.show();

        }
        switch(v.getId()){
            case R.id.btx4Reset:
                Toast.makeText(this, "You choose to Reset S-Game", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Setting up a new S-Game");

                AlertDialog.Builder dialog = new AlertDialog.Builder(fourXfour.this);
                dialog.setCancelable(false);
                dialog.setTitle("SGame! Reset");
                dialog.setMessage("Reset ! Are you quitting game... " );
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setCntrArr();
                        displayCntrArr();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(fourXfour.this,"Good...you can finish the current game",Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog alert = dialog.create();
                alert.show();
                break;
            case R.id.btx4Help:
                //TODO display the Game instructions
                Toast.makeText(this, "Please call Subodh for help ;-)", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Help Button Clicked");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_4x4, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem){
        Log.d(TAG, "Inside the menu items selection.");

        switch(menuitem.getItemId()) {
            case R.id.menu3x3:
                Intent intent3x3 = new Intent(this, SgameActivity.class);
                startActivity(intent3x3);
                break;
            case R.id.menu4x4:
                Intent intent4x4  = new Intent(this, fourXfour.class);
                startActivity(intent4x4);
                break;
        }
        return true;
    }

}
