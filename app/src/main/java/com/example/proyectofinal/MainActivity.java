package com.example.proyectofinal;

//Importar modulos para nuestra aplicacion:
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.graphics.Point;

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


//MAIN ACTIVITY
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    //Variables que usaremos en nuestro programa
    RelativeLayout layout;
    PlanoDeDibujo Miplano;
    private boolean PrimerPunto = true, SegundoPunto = true;
    private int FiguraSeleccionada, ColorSeleccionado;
    private Point Ini, Fin;


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
        Ini = new Point(0, 0);
        Fin = new Point(500, 500);
    }

    //Funcion para poder hacer Touch dentro de nuestro canvas.
    public boolean onTouch(View v, MotionEvent event) {
        //Obtener posiciones para nuestra linea.
        int Posx = (int) event.getX(), Posy = (int) event.getY();
        switch (event.getAction()) {
            //Action al oprimir la pantalla
            case MotionEvent.ACTION_DOWN:
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
                if (SegundoPunto){
                    Figuras Fig = new Figuras();
                }
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
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setStrokeWidth(10);
            paint.setARGB(255, 255 ,0 ,0);
            canvas.drawLine(Ini.x, Ini.y, Fin.x, Fin.y,paint);

        }
    }
}

//😃😃😃😃😃😃