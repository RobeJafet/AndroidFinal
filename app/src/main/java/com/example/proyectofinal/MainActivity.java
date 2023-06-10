package com.example.proyectofinal;

//Importar modulos para nuestra aplicacion:
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.graphics.Path;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.graphics.Point;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;


// --------------------------------------------------------
//Clase Figuras, define como sera cada una de estas
class Figuras{
    //Atributos
    private int TipoFigura, Color, Lados;
    private Point Ini, Fin;
    private float Contorno;

    //Atributos by default.
    Figuras(){
        TipoFigura = 0;
        Color = 1;
        Ini = null;
        Fin = null;
        Contorno = 10;
        Lados = 0;
    }

    //Metodos, simplemente para darles el valor de cada uno de sus atributos.
    //Metodos para dar valor
    public void setTipoFigura(int tipo){ TipoFigura = tipo; }
    public void setColor(int color){ Color = color; }
    public void setIni(Point aux){ Ini = aux;}
    public void setFin(Point aux){ Fin = aux;}
    public void setContorno(float contorno){Contorno = contorno;}
    public void setLados(int lados){Lados = lados;}
    //Metodos para recibir valor.
    public Point getIni(){ return Ini;}
    public Point getFin(){ return Fin;}
    public int getTipoFigura(){return TipoFigura;}
    public int getColor(){return Color;}
    public float getContorno(){return Contorno;}
    public int getLados(){return Lados;}
}



