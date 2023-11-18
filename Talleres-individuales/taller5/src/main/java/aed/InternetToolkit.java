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
        IPv4Address[] res = new IPv4Address[ipv4.length];

        int i = 0;
        for (String strval : ipv4){
            IPv4Address t = new IPv4Address(strval);
            res[i] = t;
            i++;
        }

        // ordeno 4 veces, una para cada digito
        for(int j=3; j>=0; j--){ 
            // usar insertion sort con [octeto]
            insertionSort(res, j);
        }

        return res;

    }

    private IPv4Address[] insertionSort(IPv4Address[] arr, int digito){
        int n = arr.length;
        for (int i=1; i<n;i++){
            // chequeo que el elem actual sea mayor al previo
            int j = i-1;

            // mientras sea menor al ultimo visto
            while(j>=0 && arr[j].getOctet(digito) > arr[j+1].getOctet(digito)){
                IPv4Address aux = new IPv4Address(arr[j+1].toString());
                arr[j+1] = arr[j];
                arr[j] = aux;
                j--;
            }
        }

        return arr;
    }
}