package aed;
import aed.NodoDHont;

// pasa el umbral si supera el 3% de los votos

public class MaxHeap{

    private NodoDHont[] elementos;
    private int posProximo; 
    private int P;
    private int votosTotalesDelDistrito;

    // armar un constructor que devuelva un heap con todos 0
    // armar un constructor que devuelva un heap con los elementos de un array


    public MaxHeap(int[] s){
        // Array2Heap
        // the array must be of length P
        this.posProximo = 0;
        this.votosTotalesDelDistrito = 0;

        // Busco los votos totales
        for (int i=0; i<s.length; i++)
            this.votosTotalesDelDistrito += s[i];

        // Busco la cantidad de elementos validos (que pasan el umbral)
        int cantElementosValidos = 0;
        for (int i=0; i<s.length; i++){
            if (s[i] > this.votosTotalesDelDistrito*0.03)
                cantElementosValidos++;
        }
        this.P = cantElementosValidos;
        this.elementos = new NodoDHont[cantElementosValidos];

        // Meto los que pasan el umbral en el array ==> O(P)
        for (int i = 0; i < s.length; i++) {
            if (s[i] > this.votosTotalesDelDistrito*0.03) {
                this.elementos[this.posProximo] = new NodoDHont(i, s[i]);
                this.posProximo++;
            }
        }

        // Algoritmo de Floyd con el array elementos ==> O(P)
        for (int i = (posProximo / 2) - 1; i >= 0; i--) {
            siftDown(i); 
        }
    }

    public boolean pasaElUmbral(int votos, int votosTotales){
        return votos > votosTotales*0.03;
    }

    public void siftDown(int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // si existe el hijo izquierdo y es mayor que el padre
        if (left < posProximo && elementos[left].cocientes > elementos[largest].cocientes) {
            largest = left;
        }

        // si existe el hijo derecho y es mayor que el padre
        if (right < posProximo && elementos[right].cocientes > elementos[largest].cocientes) {
            largest = right;
        }

        // si el mayor no es el padre, hacemos swap y seguimos bajando
        if (largest != i) {
            // Swap with the larger child
            NodoDHont temp = elementos[i];
            elementos[i] = elementos[largest];
            elementos[largest] = temp;
            siftDown(largest);
        }
    }


    public MaxHeap(int P){
        // Armamos un ArrayHeap con los elementos del array
        // HeapSort
        this.elementos = new NodoDHont[P];
        this.P = P;
        this.posProximo = 0;
    }

    public void Insertar(NodoDHont v){
        // Lo usamos para ingresar los partidos al heap
        this.elementos[this.posProximo] = v;
        this.posProximo++;
        Subir();
    }

    public int Dividir(){
        // Lo usamos para obtener los escaños por partidos,
        // Se le asigna una banca al partido de la raiz del heap y se reordena el heap
        // actualizando el cociente de la raiz (una division D'Hont más)

        // Obtengo el idPartido de la raiz
        int res = this.elementos[0].idPartido;
        // Divido la raiz
        this.elementos[0].cocientes = (this.elementos[0].cocientes * this.elementos[0].vecesDividido)/ (this.elementos[0].vecesDividido +1);
        this.elementos[0].vecesDividido++;

        // Reordenamos el heap (si es necesario) ==> O(log(P))
        Bajar();

        return res;        
    }

    public void Subir(){
        // Esta funcion solo funciona cuando se le pide subir el ultimo elemento agregado
        int pos = this.posProximo-1;
        int posPadre = (pos-1)/2;

        while(pos > 0 && this.elementos[posPadre].cocientes < this.elementos[pos].cocientes){
            // Swap con el padre
            NodoDHont aux = this.elementos[posPadre];
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

        while (pos < P && !esHoja(pos) && tieneUnHijoMayor(pos)){
            // Hacemos un swap con el hijo que tenga mayor prioridad
            System.out.println(pos);
            if(this.elementos[posHijoIzq].cocientes 
            > this.elementos[posHijoDer].cocientes){
                // Swap con el hijo izquierdo
                NodoDHont aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                // Actualizamos posiciones
                pos = posHijoIzq;
            }
            else{
                // Swap con el hijo derecho
                NodoDHont aux = this.elementos[posHijoDer];
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
        // Es hoja si no tiene hijo izquierdo
        return 2*pos+1 >= P;
    }

    private boolean tieneUnHijoMayor(int pos){
        // Alguno de sus hijos tiene mas prioridad
        int posHijoDer = 2*pos+2;
        int posHijoIzq = 2*pos+1;

        if (posHijoDer < P){
            return this.elementos[pos].cocientes < this.elementos[posHijoIzq].cocientes || 
                   this.elementos[pos].cocientes < this.elementos[posHijoDer].cocientes;
        }
        else{
            return this.elementos[pos].cocientes < this.elementos[posHijoIzq].cocientes;
        }
    }
}