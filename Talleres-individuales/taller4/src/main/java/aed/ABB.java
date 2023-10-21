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
        // busca un nodo que esté "por debajo" del nodo actual. Si no lo encuentra, devuelve hasta donde llegó.
        // si llega a un null (arbol vacío) devuelve null

        if (actual == null){
            return null;
        }
        // caso en el que el nodo actual es el buscado
        else if (actual.valor.compareTo(elem) == 0){
            return actual;
        }
        // caso en el que el nodo actual es mayor al buscado
        else if (actual != null && actual.valor.compareTo(elem) > 0){
            if (actual.izq == null){
                return actual;
            }
            else {
                return buscar_nodo(actual.izq, elem);
            }
        }
        // caso en el que el nodo actual es menor al buscado
        else if (actual != null && actual.valor.compareTo(elem) < 0){
            if (actual.der == null){
                return actual;
            }
            else {
                return buscar_nodo(actual.der, elem);
            }
        }
        else{
            return actual;
        }
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
        if (ult_buscado == null){
            return false;
        }
        else {
            return (ult_buscado.valor.compareTo(elem) == 0);
        }
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
            // creo un nuevo nodo para recorrer hacia arriba. Como no quiero ir hacia abajo, no le asigno los hijos, solo el padre.
            Nodo nPath = new Nodo(act.valor);
            nPath.padre = act.padre;
            
            // mientras el padre no sea null y el nodo actual sea menor que act.valor, voy para arriba hasta encontrar su sucesor.
            while (nPath.padre != null && nPath.valor.compareTo(act.valor) <= 0){
                nPath = nPath.padre;
            }
            return nPath;
        }
    }

    public String toString(){
        // initialize buffer
        StringBuffer sb = new StringBuffer();
        sb.append("{");

        Iterador<T> it = this.iterador();
        while (it.haySiguiente()){
            sb.append(it.siguiente());
            if (it.haySiguiente()){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        ABB_Iterador(){
            // inicializo el iterador en el nodo con el minimo valor
            _actual = buscar_nodo(_raiz, _min);
        }

        public boolean haySiguiente() { 
            if (_actual == null){
                return false;
            }
            else {
                return (_actual.valor.compareTo(_max) <= 0);
            }
        }
    
        public T siguiente() {
            // caso base del iterador iniciado con conjunto vacio. Si tiene elementos, _actual no deberia ser null.
            // Entonces, le asigno el siguiente del minimo y devuelvo el minimo, como si siempre hubiese estado inicializado ahí.
            if (_actual == null){
                Nodo aux = buscar_nodo(_raiz, _min);
                _actual = sucesorSiguiente(aux);
                return aux.valor;
            }
            else if (_actual.valor == _max){
                T ac_valor = _actual.valor;
                _actual = null;
                return ac_valor;
            }
            else {
                T ac_valor = _actual.valor;
                _actual = sucesorSiguiente(_actual);
                return ac_valor;
            }
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}