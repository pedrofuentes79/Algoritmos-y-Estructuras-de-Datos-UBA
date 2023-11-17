package aed;

public class InternetToolkit {
    public InternetToolkit() {
    }

    public Fragment[] tcpReorder(Fragment[] fragments) {
        // Uso insertion Sort
        // en caso promedio es O(n) porque un elemento está desordenado con probabilidad 0.01,
        // y no se tiene que mover mucho ese elemento para estar ordenado

        int n = fragments.length;
        for (int i=1; i<n;i++){
            // chequeo que el elem actual sea mayor al previo
            int j = i-1;

            

            // mientras sea menor al ultimo visto
            while(j>=0 && fragments[j].compareTo(fragments[j+1]) == 1){
                Fragment aux = new Fragment(fragments[j+1]._id);
                fragments[j+1] = fragments[j];
                fragments[j] = aux;
                j--;
            }
        }

        return fragments;

    }

    public Router[] kTopRouters(Router[] routers, int k, int umbral) {

        Router[] res = new Router[k];

        // Array2Heap by Floyd
        HeapSort heap = new HeapSort(routers, k, umbral);

        for(int i=0; i<k; i++){
            Router elem = heap.getNext();

            res[i] = elem;
                    
        }

        return res;
    }

    public IPv4Address[] sortIPv4(String[] ipv4) {
        // IMPLEMENTAR
        return null;
    }

}
