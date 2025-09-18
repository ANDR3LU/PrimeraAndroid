package com.example.eva;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout gameLayout;
    private Button btnTarget;
    private TextView tvInfo;

    private Handler handler = new Handler();
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = findViewById(R.id.gameLayout);
        btnTarget = findViewById(R.id.btnTarget);
        tvInfo = findViewById(R.id.tvInfo);

        // Al iniciar, mostrar botón tras un pequeño retraso
        iniciarRonda();

        btnTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long reactionTime = System.currentTimeMillis() - startTime;
                tvInfo.setText("¡Bien! Tiempo de reacción: " + reactionTime + " ms");
                btnTarget.setVisibility(View.GONE);

                // Esperar 2 segundos y empezar otra ronda
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iniciarRonda();
                    }
                }, 2000);
            }
        });
    }

    private void iniciarRonda() {
        tvInfo.setText("Prepárate...");
        int delay = new Random().nextInt(2000) + 1000; // entre 1 y 3 seg

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moverBotonAleatorio();
                btnTarget.setVisibility(View.VISIBLE);
                tvInfo.setText("¡Toca el botón!");
                startTime = System.currentTimeMillis();
            }
        }, delay);
    }

    private void moverBotonAleatorio() {
        int layoutWidth = gameLayout.getWidth();
        int layoutHeight = gameLayout.getHeight();

        if (layoutWidth == 0 || layoutHeight == 0) return; // evitar errores en primera carga

        Random random = new Random();
        int x = random.nextInt(layoutWidth - btnTarget.getWidth());
        int y = random.nextInt(layoutHeight - btnTarget.getHeight());

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btnTarget.getLayoutParams();
        params.leftMargin = x;
        params.topMargin = y;
        btnTarget.setLayoutParams(params);
    }
}