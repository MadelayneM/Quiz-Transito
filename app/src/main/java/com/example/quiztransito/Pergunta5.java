package com.example.quiztransito;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pergunta5 extends AppCompatActivity {
    private int acertosAtuais;
    private Button btnProxima;
    private RadioGroup radioGroupAlternativas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pergunta5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        acertosAtuais = getIntent().getIntExtra("TOTAL_ACERTOS", 0);
        boolean respostaAnteriorCorreta = getIntent().getBooleanExtra("RESPOSTA_ANTERIOR_CORRETA", false);

        if (respostaAnteriorCorreta) {
            Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Resposta Incorreta!", Toast.LENGTH_SHORT).show();
        }

        radioGroupAlternativas = findViewById(R.id.radioGroupAlternativas);
        btnProxima = findViewById(R.id.btnProxima);

        radioGroupAlternativas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Se checkedId for diferente de -1, significa que alguma opção foi marcada
                if (checkedId != -1) {
                    btnProxima.setEnabled(true);
                    // Alteração de ciza para verde
                    btnProxima.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.rgb(76, 175, 80)));
                } else {
                    btnProxima.setEnabled(false);
                }
            }
        });

        btnProxima.setOnClickListener(v -> {
            boolean acertou = Alternativas.isRespostaCorreta(radioGroupAlternativas, R.id.radioD);
            acertosAtuais = Alternativas.calcularPontuacao(acertou, acertosAtuais);

            // Avança enviando a pontuação atualizada
            Intent intent = new Intent(Pergunta5.this, Ranking.class);
            intent.putExtra("TOTAL_ACERTOS", acertosAtuais);
            intent.putExtra("RESPOSTA_ANTERIOR_CORRETA", acertou);

            startActivity(intent);
        });
    }
}

