package com.nty.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView player1Score, player2Score, playerStatus;
    private Button[] buttons   = new Button[9];
    private Button resetGame;

    private int player1ScoreCount, player2ScoreCount, rountCount;
    boolean activePlayer;
    private AdView mAdView;


    int [] gameState= {2, 2,2,2,2,2,2,2,2};
    int [] [] winningPosition = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,4,8}, {2,4,6},
            {0,3,6}, {1,4,7}, {2,5,8}

     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1Score = findViewById(R.id.player1Score);
        player2Score = findViewById(R.id.player2Score);
        playerStatus = findViewById(R.id.playerStatus);
        resetGame = findViewById(R.id.restart);



        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });




        for (int i=0; i < buttons.length;  i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rountCount = 0;
        player1ScoreCount = 0;
        player2ScoreCount = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));
        if (activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }
            else{
                ((Button) v).setText("O");
                ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
                gameState[gameStatePointer] = 1;

            }
        rountCount++;
            if(checkWinner()){
                if(activePlayer){
                    player1ScoreCount++;
                    updatePlayerScore();
                    Toast.makeText(this, R.string.pl1winround, Toast.LENGTH_LONG).show();
                    playAgain();

                }else{
                    player2ScoreCount++;
                    updatePlayerScore();
                    Toast.makeText(this, R.string.pl2winround, Toast.LENGTH_LONG).show();
                    playAgain();

                }
            }
            else if(rountCount == 9) {
            playAgain();
                Toast.makeText(this, R.string.nowinround, Toast.LENGTH_LONG).show();
            }
            else {
                activePlayer = !activePlayer;
            }
            if (player1ScoreCount > player2ScoreCount){
                playerStatus.setText(R.string.pl1wing);
            }
            else if (player2ScoreCount > player1ScoreCount){
                playerStatus.setText(R.string.pl2wing);
            }
            else {
                playerStatus.setText("");
            }
            resetGame.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    playAgain();
                    player1ScoreCount = 0;
                    player2ScoreCount = 0;
                    playerStatus.setText("");
                    updatePlayerScore();
                }
        });
        }
        public boolean checkWinner() {
        boolean winnnerResult = false;
         for(int [] winningPosition: winningPosition){
             if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                     gameState[winningPosition[1]] ==gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){
                 winnnerResult = true;
             }

         }
         return winnnerResult;
        }
        public void updatePlayerScore(){
        player1Score.setText(Integer.toString(player1ScoreCount));
        player2Score.setText(Integer.toString(player2ScoreCount));

        }
        public void playAgain(){
        rountCount = 0;
        activePlayer = true;
        for (int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }

        }
    }
