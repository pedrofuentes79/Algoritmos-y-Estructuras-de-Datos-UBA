package aed;

public class Fecha {
    private int dia=1;
    private int mes=1;

    public Fecha(int d, int m) {
        dia = d;
        mes = m;
    }

    public Fecha(Fecha fecha) {
        dia = fecha.dia;
        mes = fecha.mes;
    }

    public Integer dia() {
        return dia;
    }

    public Integer mes() {
        return mes;
    }

    public String toString() {
        return Integer.toString(dia) + "/" + Integer.toString(mes);
    }

    @Override
    public boolean equals(Object otra) {
        boolean esNull = (otra==null);
        if (esNull || otra.getClass() != this.getClass()){
            return false;
        }
        Fecha fechaOtra = (Fecha) otra;
        return fechaOtra.dia == this.dia && fechaOtra.mes == this.mes;
    }

    public void incrementarDia() {
        if (diasEnMes(mes) == dia){
            dia = 1;
            mes = (mes+1)%12;
        }
        else{
            dia++;
        }
    }

    private int diasEnMes(int mes) {
        int dias[] = {
                // ene, feb, mar, abr, may, jun
                31, 28, 31, 30, 31, 30,
                // jul, ago, sep, oct, nov, dic
                31, 31, 30, 31, 30, 31
        };
        return dias[mes - 1];
    }

}
