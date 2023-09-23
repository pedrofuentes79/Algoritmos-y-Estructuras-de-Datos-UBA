package aed;

public class Recordatorio {
    private String msj="";
    private Fecha fecha;
    private Horario horario;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        msj = mensaje;
        this.fecha = new Fecha(fecha);
        this.horario = new Horario(horario.hora(), horario.minutos());
    }

    public Horario horario() {
        return new Horario(horario.hora(), horario.minutos());
    }

    public Fecha fecha() {
        return new Fecha(fecha);
    }

    public String mensaje() {
        return msj;
    }

    @Override
    public String toString() {
        return msj + " @ " + fecha.toString() + " " + horario.toString();
    }

}
