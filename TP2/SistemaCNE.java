package aed;
public class SistemaCNE {
    // Completar atributos privados
    // meter estos dos en SistemaCNE
    private int P;
    private int D;
    private String[] nombrePartido;
    private String[] nombreDistrito;
    private int[] diputadosEnDisputa;
    private int[][] votosDiputados;
    private int[] votosPresidenciales;
    private boolean hayBallotage;
    private int[] mesasPorDistritos;
    private maxHeap<NodoDHont> [] resultadosPorDistritos;

    public class VotosPartido{
        private int presidente;
        private int diputados;
        VotosPartido(int presidente, int diputados){this.presidente = presidente; this.diputados = diputados;}
        public int votosPresidente(){return presidente;}
        public int votosDiputados(){return diputados;}
    }

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public String nombrePartido(int idPartido) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public String nombreDistrito(int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int diputadosEnDisputa(int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public String distritoDeMesa(int idMesa) {
        throw new UnsupportedOperationException("No implementada aun");
    }
    // O(3P + log(D))
    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        // sumo los votos presidenciales : O(P)

        // busco el distrito de la mesa con la busqueda binaria  : O(log(D))


        // Agarro el array correspondiente a mi distrito, le hago una copia y le sumo los votos correspondientes : O(P)
        // luego, le hago heapify  y "piso" el heap anterior : O(P)
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int votosPresidenciales(int idPartido) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int[] resultadosDiputados(int idDistrito){
        int res = new int[this.P];
        int bancasDisputa = diputadosEnDisputa(idDistrito); // O(1)
        for (int i=0; i<bancasDisputa; i++){
            int idPartido = resultadosPorDistritos[idDistrito].Dividir(); // O(log(P))
            res[idPartido] += 1;
        }
        return res;
    }

    public boolean hayBallotage(){
        throw new UnsupportedOperationException("No implementada aun");
    }
}

