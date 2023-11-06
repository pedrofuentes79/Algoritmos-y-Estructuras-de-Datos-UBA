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
    // O(3P + log(D))


    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        // busco el distrito de la mesa con la busqueda binaria  : O(log(D))
        int idDistrito = idDistritoMesa(idMesa);
        int[] votosDistrito = this.votosDiputados[idDistrito].clone(); // O(P)

        // sumo los votos presidenciales : O(P)
        for (int i=0; i<P; i++){
            this.votosPresidenciales[i] += actaMesa[i].votosPresidente();
            this.votosDiputados[idDistrito][i] += actaMesa[i].votosDiputados();
            votosDistrito[i] += actaMesa[i].votosDiputados();
        }
        // chequeo si hay ballotage : O(P)
        this.hayBallotage = auxBallotage(this.votosPresidenciales);

        
        // antes, le saco los votos en blanco, que me arruinarían la matriz de dhont ==> O(P)
        int[] votosDistritoSinBlanco = new int[P-1];
        for (int i=0; i<P-1; i++){
            votosDistritoSinBlanco[i] = votosDistrito[i];
        }

        // luego, transformo ese array en un heap y "piso" el heap anterior : O(P)
        this.resultadosPorDistritos[idDistrito] = new MaxHeap(votosDistritoSinBlanco);

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
        // hay que arreglar esto
        // el problema es que res parece cambiar a medida que se ejecuta el for más de una vez
        int[] res = new int[this.P]; // O(P)????
        int bancasDisputa = diputadosEnDisputa(idDistrito); // O(1)

        for (int i=0; i<bancasDisputa; i++){ // O(bancasDisputa)
            int idPartido = resultadosPorDistritos[idDistrito].Dividir(); // O(log(P))
            res[idPartido] += 1;
        }
        return res;
    }

    public boolean hayBallotage(){
        return this.hayBallotage;
    }
}

