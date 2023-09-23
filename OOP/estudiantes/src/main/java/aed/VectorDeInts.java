package aed;

import java.util.ArrayList;

class VectorDeInts implements SecuenciaDeInts {
    private static final int CAPACIDAD_INICIAL = 0;
    private int[] vec;
    private int size;

    public VectorDeInts() {
        vec = new int[CAPACIDAD_INICIAL];
        size = CAPACIDAD_INICIAL;
    }

    public VectorDeInts(VectorDeInts vector) {
        size = vector.copiar().size;
        vec = vector.copiar().vec;
    }

    public int longitud() {
        return size;
    }

    public void agregarAtras(int i) {
        int new_vec[] = new int[size+1];
        for (int j=0; j<size; j++){
            new_vec[j] = vec[j];
        }
        new_vec[size] = i; 
        vec = new_vec;
        size++;
    }

    public int obtener(int i) {
        return vec[i];
    }

    public void quitarAtras() {
        int new_vec[] = new int[size-1];
        for(int j=0; j<size-1; j++){
            new_vec[j] = vec[j];
        }
        vec = new_vec;
        size--;
    }

    public void modificarPosicion(int indice, int valor) {
        vec[indice] = valor;
    }

    public VectorDeInts copiar() {
        VectorDeInts new_vec = new VectorDeInts();
        
        for (int j=0; j<size; j++){
            new_vec.agregarAtras(vec[j]);
        }
        return new_vec;
    }

}
