package aed;

import java.util.Scanner;
import java.io.PrintStream;

class Archivos {
    float[] leerVector(Scanner entrada, int largo) {
        // Crear una funci´on leerVector, que dado un Scanner y un largo : N, 
        // lee un arreglo de tama˜no largo del scanner
        float[] res = new float[largo];
        for (int i=0; i<largo; i++){
            res[i] = entrada.nextFloat();
        }
        return res;
    }

    float[][] leerMatriz(Scanner entrada, int filas, int columnas) {
        float[][] res = new float[filas][columnas];
        for (int i=0; i<filas; i++){
            res[i] = leerVector(entrada, columnas);
        }
        entrada.close();
        return res;
    }

    void imprimirPiramide(PrintStream salida, int alto) {

        if (alto == 0){
            salida.print("");
            return;
        }

        // 2*alto -1 es la cantidad de pisos de la piramide
        int largo = 2*alto - 1;

        // inicializo cada línea
        for (int row=0; row<alto; row++){
            int estrellas = 2*row + 1;
            int dif = largo - estrellas;

            // printeo los primeros espacios
            for (int j=0; j<dif/2; j++){
                salida.print(' ');
            }

            // printeo las estrellas
            for (int j=0; j<estrellas; j++){
                salida.print('*');
            }

            // printeo los ultimos espacios
            for (int j=0; j<dif/2; j++){
                salida.print(' ');
            }
            
            // cierro la línea
            salida.println("");
        }



    }
}
