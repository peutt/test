package com.mobile.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.tictactoe.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Tableau a deux dimensions
    //plateau[colonne][ligne]
    // 0 : case vide
    // 1 : X
    // 2 : O
    private int plateau[][] = new int[3][3];
    private int a=0;
    private int i=0;
    private int b=0;

    // 1 : X
    // 2 : O

    private int joueurEnCours = 1;
    private int joueur = 1;
    private TextView tvJoueur;
    private ArrayList<String> firebase_buttons = new ArrayList<>();



    private ArrayList<Button> all_buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("nbPlayer");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value=="0" & a==0){
                    myRef.setValue("1");
                    joueur = 1;
                    a=1;
                }
                if(value=="1" & a==0){
                    myRef.setValue("2");
                    joueur = 2;
                    a=1;
                }
                if(value=="2" & a==0){
                    displayAlertDialog(4);
                    a=1;
                }

                Log.d("APPX", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        tvJoueur = (TextView) findViewById(R.id.joueur);

        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
        Button bt3 = (Button) findViewById(R.id.bt3);
        Button bt4 = (Button) findViewById(R.id.bt4);
        Button bt5 = (Button) findViewById(R.id.bt5);
        Button bt6 = (Button) findViewById(R.id.bt6);
        Button bt7 = (Button) findViewById(R.id.bt7);
        Button bt8 = (Button) findViewById(R.id.bt8);
        Button bt9 = (Button) findViewById(R.id.bt9);

        all_buttons.add(bt1);
        all_buttons.add(bt2);
        all_buttons.add(bt3);
        all_buttons.add(bt4);
        all_buttons.add(bt5);
        all_buttons.add(bt6);
        all_buttons.add(bt7);
        all_buttons.add(bt8);
        all_buttons.add(bt9);

        firebase_buttons.add("1,1");
        firebase_buttons.add("1,2");
        firebase_buttons.add("1,3");
        firebase_buttons.add("2,1");
        firebase_buttons.add("2,2");
        firebase_buttons.add("2,3");
        firebase_buttons.add("3,1");
        firebase_buttons.add("3,2");
        firebase_buttons.add("3,3");

        for (Button bt : all_buttons) {
            bt.setBackground(null);
            bt.setOnClickListener(this);
        }
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("nbPlayer");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value=="0" & a==1){
                    displayAlertDialog(5);
                    a=2;
                }
                if(value=="1" & a==1){
                    myRef.setValue("0");
                    joueur = 2;
                    a=2;
                }
                if(value=="2" & a==1){
                    myRef.setValue("1");
                    a=2;
                }

                Log.d("APPX", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });


    }
    @Override
    public void onClick(View view) {

        //On ne fait rien si la case cliqué n'est pas vide
        if (view.getBackground() != null | joueurEnCours != joueur )
            return;

        //Gestion du plateau
        switch (view.getId()) {
            case R.id.bt1:     // if(view.getId() == R.id.bt1)
                plateau[0][0] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1,1");
                    myRef.setValue("x");

                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1,1");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt2:
                plateau[1][0] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1,2");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1,2");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt3:
                plateau[2][0] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1,3");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1,3");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt4:
                plateau[0][1] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2,1");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2,1");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt5:
                plateau[1][1] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2,2");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2,2");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt6:
                plateau[2][1] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2,3");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2,3");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt7:
                plateau[0][2] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3,1");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3,1");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt8:
                plateau[1][2] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3,2");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3,2");
                    myRef.setValue("o");
                }
                break;
            case R.id.bt9:
                plateau[2][2] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3,3");
                    myRef.setValue("x");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3,3");
                    myRef.setValue("o");
                }
                break;
            default:
                return;
        }

        //Affiche le pion
        Drawable drawableJoueur;
        if (joueurEnCours == 1)
            drawableJoueur = ContextCompat.getDrawable(this, R.drawable.x);

        else
            drawableJoueur = ContextCompat.getDrawable(this, R.drawable.o);
        view.setBackground(drawableJoueur);
        Drawable drawableFirebaseX;
        drawableFirebaseX = ContextCompat.getDrawable(this, R.drawable.x);
        Drawable drawableFirebaseO;
        drawableFirebaseO = ContextCompat.getDrawable(this, R.drawable.o);

        //Changement de joueur
        if (joueurEnCours == 1) {
            joueurEnCours = 2;
            tvJoueur.setText("O");
        } else {
            joueurEnCours = 1;
            tvJoueur.setText("X");
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("joueurEnCours");
        myRef.setValue(String.valueOf(joueurEnCours));

        int res = checkWinner();
        displayAlertDialog(res);
        while(joueurEnCours != joueur){
            b=3;
            DatabaseReference myRefJoueurEnCours = database.getReference("JoueurEnCours");

            myRefJoueurEnCours.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    if(b==3) {
                        joueurEnCours = Integer.parseInt(value);
                    }
                    Log.d("APPX", "Value is: " + value);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("APPX", "Failed to read value.", error.toException());
                }
            });
            b=0;
            i=0;
            for (String bt : firebase_buttons) {
                DatabaseReference myRefirebase = database.getReference(bt);

                myRefirebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        if(value=="x"){
                            all_buttons.get(i).setBackground(drawableFirebaseX);
                        }
                        if(value=="o"){
                            all_buttons.get(i).setBackground(drawableFirebaseO);
                        }
                        Log.d("APPX", "Value is: " + value);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("APPX", "Failed to read value.", error.toException());
                    }
                });
                i++;
            }
        }
    }

    // 0 : partie non fini
    // 1 : X
    // 2 : O
    // 3 : egalite
    private int checkWinner(){

        // on regarde si il y a un gagnant sur les colonnes
        for (int col = 0; col <= 2; col++) {
            if (plateau[col][0] != 0 && plateau[col][0] == plateau[col][1] && plateau[col][0] == plateau[col][2])
                return plateau[col][0];
        }

        // on regarde si il y a un gagnant sur les lignes
        for (int line = 0; line <= 2; line++){
            if (plateau[0][line] != 0 && plateau[0][line] == plateau[1][line] && plateau[0][line] == plateau[2][line])
                return plateau[0][line];
        }

        // on regarde si il y a un gagnant sur la diagonale haut/gauche -> bas/droit
        if (plateau[0][0] != 0 && plateau[0][0] == plateau[1][1] && plateau[0][0] == plateau[2][2])
            return plateau[0][0];

        // on regarde si il y a un gagnant sur la diagonale haut/droite -> bas/gauche
        if (plateau[2][0] != 0 && plateau[2][0] == plateau[1][1] && plateau[2][0] == plateau[0][2])
            return plateau[2][0];

        // Egalité
        boolean isPlateauPlein = true;
        for (int col = 0; col <= 2; col++) {
            for (int line = 0; line <= 2; line++){
                if (plateau[col][line] == 0) { // case
                    isPlateauPlein = false;
                    break;
                }
            }
            if (!isPlateauPlein)
                break;
        }
        if (isPlateauPlein)
            return 3;

        // Partie non fini
        return 0;
    }

    // 0 : partie non fini
    // 1 : X
    // 2 : O
    // 3 : egalite
    private void displayAlertDialog(int res){
        if (res == 0) // partie non termine
            return;

        String strToDisplay = "";
        if (res == 1)
            strToDisplay = "Les X ont gagnées !";
        if (res == 2)
            strToDisplay = "Les O ont gagnés !";
        if (res == 3)
            strToDisplay = "Egalité !";
        if (res == 4)
            strToDisplay = "Il y a trop de joueur conecté veuillez vous reconecter plus tard";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Fin de la partie");
        alertDialog.setMessage(strToDisplay);

        alertDialog.setNeutralButton("Recommencer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetGame();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private void resetGame(){

        for (int col = 0; col <= 2; col++) {
            for (int line = 0; line <= 2; line++) {
                plateau[col][line] = 0;
            }
        }

        for (Button bt : all_buttons) {
            bt.setBackground(null);
        }
    }
}