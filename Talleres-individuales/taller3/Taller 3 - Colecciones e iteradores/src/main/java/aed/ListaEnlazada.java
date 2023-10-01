package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {

    private Nodo actual;
    private int size;

    private class Nodo {
        Nodo anterior;
        T valor;
        Nodo siguiente;

        Nodo(T v) {
            valor = v;
        }
    }

    public ListaEnlazada() {
        actual = null;
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        nuevo.anterior = null;
        nuevo.siguiente = actual;
        // muevo todo al actual
        actual = nuevo;

        size++;
    }
    
    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (actual == null) {
            actual = nuevo;
            actual.anterior = null;
        } else {
            Nodo curr = actual;
            while (curr.siguiente != null) {
                curr = curr.siguiente;
            }
            curr.siguiente = nuevo;
            nuevo.anterior = curr;
        }
        size++;
    }

    public T obtener(int i) {
        Nodo tmp = actual;

        for (int index = 0; index < i; index++){
            tmp = tmp.siguiente;
        };
        return tmp.valor;
    }

    public void eliminar(int i) {
        Nodo curr = actual;
        Nodo prev = actual;

        for (int j=0; j<i; j++){
            prev = curr;
            curr = curr.siguiente;
        }

        if (i==0){
            actual = curr.siguiente;
        }
        else{
            prev.siguiente = curr.siguiente;
        }
        size--;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo curr = actual;
        for (int i=0; i<indice; i++){
            curr = curr.siguiente;
        }
        curr.valor = elem;
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> copia = new ListaEnlazada<>(null);
        Nodo tmp = actual;
        while (tmp != null){
            T v = tmp.valor;
            copia.agregarAtras(v);
            tmp = tmp.siguiente;
        }
        return copia;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        if(lista != null){
            ListaEnlazada<T> tmpLista = lista.copiar();
            this.actual = tmpLista.actual;
            this.size = lista.size;
        }
        else{
            this.actual = null;
            this.size = 0;
        }
    }
    
    @Override
    public String toString() {
        Nodo tmp = actual;
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        while(tmp.siguiente != null){
            buffer.append(tmp.valor);
            buffer.append(", ");
            tmp = tmp.siguiente;
        }
        buffer.append(tmp.valor);
        buffer.append("]");
        return buffer.toString();

    }
    /* Implementación fallida del Iterador con Nodo en vez de puntero
    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        Nodo curr;
        
        ListaIterador(){
            curr = actual;
        }

        public boolean haySiguiente() {
            //if (curr == null){return false;}
            if (curr.siguiente == null){return false;}
            else {return true;}

        }
        
        public boolean hayAnterior() {
            if (curr.anterior == null){return false;}
            else {return true;}
        }

        public T siguiente() {
            T val = curr.valor;
            curr = curr.siguiente;

            return val;

        }
        

        public T anterior() {
            curr = curr.anterior;
            T val = curr.valor;
            return val;
        }
    }
    */
    private class ListaIterador implements Iterador<T> {
        int indice;
        ListaIterador(){
            indice = 0;
        }

        public boolean haySiguiente(){
            return indice != size;
        }

        public boolean hayAnterior(){
            return indice != 0;
        }
        
        public T siguiente(){
            int i = indice;
            indice++;
            return obtener(i);
        }

        public T anterior(){
            indice--;
            return obtener(indice);
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }
}
