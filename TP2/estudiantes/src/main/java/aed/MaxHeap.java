// TO DO:

// Validar método esHoja
// Validar actualizarPOsicion en BajarRaiz
// Validar que funque todo jejee

package aed;
import aed.NodoDHont;

public class MaxHeap {

    // Atributos privados del Heap
    private NodoDHont[] elementos;
    private int len;
    private int posProximo; 

    public MaxHeap(int P){
        // Constructor [1] del MaxHeap: Inicializa un heap vacío
        // Complejidad: O(1)
        this.elementos = new NodoDHont[P];
        this.len = P;
        this.posProximo = 0;
    }

    public MaxHeap(int[] s){
        // Constructor [2] del MaxHeap:   
        // Recibe una lista de votos por partido a partir de la cual arma
        // un heap como array de nodos, comparados por su atributo nodo.cociente
        // Complejidad: O(P)

        this.posProximo = 0;

        // Busco los votos totales: O(P)
        int votosTotalesDelDistrito = 0;
        for (int i=0; i<s.length; i++)
            votosTotalesDelDistrito += s[i];

        // Busco la cantidad de elementos que pasan el umbral (excluyo votos en blanco): O(P)
        int cantElementosValidos = 0;
        for (int i=0; i<s.length-1; i++){
            if (s[i] > votosTotalesDelDistrito*0.03)
                cantElementosValidos++;
        }
        this.len = cantElementosValidos;
        
        // Inicializamos los elementos del heap como un array de $len nodos
        this.elementos = new NodoDHont[cantElementosValidos];

        // Agregamos como nodo a todos los partidos que pasan el umbral: O(P)
        for (int i = 0; i < s.length - 1; i++) {
            if (s[i] > votosTotalesDelDistrito*0.03) {
                this.elementos[this.posProximo] = new NodoDHont(i, s[i]);
                this.posProximo++;
            }
        }

        // Reordenamos el array $elementos a partir del Algoritmo de Floyd: O(P)
        for (int i = (posProximo / 2) - 1; i >= 0; i--)
            siftDown(i); 
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

    public int proximaBanca(){
        // Método para obtener el partido al cual asignar la proxima banca.
        // Se asigna la banca al partido de la raiz del heap y se reordena el heap
        // actualizando el cociente de la raiz (una division D'Hont más)
        // Complejidad: O(log P)

        // Obtenemos el idPartido de la raiz y le asignamos un escaño mas
        int res = this.elementos[0].idPartido;
        this.elementos[0].escañosAsignados++;

        // Actualizamos el cociente de la raíz
        this.elementos[0].cociente = this.elementos[0].votosOriginales / (this.elementos[0].escañosAsignados + 1);

        // Reordenamos el heap: O(log P)
        BajarRaiz();

        return res;        
    }

    public void BajarRaiz(){
        // Método para reubicar la raíz luego de haber sido dividida tras la asignacion de una banca
        // Complejidad: O(log P)

        int pos = 0;
        int posHijoIzq = 2*pos+1;
        int posHijoDer = 2*pos+2;

        while (pos < this.len && !esHoja(pos) && tieneUnHijoMayor(pos)){
            // Intercambiamos posicion con el hijo de mayor prioridad
            // Vemos cada caso...

            // [1] Caso tiene solamente hijo izquierdo e izq>padre
            if (posHijoDer >= this.len){ // or this.elementos[posHijoDer] == null
                // Intercambiamos de posicion al hijo izquierdo con el padre
                NodoDHont aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                pos = posHijoIzq;
                // ¿Por qué tenemos que hace esto aca tmb?
                posHijoIzq = 2*pos+1;
                posHijoDer = 2*pos+2;
            }          
            // [2] Caso tiene ambos hijos y el izq>der
            else if(this.elementos[posHijoIzq].cociente > this.elementos[posHijoDer].cociente){
                // Intercambiamos de posicion al hijo izquierdo con el padre
                NodoDHont aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                pos = posHijoIzq;
            }
            // [3] Caso tiene ambos hijos y el der>=izq
            else{
                // Intercambiamos posicion al hijo derecho con el padre
                NodoDHont aux = this.elementos[posHijoDer];
                this.elementos[posHijoDer] = this.elementos[pos];
                this.elementos[pos] = aux;
                pos = posHijoDer;
            }

            // Actualizamos la posicion de los hijos
            posHijoIzq = 2*pos+1;
            posHijoDer = 2*pos+2;
        }
    }

    private boolean esHoja(int pos){
        // Complejidad: O(1)
        // Es hoja si no tiene hijo izquierdo (VALIDAR)
        return 2*pos+1 >= this.len; // or 2*pos+1 == null
    }

    private boolean tieneUnHijoMayor(int pos){
        // Devuelve True sii alguno de sus hijos tiene mas prioridad
        // Complejidad: O(1)

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