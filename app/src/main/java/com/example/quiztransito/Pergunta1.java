package com.example.quiztransito;

import static com.example.quiztransito.R.id.*;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pergunta1 extends AppCompatActivity {
    private int acertosAtuais = 0;
    private Button btnProxima;
    private RadioGroup radioGroupAlternativas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pergunta1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        radioGroupAlternativas =  findViewById(R.id.radioGroupAlternativas);
        btnProxima =  findViewById(R.id.btnProxima);


        radioGroupAlternativas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Se checkedId for diferente de -1, significa que alguma opção foi marcada
                if (checkedId != -1) {
                    // Alteração: deixa o botão verde após selecionar uma opção
                    btnProxima.setEnabled(true);
                    btnProxima.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.rgb(76, 175, 80)));
                } else {
                    btnProxima.setEnabled(false);
                }
            }
        });

        btnProxima.setOnClickListener(v -> {
            boolean acertou = Alternativas.isRespostaCorreta(radioGroupAlternativas, R.id.radioC);
            acertosAtuais = Alternativas.calcularPontuacao(acertou, acertosAtuais);

            Intent intent = new Intent(Pergunta1.this, Pergunta2.class);
            intent.putExtra("TOTAL_ACERTOS", acertosAtuais);
            intent.putExtra("RESPOSTA_ANTERIOR_CORRETA", acertou);

            startActivity(intent);
        });
    }
}