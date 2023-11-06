package aed;
import aed.NodoDHont;

// pasa el umbral si supera el 3% de los votos

public class MaxHeap {

    private NodoDHont[] elementos;
    private int posProximo; 
    private int len;
    private int votosTotalesDelDistrito;

    // armar un constructor que devuelva un heap con todos 0
    // armar un constructor que devuelva un heap con los elementos de un array


    public MaxHeap(int[] s){
        // Array2Heap
        // s es de longitud P-1 porque le sacamos los votos en blanco
        // este heap tendrá solo a los elementos que pasen el umbral?
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
        this.len = cantElementosValidos;
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
        if (left < posProximo && elementos[left].cociente > elementos[largest].cociente) {
            largest = left;
        }

        // si existe el hijo derecho y es mayor que el padre
        if (right < posProximo && elementos[right].cociente > elementos[largest].cociente) {
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
        // Armo un heap vacío de tamaño P
        this.elementos = new NodoDHont[P];
        this.len = P;
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

            // chequeo que la raiz pase el umbral???

            // Obtengo el idPartido de la raiz
            int res = this.elementos[0].idPartido;

            // Le asigno un escaño más a la raíz
            this.elementos[0].escañosAsignados++;

            // Actualizo el cociente de la raíz
            this.elementos[0].cociente = this.elementos[0].votosOriginales / (this.elementos[0].escañosAsignados + 1);

            // Reordenamos el heap ==> O(log(P))
            Bajar();

            return res;        
        }


    public void Subir(){
        // Esta funcion solo funciona cuando se le pide subir el ultimo elemento agregado
        int pos = this.posProximo-1;
        int posPadre = (pos-1)/2;

        while(pos > 0 && this.elementos[posPadre].cociente < this.elementos[pos].cociente){
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

        while (pos < this.len && !esHoja(pos) && tieneUnHijoMayor(pos)){
            // Hacemos un swap con el hijo que sea mayor

            // caso tiene solamente hijo izquierdo e izq>padre
            if (posHijoDer >= this.len){
                // Swap con el hijo izquierdo
                NodoDHont aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                // Actualizamos posiciones
                pos = posHijoIzq;
                posHijoIzq = 2*pos+1;
                posHijoDer = 2*pos+2;
                continue;
            }
            // caso tiene ambos hijos y el izq>der
            else if(this.elementos[posHijoIzq].cociente > this.elementos[posHijoDer].cociente){
                // Swap con el hijo izquierdo
                NodoDHont aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                // Actualizamos posiciones
                pos = posHijoIzq;
            }
            // caso tiene ambos hijos y el der>=izq
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
        return 2*pos+1 >= this.len;
    }

    private boolean tieneUnHijoMayor(int pos){
        // Alguno de sus hijos tiene mas prioridad
        int posHijoDer = 2*pos+2;
        int posHijoIzq = 2*pos+1;

        if (posHijoDer < this.len){
            return this.elementos[pos].cociente < this.elementos[posHijoIzq].cociente || 
                   this.elementos[pos].cociente < this.elementos[posHijoDer].cociente;
        }
        else{
            return this.elementos[pos].cociente < this.elementos[posHijoIzq].cociente;
        }
    }
}