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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public RadioGroup answer;
    public RadioButton answerSelected;
    public TextView txtDrawnNumber;
    public int drawnNumber;
    public int rightAnswerPosition;
    public double wins = 0;
    public double losses = 0;
    public double runs = 0;
    public double finalScore = 0;
    TableLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame();
    }

    public void startGame() {
        // se já foram respondidas 5 rodadas, então mostra a nota final
        if (runs == 5) {
            finalScore = (wins/runs)*100;
            int finalScoreInt = (int)finalScore;
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Fim de jogo");
            alertDialog.setMessage("Sua nota foi " + finalScoreInt + "!");

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else {
            newGame();
        }
    }

    public void newGame() {
        // aumenta o contador de perguntas (jogadas)
        runs++;

        // mapeia os componentes da tela
        answer = findViewById(R.id.radioGroupAnswer);
        RadioButton radioA = findViewById(R.id.radioAnswer1);
        RadioButton radioB = findViewById(R.id.radioAnswer2);
        RadioButton radioC = findViewById(R.id.radioAnswer3);
        txtDrawnNumber = findViewById(R.id.txtDrawnNumber);
        layout = (TableLayout) findViewById(R.id.tablelayout);

        // limpa valores da pergunta anterior
        layout.removeAllViews();
        answer.clearCheck();

        // sorteia a resposta
        drawnNumber = sortear(1, 10);
        txtDrawnNumber.setText(String.valueOf(drawnNumber));

        // com base no numero sorteado, exibe as figuras
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

        // sorteia qual radio tera a resposta certa, e sorteia os valores dos outros radios
        int aValue, bValue, cValue;
        rightAnswerPosition = sortear(1, 3);
        switch(rightAnswerPosition) {
            case 1:
                radioA.setText(String.valueOf(drawnNumber));
                bValue = sortear(1, 10);
                while (bValue == drawnNumber) {
                    bValue = sortear(1, 10);
                }
                radioB.setText(String.valueOf(bValue));

                cValue = sortear(1, 10);
                while (cValue == drawnNumber || cValue == bValue) {
                    cValue = sortear(1, 10);
                }
                radioC.setText(String.valueOf(cValue));
                break;
            case 2:
                radioB.setText(String.valueOf(drawnNumber));
                aValue = sortear(1, 10);
                while (aValue == drawnNumber) {
                    aValue = sortear(1, 10);
                }
                radioA.setText(String.valueOf(aValue));

                cValue = sortear(1, 10);
                while (cValue == drawnNumber || cValue == aValue) {
                    cValue = sortear(1, 10);
                }
                radioC.setText(String.valueOf(cValue));
                break;
            case 3:
                radioC.setText(String.valueOf(drawnNumber));
                aValue = sortear(1, 10);
                while (aValue == drawnNumber) {
                    aValue = sortear(1, 10);
                }
                radioA.setText(String.valueOf(aValue));

                bValue = sortear(1, 10);
                while (bValue == drawnNumber || bValue == aValue) {
                    bValue = sortear(1, 10);
                }
                radioB.setText(String.valueOf(bValue));
                break;
        }
    }

    public void checkAnswer(View view) {
        answerSelected = findViewById(answer.getCheckedRadioButtonId());
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Resultado");
        if (answerSelected.getText().equals(String.valueOf(drawnNumber))) {
            wins++;
            alertDialog.setMessage("Resposta certa!");

        } else {
            losses++;
            alertDialog.setMessage("Resposta errada! A resposta correta era " + drawnNumber);

        }
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startGame();
                    }
                });
        alertDialog.show();
    }

    public int sortear(int min, int max) {
        Random r = new Random();
        int result = r.nextInt(max - min + 1) + min;
        return result;
    }

}
