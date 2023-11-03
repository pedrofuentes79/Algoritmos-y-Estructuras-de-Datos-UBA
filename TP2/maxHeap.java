package aed;
import nodoDHont.nodoDHont;

public class maxHeap<Nodo>{

    private Nodo[] elementos;
    private int posProximo; 
    private int longitud;

    public maxHeap(int P){
        // Armamos un ArrayHeap con los elementos del array
        // HeapSort
        this.elementos = new NodoDHont[P];
        this.longitud = P;
        this.posProximo = 0;
    }

    public Insertar(NodoDHont v){
        // Lo usamos para ingresar los partidos al heap
        this.elementos[this.posProximo] = v;
        this.posProximo++;
        Subir();
    }

    public int Dividir(){
        // Lo usamos para obtener los escaños por partidos,
        // Se le asigna una banca al partido de la raiz del heap y se reordena el heap
        // actualizando el cociente de la raiz (una division D'Hont más)

        // Dividimos la raiz 
        Nodo root0 = this.elementos[0];
        this.elementos[0].cocientes = this.elementos[0].cocientes * this.elementos[0].vecesDividido/ this.elementos[0].vecesDividido+1;
        this.elementos[0].vecesDividido++;

        // Reordenamos el heap (si es necesario)
        Bajar();

        // Devolvemos el idPartido de la raíz antes de la division
        return root0.idPartido;        
    }

    public void Subir(){
        // Esta funcion solo funciona cuando se le pide subir el ultimo elemento agregado
        int pos = this.posProximo-1;
        int posPadre = (pos-1)/2;

        while(pos > 0 && this.elementos[posPadre].cocientes < this.elementos[pos].cocientes){
            // Swap con el padre
            Nodo aux = this.elementos[posPadre];
            this.elementos[posPadre] = this.elementos[pos];
            this.elementos[pos] = aux;
            // Actualizamos posiciones
            pos = posPadre;
            posPadre = (pos-1)/2;
        }
    }

    public void Bajar(){
        // Esta funcion solamente baja la raiz hasta su posicion correspondiente en el heap
        int pos = 0;
        int posHijoIzq = 2*pos+1;
        int posHijoDer = 2*pos+2;

        while (!esHoja(pos) && tieneMenorPrioridad(pos))
        {
            // Hacemos un swap con el hijo que tenga mayor prioridad
            if(this.elementos[posHijoIzq].cocientes > this.elementos[posHijoDer].cocientes){
                // Swap con el hijo izquierdo
                Nodo aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                // Actualizamos posiciones
                pos = posHijoIzq;
            }
            else{
                // Swap con el hijo derecho
                Nodo aux = this.elementos[posHijoDer];
                this.elementos[posHijoDer] = this.elementos[pos];
                this.elementos[pos] = aux;
                // Actualizamos posiciones
                pos = posHijoDer;
 
            }
            posHijoIzq = 2*pos+1;
            posHijoDer = 2*pos+2;
        }
    }

    private boolean esHoja(int pos){
        return this.elementos[2*pos+1] != null;
    }

    private boolean tieneMenorPrioridad(int pos){
        // Alguno de sus hijos tiene mas prioridad
        // Obs: Esto no se va a indeterminar (se va de rango) porque esHoja() chequea que el hijo izquierdo exista
        int posHijoIzq = 2*pos+1;
        int posHijoDer = 2*pos+2;
        return this.elementos[pos].cocientes < this.elementos[posHijoIzq].cocientes || 
               this.elementos[pos].cocientes < this.elementos[posHijoDer].cocientes;
    }
}

