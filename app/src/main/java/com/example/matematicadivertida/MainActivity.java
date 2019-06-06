package com.example.matematicadivertida;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public RadioGroup answer;
    public RadioButton answerSelected;
    public TextView txtDrawnNumber;
    public ImageView imageView;
    public int min = 1;
    public int max = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random r = new Random();
        int drawnNumber = r.nextInt(max - min + 1) + min;

        txtDrawnNumber = findViewById(R.id.txtDrawnNumber);
        answer = findViewById(R.id.radioGroupAnswer);
        txtDrawnNumber.setText(String.valueOf(drawnNumber));
        //imageView = findViewById(R.id.imageView);
        //imageView.setImageResource(R.drawable.bola);
        TableLayout layout = (TableLayout) findViewById(R.id.tablelayout);

        layout.setStretchAllColumns(true);
        layout.bringToFront();
        TableRow tr =  new TableRow(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(30, 30);
        for(int i = 0; i < drawnNumber; i++){
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.bola);;

            iv.setLayoutParams(layoutParams);
            tr.addView(iv);
        }
        layout.addView(tr);

    }

    public void checkAnswer(View view) {

        answerSelected = findViewById(answer.getCheckedRadioButtonId());
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Resultado");
        if (answerSelected.getText().equals(txtDrawnNumber.getText())) {
            alertDialog.setMessage("Resposta certa!");
        } else {
            alertDialog.setMessage("Resposta errada!");
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
