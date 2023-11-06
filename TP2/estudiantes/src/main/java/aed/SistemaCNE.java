package aed;
public class SistemaCNE {
    private int P;
    private int D;
    private String[] nombrePartido;
    private String[] nombreDistrito;
    private int[] diputadosEnDisputa;
    private int[][] votosDiputados;
    private int[] votosPresidenciales;
    private boolean hayBallotage;
    private int[] mesasPorDistritos;
    private MaxHeap[] resultadosPorDistritos;

    // variables de escaños asignados
    private boolean[] fueCalculado;
    private int[][] escañosAsignados;

    public class VotosPartido{
        // ambos >= 0
        private int presidente;
        private int diputados;
        VotosPartido(int presidente, int diputados){this.presidente = presidente; this.diputados = diputados;}
        public int votosPresidente(){return presidente;}
        public int votosDiputados(){return diputados;}
    }

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        this.nombrePartido = nombresPartidos;
        this.nombreDistrito = nombresDistritos;
        this.diputadosEnDisputa = diputadosPorDistrito;
        this.P = nombresPartidos.length; // P incluye los votos en blanco
        this.D = nombresDistritos.length;
        this.votosDiputados = new int[D][P];
        this.votosPresidenciales = new int[P];
        // que pasa cuando hay 0 votos para cada partido?
        this.hayBallotage = false;

        this.mesasPorDistritos = ultimasMesasDistritos;
        
        // construir la matriz de MaxHeaps (de long P) inicializada en 0
        this.resultadosPorDistritos = new MaxHeap[D];

        // construir el array de fueCalculado (todos en false) y el array de escañosAsignados (todos en 0)
        this.fueCalculado = new boolean[D];
        this.escañosAsignados = new int[D][P-1];


        // inicializar los MaxHeaps en 0, con longitud P-1 así saco los votos en blanco
        for (int i=0; i<D; i++){
            this.resultadosPorDistritos[i] = new MaxHeap(P-1);
        }
    }

    public String nombrePartido(int idPartido) {
        return nombrePartido[idPartido];
    }

    public String nombreDistrito(int idDistrito) {
        return nombreDistrito[idDistrito];
    }

    public int diputadosEnDisputa(int idDistrito) {
        return diputadosEnDisputa[idDistrito];
    }

    public int idDistritoMesa(int idMesa){
        // busqueda binaria en mesasPorDistritos, que ya esta ordenada
        // requiere que el idMesa sea menor a la ultima mesa del ultimo distrito
            
        int low = 0;
        int high = mesasPorDistritos.length - 1;
        
        // small check so that the list isn't out of range at the end
        if (idMesa >= mesasPorDistritos[high]) return high;
        
        while (low <= high){
            int mid = (low + high) / 2;
    
            if (idMesa < mesasPorDistritos[mid]){
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return high+1;
    }
    public String distritoDeMesa(int idMesa) {
        return nombreDistrito[idDistritoMesa(idMesa)];
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        // se pide O(P + log(D))
        // si se pide registrar una mesa después de haber calculado los escaños, esa distribucion de escaños puede estar mal
            // a menos que pueda calcular de vuelta los escaños en la complejidad pedida

        // busco el distrito de la mesa con la busqueda binaria  : O(log(D))
        int idDistrito = idDistritoMesa(idMesa);

        // sumo los votos presidenciales : O(P)
        for (int i=0; i<P; i++){
            this.votosPresidenciales[i] += actaMesa[i].votosPresidente();
            this.votosDiputados[idDistrito][i] += actaMesa[i].votosDiputados();
        }
        // chequeo si hay ballotage : O(P)
        this.hayBallotage = auxBallotage(this.votosPresidenciales);

        // Hago la copia con los votos ya sumados
        int[] votosDistrito = this.votosDiputados[idDistrito].clone(); // O(P)

        // luego, transformo ese array en un heap y "piso" el heap anterior : O(P)
        this.resultadosPorDistritos[idDistrito] = new MaxHeap(votosDistrito);        

    }

    private boolean auxBallotage(int[] votosPresidenciales) {
        int max = 0;
        int max2 = 0;
        int totalVotos = 0;
        for (int i=0; i<P; i++){
            if (votosPresidenciales[i] > max){
                max2 = max;
                max = votosPresidenciales[i];
            } else if (votosPresidenciales[i] > max2){
                max2 = votosPresidenciales[i];
            }
            totalVotos += votosPresidenciales[i];
        }
        
        if (max > totalVotos*0.45){
            return false;
        } else if ((max - max2) > 0.1*totalVotos && max > 0.4*totalVotos){
            return false;
        } else {
            return true;
        }
    }

    public int votosPresidenciales(int idPartido) {
        return votosPresidenciales[idPartido];
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        return votosDiputados[idDistrito][idPartido];
    }

    public int[] resultadosDiputados(int idDistrito){
        // se pide O(Dd * log(P))

        // hay que arreglar esto
        // el problema es que res parece cambiar a medida que se ejecuta el for más de una vez
        // inicializo en p-1 porque no quiero contar los votos en blanco
        int[] res = new int[this.P-1]; // O(P)????
        int bancasDisputa = diputadosEnDisputa(idDistrito); // O(1)

        // chequeo si ya calcule los escaños para este distrito
        if (fueCalculado[idDistrito]){
            return escañosAsignados[idDistrito];
        }
        else{
            for (int i=0; i<bancasDisputa; i++){ // O(bancasDisputa)
                int idPartido = resultadosPorDistritos[idDistrito].Dividir(); // O(log(P))
                res[idPartido] += 1;
            }
            // actualizo los escaños asignados
            this.fueCalculado[idDistrito] = true;
            this.escañosAsignados[idDistrito] = res; // se pasa por referencia? toma O(P)?
            return res;
        }

    }

    public boolean hayBallotage(){
        return this.hayBallotage;
    }
}

