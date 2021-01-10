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
import com.google.firebase.database.core.Path;
import com.mobile.tictactoe.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Tableau a deux dimensions
    //plateau[colonne][ligne]
    // 0 : case vide
    // 1 : X
    // 2 : O
    private int plateau[][] = new int[3][3];
    private int a;
    // 1 : X
    // 2 : O
    private int joueurEnCours = 1;
    private int joueur;
    private int NbJoueur;
    private TextView tvJoueur;
    private ArrayList<Button> all_buttons = new ArrayList<>();
    private ArrayList<String> firebase_buttons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefNbJoueur = database.getReference("NbJoueur");
        DatabaseReference myRefjoueurEnCours = database.getReference("joueurEnCours");


        myRefNbJoueur.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(int.class);
                    joueur = value + 1;
                    NbJoueur =value +1;
                    myRefNbJoueur.setValue(joueur);
                    if (joueur > 2) {
                        displayAlertDialog(4);

                }
                Log.d("APPX", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        myRefjoueurEnCours.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(int.class);
                joueurEnCours=value;
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
        firebase_buttons.add("1");
        firebase_buttons.add("2");
        firebase_buttons.add("3");
        firebase_buttons.add("4");
        firebase_buttons.add("5");
        firebase_buttons.add("6");
        firebase_buttons.add("7");
        firebase_buttons.add("8");
        firebase_buttons.add("9");
        for (String bt : firebase_buttons){
            DatabaseReference myReFirebase = database.getReference(bt);
            myReFirebase.setValue(2);
        }
        for (Button bt : all_buttons) {
            bt.setBackground(null);
            bt.setOnClickListener(this);
        }
        Drawable drawableFirebaseX=ContextCompat.getDrawable(this, R.drawable.x);
        Drawable drawableFirebaseO=ContextCompat.getDrawable(this, R.drawable.o);
        for (String bt : firebase_buttons) {
            DatabaseReference myRefirebase = database.getReference(bt);
            myRefirebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    int value = dataSnapshot.getValue(int.class);
                    String path= dataSnapshot.getKey();
                    if(value==0){
                        all_buttons.get(parseInt(path)-1).setBackground(drawableFirebaseX);
                    }
                    if(value==1){
                        all_buttons.get(parseInt(path)-1).setBackground(drawableFirebaseO);
                    }
                        /*if (value == 0 & all_buttons.get(parseInt(dataSnapshot.getKey())).getBackground() == null ) {
                            all_buttons.get(parseInt(dataSnapshot.getKey())).setBackground(drawableFirebaseX);
                        }
                        if (value == 1 & all_buttons.get(parseInt(dataSnapshot.getKey())).getBackground() == null) {
                            all_buttons.get(parseInt(dataSnapshot.getKey())).setBackground(drawableFirebaseO);
                        }*/

                    Log.d("APPX", "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("APPX", "Failed to read value.", error.toException());
                }
            });
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefNbJoueur = database.getReference("NbJoueur");
        myRefNbJoueur.setValue(NbJoueur-1);
    }
    @Override
    public void onClick(View view) {

        //On ne fait rien si la case cliqué n'est pas vide
        if (view.getBackground() != null |joueurEnCours!=joueur)
            return;
        //Gestion du plateau
        switch (view.getId()) {
            case R.id.bt1:     // if(view.getId() == R.id.bt1)
                plateau[0][0] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1");
                    myRef.setValue(0);

                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("1");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt2:
                plateau[1][0] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("2");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt3:
                plateau[2][0] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("3");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt4:
                plateau[0][1] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("4");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("4");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt5:
                plateau[1][1] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("5");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("5");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt6:
                plateau[2][1] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("6");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("6");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt7:
                plateau[0][2] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("7");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("7");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt8:
                plateau[1][2] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("8");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("8");
                    myRef.setValue(1);
                }
                break;
            case R.id.bt9:
                plateau[2][2] = joueurEnCours;
                if (joueurEnCours == 1) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("9");
                    myRef.setValue(0);
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("9");
                    myRef.setValue(1);
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
        if (joueurEnCours == 1) {
            joueurEnCours = 2;
            tvJoueur.setText("X");
        } else {
            joueurEnCours = 1;
            tvJoueur.setText("O");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("joueurEnCours");
        myRef.setValue(joueurEnCours);

        int res = checkWinner();
        displayAlertDialog(res);

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