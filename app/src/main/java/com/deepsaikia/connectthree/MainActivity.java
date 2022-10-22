package com.deepsaikia.connectthree;


import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    //red 0 yellow 1 empty 2
    private static final String TAG = "TasksSample";
    boolean counter = true;
    int[][] board = new int[3][3];
    boolean gameOverCheck = false;
    Button button;
    boolean buttonUsed=false;
    GridLayout gridLayout;
    TextView textViewRed;
    TextView textViewYellow;
    int redCounter=0;
    int yellowCounter=0;
    public boolean checkDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] ==2)
                    return false;
        return true;
    }

    public boolean gameOver() {
        //checking the win for Simple Rows and Simple Column

        for (int i = 0; i < 3; i++)
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][2]!=2 || board[0][i] == board[1][i] && board[0][i] == board[2][i]&& board[2][i]!=2)
                return true;

        //checking the win for both diagonal

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]&& board[2][2]!=2 || board[0][2] == board[1][1] && board[0][2] == board[2][0]&& board[0][2]!=2)
            return true;

        return false;
    }

    public void clicked(View view) {

        ImageView imageView = (ImageView) view;
        String tagValue=imageView.getTag().toString();
        int valueOne=Integer.parseInt(tagValue)/10;
        int valueTwo=Integer.parseInt(tagValue)%10;
        Log.i(TAG, tagValue);
        Log.i(TAG, Integer.toString(valueOne)+Integer.toString(valueTwo));
        if(board[valueOne][valueTwo]!=2 || gameOverCheck)
        {
            return;
        }
        if (counter) {
            counter = false;
            imageView.setImageResource(R.drawable.red);
            imageView.animate().alpha(1).setDuration(500);
            board[valueOne][valueTwo]=0;

        } else {
            imageView.setImageResource(R.drawable.yellow);
            imageView.animate().alpha(1).setDuration(500);
            counter = true;
            board[valueOne][valueTwo]=1;
        }
        if(gameOver())
        {
            String decide="";
            if(counter)
            {
                yellowCounter++;
                textViewYellow.setText("YELLOW : "+Integer.toString(yellowCounter));
                decide+="YELLOW";
            }
            else
            {
                redCounter++;
                textViewRed.setText("RED : "+Integer.toString(redCounter));
                decide+="RED";
            }
            decide+=" WON";
            Toast.makeText(this, decide, Toast.LENGTH_SHORT).show();
            gameOverCheck=true;
        }
        if(checkDraw())
        {
            Toast.makeText(this, "DRAW!!", Toast.LENGTH_SHORT).show();
            gameOverCheck=true;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        button=findViewById(R.id.button);
        gridLayout=findViewById(R.id.gridLayout);
        textViewRed=findViewById(R.id.textViewRED);
        textViewYellow=findViewById(R.id.textViewYELLOW);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!buttonUsed)
                {
                    textViewRed.setText("RED : 0");
                    textViewYellow.setText("YELLOW : 0");
                    buttonUsed=true;
                    button.setText("RESTART GAME");


                }
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {
                        board[i][j] = 2;
                        Log.i(TAG, Integer.toString(board[i][j])+"qe");

                    }
                }
                int count = gridLayout.getChildCount();
                Log.i(TAG, Integer.toString(count));
                for(int i = 0 ; i <count ; i++){
                    View child = gridLayout.getChildAt(i);
                    child.animate().alpha(0).setDuration(500);
                }
                gameOverCheck=false;

            }
        });


    }
}