// ---------------------------------------------------------
//MAIN ACTIVITY
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    //Variables que usaremos en nuestro programa
    private RelativeLayout layout;
    private PlanoDeDibujo Miplano;
    private boolean PrimerPunto = true, SegundoPunto = true;
    private int FiguraSeleccionada, ColorSeleccionado = 1, lados;
    private float Contorno = 10;
    private Point Ini, Fin;
    private ArrayList<Figuras> Lista;
    private int[][] Color = {{0, 0, 0}, // NEGRO
            {222, 49, 99}, //ROJO
            {159, 226, 191}, // VERDE
            {100, 149, 237}, // AZUL
            {223, 255, 0}, //AMARILLO
            {64, 224, 208}, //CYAN
            {204, 204, 255}}; // MAGENTA
    // Definición de variables para los rectángulos
    private Rect SelectionLine, SelectionCircle, SelectionSquare, SelectionRect, SelectionOval, SelectionPol,
            SelectionClear, SelectionSave, SelectionOpen, SelectionColor;
    private Rect SelectionPlus, SelectionMinus;

    private Random random = new Random();
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
        SelectionPol = new Rect(1000, 0, 1200, 200);

        SelectionClear = new Rect(1240, 0, 1440, 200);

        SelectionPlus = new Rect(0, 2110, 200, 2310);
        SelectionMinus = new Rect(200, 2110, 400, 2310);

        SelectionSave = new Rect(1240, 2110, 1440, 2310);
        SelectionOpen = new Rect(1040, 2110, 1240, 2310);

        SelectionColor = new Rect(400, 2110, 1040, 2310);
        // ARREGLO PARA FIGURAS
        Lista = new ArrayList<>();
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
                // Seleccionar Polígono
                if((Posx > 1000)&&(Posx < 1200) && (Posy > 0)&&(Posy < 200)){
                    FiguraSeleccionada = 6;
                    Toast.makeText(this, "Polígono sel", Toast.LENGTH_SHORT).show();
                    lados = random.nextInt(11) + 3; // número de lados del polígono
                    break;
                }
                // Seleccionar Limpiar
                if((Posx > 1240)&&(Posx < 1440) && (Posy > 0)&&(Posy < 200)){
                    Toast.makeText(this, "Clear canvas", Toast.LENGTH_SHORT).show();
                    Lista.clear();
                    break;
                }
                // Seleccionar Mas
                if((Posx > 0)&&(Posx < 200) && (Posy > 2110)&&(Posy < 2310)){
                    if(Contorno < 50)
                        Contorno += 5;
                    Toast.makeText(this, "Mas", Toast.LENGTH_SHORT).show();
                    break;
                }
                // Seleccionar Menos
                if((Posx > 200)&&(Posx < 400) && (Posy > 2110)&&(Posy < 2310)){
                    if (Contorno != 0)
                        Contorno -= 5;
                    Toast.makeText(this, "Menos", Toast.LENGTH_SHORT).show();
                    break;
                }
                // Seleccionar Guardar
                if((Posx > 1240)&&(Posx < 1440) && (Posy > 2110)&&(Posy < 2310)){
                    // PREPARAR EL ARCHIVO PARA AESCRITURA DE DATOS STRING
                    try{
                        String Aux;
                        FileOutputStream ArchivoSalida = openFileOutput("canva.txt", MODE_PRIVATE);
                        OutputStreamWriter TextoSalida = new OutputStreamWriter(ArchivoSalida);
                        for(int k = 0; k < Lista.size(); k++){
                            // GUARDAR TIPO DE FIGURA
                            Aux = (Lista.get(k).getTipoFigura()) + "\n";
                            TextoSalida.write(Aux);
                            Aux = (Lista.get(k).getIni().x) + "\n";
                            TextoSalida.write(Aux);
                            Aux = (Lista.get(k).getIni().y) + "\n";
                            TextoSalida.write(Aux);
                            Aux = (Lista.get(k).getFin().x) + "\n";
                            TextoSalida.write(Aux);
                            Aux = (Lista.get(k).getFin().y) + "\n";
                            TextoSalida.write(Aux);
                            Aux = (Lista.get(k).getColor()) + "\n";
                            TextoSalida.write(Aux);
                            Aux = (Lista.get(k).getContorno()) + "\n";
                            TextoSalida.write(Aux);
                            Aux = (Lista.get(k).getLados()) + "\n";
                            TextoSalida.write(Aux);
                        }
                        TextoSalida.close();
                        ArchivoSalida.close();
                        Toast.makeText(this, "Canvas guardado correctamente", Toast.LENGTH_SHORT).show();
                    }catch(Exception caso){
                        Toast.makeText(this, "ERROR:canvas no guardado", Toast.LENGTH_SHORT).show();
                        caso.printStackTrace();
                    }
                    break;
                }
                // Seleccionar Cargar
                if((Posx > 1040)&&(Posx < 1240) && (Posy > 2110)&&(Posy < 2310)){
                    try{
                        Lista.clear();
                        // PREPARAR EL ARCHIVO PARA LA LECTURA POR LÍNEA
                        FileInputStream ArchivoEntrada = openFileInput("canva.txt");
                        InputStreamReader TextoEntrada = new InputStreamReader(ArchivoEntrada);
                        BufferedReader LineaDeTextoDeEntrada = new BufferedReader(TextoEntrada);
                        // LEER LAS LÍNEAS DE TEXTO DENTRO DEL ARCHIVO
                        String linea = "", linea_ = "";
                        // LEER TODAS LAS LÍNEAS DE TEXTO
                        linea = LineaDeTextoDeEntrada.readLine(); // TIPO FIGURA
                        while(linea != null){
                            Figuras Fig = new Figuras();
                            Fig.setTipoFigura(Integer.parseInt(linea));
                            linea = LineaDeTextoDeEntrada.readLine();
                            linea_ = LineaDeTextoDeEntrada.readLine();
                            Point auxIni = new Point(Integer.parseInt(linea), Integer.parseInt(linea_));
                            Fig.setIni(auxIni);
                            linea = LineaDeTextoDeEntrada.readLine();
                            linea_ = LineaDeTextoDeEntrada.readLine();
                            Point auxFin = new Point(Integer.parseInt(linea), Integer.parseInt(linea_));
                            Fig.setFin(auxFin);
                            linea = LineaDeTextoDeEntrada.readLine(); // COLOR
                            Fig.setColor(Integer.parseInt(linea));
                            linea = LineaDeTextoDeEntrada.readLine(); // CONTORNO
                            Fig.setContorno(Float.parseFloat(linea));
                            linea = LineaDeTextoDeEntrada.readLine(); // COLOR
                            Fig.setLados(Integer.parseInt(linea));
                            //System.out.println("Tipo:"+Fig.getTipoFigura());
                            //System.out.println("Ini:"+Fig.getIni().x+","+Fig.getIni().y);
                            //System.out.println("Fin:"+Fig.getFin().x+","+Fig.getFin().y);
                            //System.out.println("Color: "+Fig.getColor());
                            //System.out.println("Contorno: "+Fig.getContorno());
                            Lista.add(Fig);
                            linea = LineaDeTextoDeEntrada.readLine(); // TIPO FIGURA
                        }
                        // CERRAR LOS FLUJOS UTILIZADOS
                        TextoEntrada.close();
                        ArchivoEntrada.close();
                        Toast.makeText(this, "Canvas cargado correctamente", Toast.LENGTH_SHORT).show();
                        // System.out.println("Total de figuras cargadas: "+Lista.size());
                    }
                    catch(Exception caso){
                        Toast.makeText(this, "ERROR:canvas no cargado", Toast.LENGTH_SHORT).show();
                        caso.printStackTrace();
                    }
                    break;
                }
                // Seleccionar Color
                if((Posx > 400)&&(Posx < 1240) && (Posy > 2110)&&(Posy < 2310)){
                    ColorSeleccionado+=1;
                    if(ColorSeleccionado > 7)
                        ColorSeleccionado=1;
                    Toast.makeText(this, "Cambiar color", Toast.LENGTH_SHORT).show();
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
                if (SegundoPunto){
                    Figuras Fig = new Figuras();
                    Fig.setTipoFigura(FiguraSeleccionada);
                    Fig.setColor(ColorSeleccionado);
                    Point PuntoInicial = new Point(Ini.x, Ini.y);
                    Fig.setIni(PuntoInicial);
                    Point PuntoFinal = new Point(Fin.x, Fin.y);
                    Fig.setFin(PuntoFinal);
                    Fig.setContorno(Contorno);
                    Fig.setLados(lados);
                    Lista.add(Fig);
                    Contorno = 10;
                    System.out.println(Lista.size());
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
            // Constructor de onDraw
            // Para la función
            super.onDraw(canvas);

            // Definición de los pinceles
            Paint paint = new Paint();
            Paint PincelRect = new Paint();
            Paint PincelIcono = new Paint();

            // Se pinta la línea o fígura que realiza el usuario
            paint.setStrokeWidth(Contorno);
            paint.setStyle(Paint.Style.STROKE);
            // MOSTRAR FIGURA EN PROGRESO DE DIBUJADO
            switch(ColorSeleccionado){
                case 1: paint.setARGB(255, Color[0][0], Color[0][1], Color[0][2]);
                    PincelRect.setARGB(255, Color[0][0], Color[0][1], Color[0][2]);
                    break;
                case 2: paint.setARGB(255, Color[1][0], Color[1][1], Color[1][2]);
                    PincelRect.setARGB(255, Color[1][0], Color[1][1], Color[1][2]);
                    break;
                case 3: paint.setARGB(255, Color[2][0], Color[2][1], Color[2][2]);
                    PincelRect.setARGB(255, Color[2][0], Color[2][1], Color[2][2]);
                    break;
                case 4: paint.setARGB(255, Color[3][0], Color[3][1], Color[3][2]);
                    PincelRect.setARGB(255, Color[3][0], Color[3][1], Color[3][2]);
                    break;
                case 5: paint.setARGB(255, Color[4][0], Color[4][1], Color[4][2]);
                    PincelRect.setARGB(255, Color[4][0], Color[4][1], Color[4][2]);
                    break;
                case 6: paint.setARGB(255, Color[5][0], Color[5][1], Color[5][2]);
                    PincelRect.setARGB(255, Color[5][0], Color[5][1], Color[5][2]);
                    break;
                case 7: paint.setARGB(255, Color[6][0], Color[6][1], Color[6][2]);
                    PincelRect.setARGB(255, Color[6][0], Color[6][1], Color[6][2]);
                    break;
            }
            canvas.drawRect(SelectionColor, PincelRect);
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
                    case 6:
                        // Dibujar el polígono
                        difX = Fin.x - Ini.x;
                        difY = Fin.y - Ini.y;
                        radio = (float) sqrt((difX*difX) + (difY*difY));
                        // Define las coordenadas de los vértices del polígono
                        Path path = new Path();
                        float angulo = (float) (2 * Math.PI / lados);
                        float startingAngle = (float) (Math.PI / 2); // 90 grados (12 en punto en un reloj)
                        for (int i = 0; i < lados; i++) {
                            float x = (float) (Ini.x + radio * Math.cos(startingAngle + i * angulo));
                            float y = (float) (Ini.y + radio * Math.sin(startingAngle + i * angulo));
                            if (i == 0) {
                                path.moveTo(x, y);
                            } else {
                                path.lineTo(x, y);
                            }
                        }
                        path.close(); // Cierra el path (une el último vértice con el primero)
                        // Dibuja el polígono en el canvas
                        canvas.drawPath(path, paint);
                        break;
                }
            }
            for(int k = 0; k < Lista.size(); k++){
                System.out.println("Tipo:"+Lista.get(k).getTipoFigura());
                System.out.println("Ini:"+Lista.get(k).getIni().x+","+Lista.get(k).getIni().y);
                System.out.println("Fin:"+Lista.get(k).getFin().x+","+Lista.get(k).getFin().y);
                System.out.println("Color: "+Lista.get(k).getColor());
                System.out.println("Contorno: "+Lista.get(k).getContorno());
                System.out.println("Lados: "+Lista.get(k).getLados());
            }
            // MOSTRAR LAS FIGURAS EN EL CANVA
            for(int k = 0; k < Lista.size(); k++){
                // CAMBIR DEAR DE COLOR EL PINCEL
                switch(Lista.get(k).getColor()){
                    case 1: paint.setARGB(255, Color[0][0], Color[0][1], Color[0][2]);
                        break;
                    case 2: paint.setARGB(255, Color[1][0], Color[1][1], Color[1][2]);
                        break;
                    case 3: paint.setARGB(255, Color[2][0], Color[2][1], Color[2][2]);
                        break;
                    case 4: paint.setARGB(255, Color[3][0], Color[3][1], Color[3][2]);
                        break;
                    case 5: paint.setARGB(255, Color[4][0], Color[4][1], Color[4][2]);
                        break;
                    case 6: paint.setARGB(255, Color[5][0], Color[5][1], Color[5][2]);
                        break;
                    case 7: paint.setARGB(255, Color[6][0], Color[6][1], Color[6][2]);
                        break;
                }
                // CAMBIA Contorno
                paint.setStrokeWidth(Lista.get(k).getContorno());
                switch(Lista.get(k).getTipoFigura()){
                    // Se selecciona la línea como figura
                    case 1:
                        canvas.drawLine(Lista.get(k).getIni().x, Lista.get(k).getIni().y, Lista.get(k).getFin().x, Lista.get(k).getFin().y, paint);
                        break;
                    // Se selecciona el círculo como figura
                    case 2:
                        float difX = Lista.get(k).getFin().x - Lista.get(k).getIni().x;
                        float difY = Lista.get(k).getFin().y - Lista.get(k).getIni().y;
                        float radio = (float) sqrt((difX*difX) + (difY*difY));
                        canvas.drawCircle(Lista.get(k).getIni().x, Lista.get(k).getIni().y, radio, paint);
                        break;
                    // Se selecciona el cuadrado como figura
                    case 3:
                        difX = abs(Lista.get(k).getFin().x - Lista.get(k).getIni().x);
                        if ((Lista.get(k).getFin().x > Lista.get(k).getIni().x) && (Lista.get(k).getFin().y > Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x, Lista.get(k).getIni().y, Lista.get(k).getIni().x + difX, Lista.get(k).getIni().y + difX, paint);
                        else if ((Lista.get(k).getFin().x < Lista.get(k).getIni().x) && (Lista.get(k).getFin().y < Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x - difX, Lista.get(k).getIni().y -difX, Lista.get(k).getIni().x, Lista.get(k).getIni().y, paint);
                        else if ((Lista.get(k).getFin().x < Lista.get(k).getIni().x) && (Lista.get(k).getFin().y > Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x - difX, Lista.get(k).getIni().y , Lista.get(k).getIni().x, Lista.get(k).getIni().y + difX, paint);
                        else if ((Lista.get(k).getFin().x > Lista.get(k).getIni().x) && (Lista.get(k).getFin().y < Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x , Lista.get(k).getIni().y - difX , Lista.get(k).getIni().x + difX, Lista.get(k).getIni().y , paint);
                        break;
                    // Se selecciona el rectangulo como figura
                    case 4:
                        difX = abs(Lista.get(k).getFin().x - Lista.get(k).getIni().x);
                        difY = abs(Lista.get(k).getFin().y - Lista.get(k).getIni().y);
                        if ((Lista.get(k).getFin().x > Lista.get(k).getIni().x) && (Lista.get(k).getFin().y > Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x, Lista.get(k).getIni().y, Lista.get(k).getIni().x + difX, Lista.get(k).getIni().y + difY, paint);
                        else if ((Lista.get(k).getFin().x < Lista.get(k).getIni().x) && (Lista.get(k).getFin().y < Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x - difX, Lista.get(k).getIni().y - difY, Lista.get(k).getIni().x, Lista.get(k).getIni().y, paint);
                        else if ((Lista.get(k).getFin().x < Lista.get(k).getIni().x) && (Lista.get(k).getFin().y > Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x - difX, Lista.get(k).getIni().y , Lista.get(k).getIni().x, Lista.get(k).getIni().y + difY, paint);
                        else if ((Lista.get(k).getFin().x > Lista.get(k).getIni().x) && (Lista.get(k).getFin().y < Lista.get(k).getIni().y))
                            canvas.drawRect(Lista.get(k).getIni().x , Lista.get(k).getIni().y - difY , Lista.get(k).getIni().x + difX, Lista.get(k).getIni().y , paint);
                        break;
                    // Se selecciona el elipse como figura
                    case 5:
                        difX = abs(Lista.get(k).getFin().x - Lista.get(k).getIni().x);
                        difY = abs(Lista.get(k).getFin().y - Lista.get(k).getIni().y);
                        if ((Lista.get(k).getFin().x > Lista.get(k).getIni().x) && (Lista.get(k).getFin().y > Lista.get(k).getIni().y))
                            canvas.drawOval(Lista.get(k).getIni().x, Lista.get(k).getIni().y, Lista.get(k).getIni().x + difX, Lista.get(k).getIni().y + difY, paint);
                        else if ((Lista.get(k).getFin().x < Lista.get(k).getIni().x) && (Lista.get(k).getFin().y < Lista.get(k).getIni().y))
                            canvas.drawOval(Lista.get(k).getIni().x - difX, Lista.get(k).getIni().y - difY, Lista.get(k).getIni().x, Lista.get(k).getIni().y, paint);
                        else if ((Lista.get(k).getFin().x < Lista.get(k).getIni().x) && (Lista.get(k).getFin().y > Lista.get(k).getIni().y))
                            canvas.drawOval(Lista.get(k).getIni().x - difX, Lista.get(k).getIni().y , Lista.get(k).getIni().x, Lista.get(k).getIni().y + difY, paint);
                        else if ((Lista.get(k).getFin().x > Lista.get(k).getIni().x) && (Lista.get(k).getFin().y < Lista.get(k).getIni().y))
                            canvas.drawOval(Lista.get(k).getIni().x , Lista.get(k).getIni().y - difY , Lista.get(k).getIni().x + difX, Lista.get(k).getIni().y , paint);
                        break;
                    case 6:
                        // Dibujar el polígono
                        difX = Lista.get(k).getFin().x - Lista.get(k).getIni().x;
                        difY = Lista.get(k).getFin().y - Lista.get(k).getIni().y;
                        radio = (float) sqrt((difX*difX) + (difY*difY));
                        // Define las coordenadas de los vértices del polígono
                        Path path = new Path();
                        float angulo = (float) (2 * Math.PI / Lista.get(k).getLados());
                        float startingAngle = (float) (Math.PI / 2); // 90 grados (12 en punto en un reloj)
                        for (int i = 0; i < Lista.get(k).getLados(); i++) {
                            float x = (float) (Lista.get(k).getIni().x + radio * Math.cos(startingAngle + i * angulo));
                            float y = (float) (Lista.get(k).getIni().y + radio * Math.sin(startingAngle + i * angulo));
                            if (i == 0) {
                                path.moveTo(x, y);
                            } else {
                                path.lineTo(x, y);
                            }
                        }
                        path.close(); // Cierra el path (une el último vértice con el primero)
                        // Dibuja el polígono en el canvas
                        canvas.drawPath(path, paint);
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
            // Elipse
            PincelRect.setARGB(255, 0, 255, 40);
            canvas.drawRect(SelectionOval, PincelRect);
            // Oval
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawOval(840, 60, 960, 140, PincelIcono);

            //Botón para seleccionar polígonos
            // Polígono pendiente
            PincelRect.setARGB(255, 150, 90, 255);
            canvas.drawRect(SelectionPol, PincelRect);
            // Oval
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawOval(840, 60, 960, 140, PincelIcono);

            //Botón para limpiar lienzo
            // Cruz
            PincelRect.setARGB(255, 190, 45, 30);
            canvas.drawRect(SelectionClear, PincelRect);
            // Oval
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawLine(1290, 50, 1390, 150, PincelIcono);
            canvas.drawLine(1390, 50, 1290, 150, PincelIcono);

            //Botón para seleccionar más
            // Rectángulo
            PincelRect.setARGB(255, 255, 0, 0);
            canvas.drawRect(SelectionPlus, PincelRect);
            // Plus
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawLine(50, 2210, 150, 2210, PincelIcono);
            canvas.drawLine(100, 2160, 100, 2260, PincelIcono);

            //Botón para seleccionar menos
            // Rectángulo
            PincelRect.setARGB(255, 0, 0, 255);
            canvas.drawRect(SelectionMinus, PincelRect);
            // Minus
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            canvas.drawLine(250, 2210, 350, 2210, PincelIcono);

            //Botón para guardar lienzo
            // Rectángulo
            PincelRect.setARGB(255, 75, 160, 250);
            canvas.drawRect(SelectionSave, PincelRect);
            // Ícono guardar
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setARGB(255,255,255,255);
            // canvas.drawLine(50, 2210, 150, 2210, PincelIcono);
            // canvas.drawLine(100, 2160, 100, 2260, PincelIcono);

            //Botón para cargar lienzo
            // Rectángulo
            PincelRect.setARGB(255, 240, 250, 110);
            canvas.drawRect(SelectionOpen, PincelRect);
            // Ícono abrir
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);
            // canvas.drawLine(250, 2210, 350, 2210, PincelIcono);

            // Poligono Icono
            PincelIcono.setStrokeWidth(15);
            PincelIcono.setStyle(Paint.Style.STROKE);
            PincelIcono.setARGB(255,255,255,255);

            int radio = 50;

            // Define las coordenadas de los vértices del polígono
            int centerX = 1100;
            int centerY = 100;

            int l = 5;

            Path path = new Path();
            float angulo = (float) (2 * Math.PI / l);
            float startingAngle = (float) (Math.PI / 2);

            for (int i = 0; i < l; i++) {
                float x = (float) (centerX + radio * Math.cos(startingAngle + i * angulo));
                float y = (float) (centerY + radio * Math.sin(startingAngle + i * angulo));

                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }

            path.close(); // Cierra el path (une el último vértice con el primero)

            // Dibuja el polígono en el canvas
            canvas.drawPath(path, PincelIcono);

        }
    }
}

//😃😃😃😃😃😃