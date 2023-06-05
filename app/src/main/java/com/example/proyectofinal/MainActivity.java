package com.example.proyectofinal;

//Importar modulos para nuestra aplicacion:
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.graphics.Point;
import android.widget.Toast;


// --------------------------------------------------------
//Clase Figuras, define como sera cada una de estas
class Figuras{
    //Atributos
    private int TipoFigura;
    private int Color;
    private Point Ini, Fin;

    //Atributos by default.
    Figuras(){
        TipoFigura = 0;
        Color = 0;
        Ini = null;
        Fin = null;
    }

    //Metodos, simplemente para darles el valor de cada uno de sus atributos.
    //Metodos para dar valor
    public void setTipoFigura(int tipo){ TipoFigura = tipo; }
    public void setColor(int color){ Color = color; }
    public void setIni(Point aux){ Ini = aux;}
    public void setFin(Point aux){ Fin = aux;}

    //Metodos para recibir valor.
    public Point getIni(){ return Ini;}
    public Point getFin(){ return Fin;}
    public int getTipoFigura(){return TipoFigura;}
    public int getColor(){return Color;}
}



// ---------------------------------------------------------
//MAIN ACTIVITY
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    //Variables que usaremos en nuestro programa
    RelativeLayout layout;
    PlanoDeDibujo Miplano;

    private boolean PrimerPunto = true, SegundoPunto = true;
    private int FiguraSeleccionada, ColorSeleccionado;
    private Point Ini, Fin;
    // Definición de variables para los rectángulos
    private Rect SelectionLine, SelectionCircle, SelectionSquare, SelectionRect, SelectionOval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Donde dibujaremos nuestro contenido.
        layout = findViewById(R.id.layout);
        Miplano = new PlanoDeDibujo((this));
        Miplano.setOnTouchListener(this);
        layout.addView(Miplano);
        //Puntos iniciales por default.
        Ini = new Point(300, 300);
        Fin = new Point(500, 500);
        // Se define la localización de los botones para seleccionar las opciones
        SelectionLine = new Rect(0, 0, 200, 200);
        SelectionCircle = new Rect(200, 0, 400, 200);
        SelectionSquare = new Rect(400, 0, 600, 200);
        SelectionRect = new Rect(600, 0, 800, 200);
        SelectionOval = new Rect(800, 0, 1000, 200);
    }

    //Funcion para poder hacer Touch dentro de nuestro canvas.
    public boolean onTouch(View v, MotionEvent event) {
        //Obtener posiciones para nuestra linea.
        int Posx = (int) event.getX(), Posy = (int) event.getY();
        switch (event.getAction()) {
            //Action al oprimir la pantalla
            case MotionEvent.ACTION_DOWN:
                // Tocar los botones para la figura correspondiente
                // Seleccionar Linea
                if((Posx > 0)&&(Posx < 200) && (Posy > 0)&&(Posy < 200)){
                    FiguraSeleccionada = 1;
                    Toast.makeText(this, "Linea sel", Toast.LENGTH_SHORT).show();
                    break;
                }
                // Seleccionar Circulo
                if((Posx > 200)&&(Posx < 400) && (Posy > 0)&&(Posy < 200)){
                    FiguraSeleccionada = 2;
                    Toast.makeText(this, "Círculo sel", Toast.LENGTH_SHORT).show();
                    break;
                }
                // Seleccionar Cuadrado
                if((Posx > 400)&&(Posx < 600) && (Posy > 0)&&(Posy < 200)){
                    FiguraSeleccionada = 3;
                    Toast.makeText(this, "Cuadrado sel", Toast.LENGTH_SHORT).show();
                    break;
                }
                // Seleccionar Rectangulo
                if((Posx > 600)&&(Posx < 800) && (Posy > 0)&&(Posy < 200)){
                    FiguraSeleccionada = 4;
                    Toast.makeText(this, "Rectangulo sel", Toast.LENGTH_SHORT).show();
                    break;
                }
                // Seleccionar Elipse
                if((Posx > 800)&&(Posx < 1000) && (Posy > 0)&&(Posy < 200)){
                    FiguraSeleccionada = 5;
                    Toast.makeText(this, "Elipse sel", Toast.LENGTH_SHORT).show();
                    break;
                }

                // Inicio de la figura o línea
                if (PrimerPunto) {
                    Ini.set(Posx, Posy);
                    PrimerPunto = false;
                    SegundoPunto = false;
                }

                break;

            //Action al moverse en la pantalla.
            case MotionEvent.ACTION_MOVE:
                Fin.set(Posx, Posy);
                SegundoPunto = true;
                break;

            //Action al quitar el dedo de la pantalla
            case MotionEvent.ACTION_UP:
                PrimerPunto = true;
                SegundoPunto = false;
                break;
        }
        Miplano.invalidate();
        return true;
    }

    //Plano en donde dibujaremos, y le decimos de que color, anchura y de donde a donde
    //Sera nuestra raya.
    class PlanoDeDibujo extends View {
        public PlanoDeDibujo(Context context){
            super(context);
        }
        protected void onDraw(Canvas canvas){
            // Constructor de onDraw
            // Para la función
            super.onDraw(canvas);

            // Definición de los pinceles
            Paint paint = new Paint();
            Paint PincelRect = new Paint();
            Paint PincelIcono = new Paint();

            // Se pinta la línea o fígura que realiza el usuario

            paint.setStrokeWidth(10);
            paint.setARGB(255, 255 ,0 ,0);

            if (SegundoPunto){

                switch(FiguraSeleccionada){
                    // Se selecciona la línea como figura
                    case 1:
                        canvas.drawLine(Ini.x, Ini.y, Fin.x, Fin.y, paint);
                        break;
                    // Se selecciona el círculo como figura
                    case 2:
                        float difX = Fin.x - Ini.x;
                        float difY = Fin.y - Ini.y;
                        float radio = (float) sqrt((difX*difX) + (difY*difY));
                        canvas.drawCircle(Ini.x, Ini.y, radio, paint);
                        break;
                    // Se selecciona el cuadrado como figura
                    case 3:
                        difX = abs(Fin.x - Ini.x);
                        if ((Fin.x > Ini.x) && (Fin.y > Ini.y))
                            canvas.drawRect(Ini.x, Ini.y, Ini.x + difX, Ini.y + difX, paint);
                        else if ((Fin.x < Ini.x) && (Fin.y < Ini.y))
                            canvas.drawRect(Ini.x - difX, Ini.y -difX, Ini.x, Ini.y, paint);
                        else if ((Fin.x < Ini.x) && (Fin.y > Ini.y))
                            canvas.drawRect(Ini.x - difX, Ini.y , Ini.x, Ini.y + difX, paint);
                        else if ((Fin.x > Ini.x) && (Fin.y < Ini.y))
                            canvas.drawRect(Ini.x , Ini.y - difX , Ini.x + difX, Ini.y , paint);
                        break;
                    // Se selecciona el rectangulo como figura
                    case 4:
                        difX = abs(Fin.x - Ini.x);
                        difY = abs(Fin.y - Ini.y);
                        if ((Fin.x > Ini.x) && (Fin.y > Ini.y))
                            canvas.drawRect(Ini.x, Ini.y, Ini.x + difX, Ini.y + difY, paint);
                        else if ((Fin.x < Ini.x) && (Fin.y < Ini.y))
                            canvas.drawRect(Ini.x - difX, Ini.y - difY, Ini.x, Ini.y, paint);
                        else if ((Fin.x < Ini.x) && (Fin.y > Ini.y))
                            canvas.drawRect(Ini.x - difX, Ini.y , Ini.x, Ini.y + difY, paint);
                        else if ((Fin.x > Ini.x) && (Fin.y < Ini.y))
                            canvas.drawRect(Ini.x , Ini.y - difY , Ini.x + difX, Ini.y , paint);
                        break;
                    // Se selecciona el elipse como figura
                    case 5:
                        difX = abs(Fin.x - Ini.x);
                        difY = abs(Fin.y - Ini.y);
                        if ((Fin.x > Ini.x) && (Fin.y > Ini.y))
                            canvas.drawOval(Ini.x, Ini.y, Ini.x + difX, Ini.y + difY, paint);
                        else if ((Fin.x < Ini.x) && (Fin.y < Ini.y))
                            canvas.drawOval(Ini.x - difX, Ini.y - difY, Ini.x, Ini.y, paint);
                        else if ((Fin.x < Ini.x) && (Fin.y > Ini.y))
                            canvas.drawOval(Ini.x - difX, Ini.y , Ini.x, Ini.y + difY, paint);
                        else if ((Fin.x > Ini.x) && (Fin.y < Ini.y))
                            canvas.drawOval(Ini.x , Ini.y - difY , Ini.x + difX, Ini.y , paint);
                        break;
                }
            }

            // Botón para seleccionar linea
            // Rectángulo
            PincelRect.setARGB(255, 0, 155, 255);
            canvas.drawRect(SelectionLine, PincelRect);
            // Linea
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawLine(40, 160, 160, 40, PincelIcono);

            //Botón para seleccionar círculo
            // Rectángulo
            PincelRect.setARGB(255, 255, 200, 0);
            canvas.drawRect(SelectionCircle, PincelRect);
            // Circulo
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawCircle(300,100 , 65, PincelIcono);

            //Botón para seleccionar cuadrado
            // Rectángulo
            PincelRect.setARGB(255, 255, 100, 0);
            canvas.drawRect(SelectionSquare, PincelRect);
            // Cuadrado
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawRect(450, 50, 550, 150, PincelIcono);

            //Botón para seleccionar rectángulo
            // Rectángulo
            PincelRect.setARGB(255, 255, 0, 120);
            canvas.drawRect(SelectionRect, PincelRect);
            // Rectangulo
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawRect(640, 50, 760, 130, PincelIcono);

            //Botón para seleccionar ovalos
            // Rectángulo
            PincelRect.setARGB(255, 0, 255, 40);
            canvas.drawRect(SelectionOval, PincelRect);
            // Oval
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawOval(840, 60, 960, 140, PincelIcono);

        }
    }
}

//😃😃😃😃😃😃