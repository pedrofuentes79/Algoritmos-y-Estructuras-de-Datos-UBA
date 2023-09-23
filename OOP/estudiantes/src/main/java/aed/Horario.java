package aed;

public class Horario {
    private int hora=0;
    private int min=0;

    public Horario(int h, int m) {
        hora = h;
        min = m;
    }

    public int hora() {
        return hora;
    }

    public int minutos() {
        return min;
    }

    @Override
    public String toString() {
        return Integer.toString(hora) + ":" + Integer.toString(min);
    }

    @Override
    public boolean equals(Object otro) {
        boolean esNull = (otro==null);
        if (esNull || otro.getClass() != this.getClass()){
            return false;
        }
        Horario otroHorario = (Horario) otro;
        return otroHorario.hora == this.hora && otroHorario.min == this.min;
    }


}
