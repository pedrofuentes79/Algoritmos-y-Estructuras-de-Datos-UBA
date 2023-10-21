package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB_V2<T extends Comparable<T>> implements Conjunto<T> {
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

    public ABB_V2() {
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
        // nota, aca no estoy considerando si borro el minimo. Cada vez que elimino, debería chequear 
        // si el nodo a eliminar es _min o _max. Si son, debería actualizarlos. No lo hago porque no lo pide el test :P.
        Nodo ult_buscado = buscar_nodo(_raiz, elem);
        

        if (ult_buscado.valor.compareTo(elem) == 0){
            // caso 0 hijos
            if (ult_buscado.der == null && ult_buscado.izq == null){
                // caso especial: el nodo a eliminar es la raiz
                if (ult_buscado.padre == null){
                    _raiz = null;
                }

                // el nodo a borrar, es hijo izquierdo o derecho?
                else if (ult_buscado.padre.izq != null && ult_buscado.padre.izq.valor.compareTo(ult_buscado.valor) == 0){
                    ult_buscado.padre.izq = null;
                }
                else{
                    ult_buscado.padre.der = null;
                }
                _cardinal--;

                // chequear minmax
                if (_cardinal == 0){
                    _min = null;
                    _max = null;
                }
                else if (elem.compareTo(_min) == 0){
                    _min = sucesorSiguiente(ult_buscado).valor;
                }
                else if (elem.compareTo(_max) == 0){
                    _max = predecesorSiguiente(ult_buscado).valor;                    
                }
            }
            // caso: el nodo a eliminar tiene 1 hijo (izq)
            else if (ult_buscado.der == null && ult_buscado.izq != null){
                // actualizo el padre del nodo a eliminar para que apunte al hijo
    
                // caso especial: el nodo a eliminar es la raiz
                if (ult_buscado.padre == null){
                    _raiz = ult_buscado.izq;
                }
                // caso general
                else if (ult_buscado.padre.izq != null && ult_buscado.padre.izq.valor.compareTo(ult_buscado.valor) == 0){
                    ult_buscado.padre.izq = ult_buscado.izq;
                }
                // caso especial: el nodo a eliminar es hijo derecho de su padre
                else{
                    ult_buscado.padre.der = ult_buscado.izq;
                }
                // actualizo el padre del hijo para que apunte al padre del nodo a eliminar
                ult_buscado.izq.padre = ult_buscado.padre;
                _cardinal--;

                // chequear minmax
                if (_cardinal == 0){
                    _min = null;
                    _max = null;
                }
                else if (elem.compareTo(_min) == 0){
                    _min = sucesorSiguiente(ult_buscado).valor;
                }
                else if (elem.compareTo(_max) == 0){
                    _max = predecesorSiguiente(ult_buscado).valor;                    
                }
            }
            // caso: el nodo a eliminar tiene 1 hijo (der)
            else if (ult_buscado.izq == null && ult_buscado.der != null){

                // actualizo el padre del nodo a eliminar para que apunte al hijo
                // caso especial: el nodo a eliminar es la raiz
                if (ult_buscado.padre == null){
                    _raiz = ult_buscado.der;
                }
                // caso general
                else if (ult_buscado.padre.izq != null && ult_buscado.padre.izq.valor.compareTo(ult_buscado.valor) == 0){
                    ult_buscado.padre.izq = ult_buscado.der;
                }
                // caso especial: el nodo a eliminar es hijo derecho de su padre
                else{
                    ult_buscado.padre.der = ult_buscado.der;
                }
    
                ult_buscado.der.padre = ult_buscado.padre;
                _cardinal--;

                // chequear minmax
                if (_cardinal == 0){
                    _min = null;
                    _max = null;
                }
                else if (elem.compareTo(_min) == 0){
                    _min = sucesorSiguiente(ult_buscado).valor;
                }
                else if (elem.compareTo(_max) == 0){
                    _max = predecesorSiguiente(ult_buscado).valor;                    
                }
            }
            // caso 2 hijos
            else if (ult_buscado.izq != null && ult_buscado.der != null){
                // lo reemplazo por el valor de su sucesor y borro a su sucesor.
                Nodo suc = sucesorAbajoSuyo(ult_buscado);
                T valor_sucesor = suc.valor;
                eliminar(valor_sucesor);
                ult_buscado.valor = valor_sucesor;

                // chequear minmax
                if (_cardinal == 0){
                    _min = null;
                    _max = null;
                }
                else if (elem.compareTo(_min) == 0){
                    _min = sucesorSiguiente(ult_buscado).valor;
                }
                else if (elem.compareTo(_max) == 0){
                    _max = predecesorSiguiente(ult_buscado).valor;                    
                }
            }
        }
        // si ult_buscado != elem no hago nada, no esta elem en el arbol
    }

    private Nodo predecesorSiguiente(Nodo act){
        // Dado un nodo act, devuelve el valor del predecesor de act.
        // Si act no tiene predecesor, devuelve null.
        // Si act es el minimo, devuelve el null. Esta funcion nunca se llamara cuando act es el minimo

       if (act.izq != null){
            return maximoAux(act.izq, act);
        }
        else if (act.padre != null){
            Nodo nPath = new Nodo(act.valor);
            nPath.padre = act.padre;
            while (nPath.padre != null && nPath.valor.compareTo(act.valor) >= 0){
                nPath = nPath.padre;
            }
            return nPath;
        }
        // act es el minimo
        else {
            return null;
        }
    }

    private Nodo maximoAux(Nodo actual, Nodo maxVisto){
        if (maxVisto == null){
            return null;
        }
        else if (actual.valor.compareTo(maxVisto.valor) > 0){
            if (actual.der != null){
                return maximoAux(actual.der, actual);
            }
            else {
                return actual;
            }
        }
        else {
            if (actual.der != null){
                return maximoAux(actual.der, maxVisto);
            }
            else {
                return maxVisto;
            }
        }
    }

    private Nodo sucesorAbajoSuyo(Nodo actual){
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
        if (sucesorAbajoSuyo(act) != null){
            return sucesorAbajoSuyo(act);
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