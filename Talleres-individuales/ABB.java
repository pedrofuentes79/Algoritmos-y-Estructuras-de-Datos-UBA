package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo _raiz;
    private int _cardinal;
    private T _min;
    private T _max;


    private class Nodo {
        T valor;
        Nodo izq;
        Nodo der;
        Nodo padre;
        
        Nodo(T v){
            valor = v;
            izq = null;
            der = null;
            padre = null;
        }
    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
        _min = null;
        _max = null;
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        return _min;
    }

    public T maximo(){
        return _max;
    }

    private Nodo buscar_nodo(Nodo actual, T elem){

        
        if (actual.valor.compareTo(elem) == 0){
            return actual;
        }
        else if (actual != null && actual.valor.compareTo(elem) > 0){
            if (actual.izq == null){
                return actual;
            }
            else {
                return buscar_nodo(actual.izq, elem);
            }
        }
        else if (actual != null && actual.valor.compareTo(elem) < 0){
            if (actual.der == null){
                return actual;
            }
            else {
                return buscar_nodo(actual.der, elem);
            }
        }
        else{return actual;}
    }

    public void insertar(T elem){
        // caso base de arbol vacio
        if (_raiz == null){
            _raiz = new Nodo(elem);
            _cardinal++;
            _min = elem;
            _max = elem;
        }

        // buscar nodo. Si está, no hago nada. Si no está, lo agrego a la izq si es menor y a su der si es mayor.
        Nodo ult_buscado = buscar_nodo(_raiz, elem);

        if (ult_buscado.valor.compareTo(elem) > 0){
            Nodo newnode = new Nodo(elem);
            newnode.padre = ult_buscado;
            ult_buscado.izq = newnode;
            _cardinal++;
            
            //minmax
            if (elem.compareTo(_max) > 0){
                _max = elem;
            }
            if (elem.compareTo(_min) < 0){
                _min = elem;
            }
        }
        else if (ult_buscado.valor.compareTo(elem) < 0){
            Nodo newnode = new Nodo(elem);
            newnode.padre = ult_buscado;
            ult_buscado.der = newnode;
            _cardinal++;

            //minmax
            if (elem.compareTo(_max) > 0){
                _max = elem;
            }
            if (elem.compareTo(_min) < 0){
                _min = elem;
            }
        }
    }


    public boolean pertenece(T elem){
        Nodo ult_buscado = buscar_nodo(_raiz, elem);
        return (ult_buscado.valor.compareTo(elem) == 0);
    }

    public void eliminar(T elem){
        Nodo ult_buscado = buscar_nodo(_raiz, elem);
        

        if (ult_buscado.valor.compareTo(elem) == 0){
            // caso 0 hijos
            if (ult_buscado.der == null && ult_buscado.izq == null){
                // caso hijo izquierdo
                if (ult_buscado.padre.izq.valor.compareTo(ult_buscado.valor) == 0){
                    ult_buscado.padre.izq = null;
                }
                // caso hijo derecho
                else{
                    ult_buscado.padre.der = null;
                }
                _cardinal--;
            }
            // caso 1 hijo (izq)
            else if (ult_buscado.der == null && ult_buscado.izq != null){
                // no lo borro, lo reemplazo por su hijo y borro a su hijo
                T val_hijo = ult_buscado.izq.valor;
                ult_buscado.valor = val_hijo;
                ult_buscado.izq = null;
                _cardinal--;

            }
            // caso 1 hijo (der)
            else if (ult_buscado.izq == null && ult_buscado.der != null){
                T val_hijo = ult_buscado.der.valor;
                ult_buscado.valor = val_hijo;
                ult_buscado.der = null;
                _cardinal--;

            }

            // caso 2 hijos
            else if (ult_buscado.izq != null && ult_buscado.der != null){
                // lo reemplazo por el valor de su sucesor y borro a su sucesor.
                Nodo suc = sucesor(ult_buscado);
                T valor_sucesor = suc.valor;
                eliminar(valor_sucesor);
                ult_buscado.valor = valor_sucesor;

            }

        }
        // si ult_buscado != elem no hago nada, no esta elem en el arbol

    }

    private Nodo sucesor(Nodo actual){
        Nodo minVisto = actual.der;
        return minimoAux(actual.der, minVisto);
    }

    private Nodo minimoAux(Nodo actual, Nodo minVisto){
        if (minVisto == null){
            return null;
        }
        else if (actual.valor.compareTo(minVisto.valor) < 0){
            if (actual.izq != null){
                return minimoAux(actual.izq, actual);
            }
            else {
                return actual;
            }
        }
        else {
            if (actual.izq != null){
                return minimoAux(actual.izq, minVisto);
            }
            else {
                return minVisto;
            }
        }
    }

    private Nodo sucesorSiguiente(Nodo act){
        // el abuelo no puede ser el sucesor
        // entonces, si no tiene sucesor (con la funcion anterior, que no considera el padre)
        // chequeo si el padre es el sucesor y lo devuelvo
        if (sucesor(act) != null){
            return sucesor(act);
        }
        else{
            // si no tiene un sucesor en sus hijos, recorro para arriba
            Nodo nPath = new Nodo(act.valor);
            nPath.padre = act.padre;
            while (nPath != null && nPath.valor.compareTo(act.valor) < 0){
                nPath = nPath.padre;
            }
            return nPath.padre;
        }
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        ABB_Iterador(){
            // inicializo el iterador en el nodo con el minimo valor
            _actual = buscar_nodo(_raiz, _min);
        }

        public boolean haySiguiente() {            
            return (_actual.valor != _max);
        }
    
        public T siguiente() {
            T ac_valor = _actual.valor;
            _actual = sucesorSiguiente(_actual);
            return ac_valor;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
