package aed;

public class HeapSort {

    // Atributos privados del Heap
    private Router[] elementos;
    private int len;
    private int cantValidos;

    public HeapSort(Router[] s, int k, int umbral) {
        this.len = 0;
        this.cantValidos = 0;
    
        this.elementos = new Router[s.length];

        for(int i = 0; i<s.length; i++){
            if(s[i].getTrafico() > umbral){
                this.elementos[i] = s[i];
                this.len++;
                this.cantValidos++;
            } 
        }

        this.HeapifyByFloyd();
    }

    public void HeapifyByFloyd() {
        // Recibe un array de nodos y lo reordena como un heap
        // aplicando el Algoritmo de Floyd.
        // Complejidad: O(n)
        for (int i = (len / 2) - 1; i >= 0; i--) {
            floydRecursivo(i);
        }
    }

    public void floydRecursivo(int i) {
        // Swapea el nodo i con su hijo de menor prioridad

        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Si existe el hijo izquierdo y es menor que el padre
        if (left < len && elementos[left].getTrafico() > elementos[largest].getTrafico()) {
            largest = left;
        }
        // Si existe el hijo derecho y es menor que el padre
        if (right < len && elementos[right].getTrafico() > elementos[largest].getTrafico()) {
            largest = right;
        }

        // Si el menor no es el padre, hacemos swap y seguimos bajando
        if (largest != i) {
            // Intercambiamos con el hijo mayor
            Router temp = elementos[i];
            elementos[i] = elementos[largest];
            elementos[largest] = temp;
            floydRecursivo(largest);
        }
    }
    
    public Router getNext(){
        if(this.len==0){return null;}

        Router res = this.elementos[0];

        // swap con el ultimo elemento
        this.elementos[0] = this.elementos[this.len-1];
        // vuelo el ultimo elemento => reduzco len
        this.len--;

        // bajo la raiz hasta donde tiene que estar
        this.BajarRaiz();


        return res;
    }

    public void BajarRaiz() {
        // Método para reubicar la raíz luego de haber sido dividida tras la asignacion
        // de una banca
        // Complejidad: O(log P)

        int pos = 0;
        int posHijoIzq = 2 * pos + 1;
        int posHijoDer = 2 * pos + 2;

        while (pos < this.len && !esHoja(pos) && tieneUnHijoMayor(pos)) {
            // Intercambiamos posicion con el hijo de mayor prioridad
            // Vemos cada caso...
            // [1] Caso tiene solamente hijo izquierdo e izq>padre
            if (posHijoDer >= this.len) {
                // Intercambiamos de posicion al hijo izquierdo con el padre
                Router aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                pos = posHijoIzq;
            }
            // [2] Caso tiene ambos hijos y el izq>der
            else if (this.elementos[posHijoIzq].getTrafico() > this.elementos[posHijoDer].getTrafico()) {
                // Intercambiamos de posicion al hijo izquierdo con el padre
                Router aux = this.elementos[posHijoIzq];
                this.elementos[posHijoIzq] = this.elementos[pos];
                this.elementos[pos] = aux;
                pos = posHijoIzq;
            }
            // [3] Caso tiene ambos hijos y el der>=izq
            else {
                // Intercambiamos posicion al hijo derecho con el padre
                Router aux = this.elementos[posHijoDer];
                this.elementos[posHijoDer] = this.elementos[pos];
                this.elementos[pos] = aux;
                pos = posHijoDer;
            }
            // Actualizamos la posicion de los hijos
            posHijoIzq = 2 * pos + 1;
            posHijoDer = 2 * pos + 2;
        }
    }
    private boolean esHoja(int pos) {
        // Complejidad: O(1)
        // Es hoja si no tiene hijo izquierdo
        // Observacion: Esto solo es cierto porque el arrayHeap siempre está completo
        return 2 * pos + 1 >= this.len;
    }

    private boolean tieneUnHijoMayor(int pos) {
        // Devuelve True sii alguno de sus hijos tiene mas prioridad
        // Complejidad: O(1)
        int posHijoDer = 2 * pos + 2;
        int posHijoIzq = 2 * pos + 1;

        if (posHijoDer < this.len)
            return this.elementos[pos].getTrafico() < this.elementos[posHijoIzq].getTrafico() ||
                    this.elementos[pos].getTrafico() < this.elementos[posHijoDer].getTrafico();
        else
            return this.elementos[pos].getTrafico() < this.elementos[posHijoIzq].getTrafico();
    }


}
