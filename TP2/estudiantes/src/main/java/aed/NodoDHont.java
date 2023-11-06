package aed;

public class NodoDHont{
    public int idPartido;
    public int votosOriginales;
    public int cociente;
    public int escañosAsignados;

    public NodoDHont(int idPartido, int votos){
        this.idPartido = idPartido;
        this.votosOriginales = votos;
        this.cociente = votos;
        this.escañosAsignados = 0;
    }

}

