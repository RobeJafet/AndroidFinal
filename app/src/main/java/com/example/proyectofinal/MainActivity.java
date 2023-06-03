package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    RelativeLayout layout;
    PlanoDeDibujo Miplano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout);
        Miplano = new PlanoDeDibujo((this));
        Miplano.setOnTouchListener(this);
        layout.addView(Miplano);
    }

    public boolean onTouch(View v, MotionEvent event){
        return true;
    }

    class PlanoDeDibujo extends View {
        public PlanoDeDibujo(Context context){
            super(context);
        }

        protected void onDraw(Canvas canvas){

        }
    }
}
