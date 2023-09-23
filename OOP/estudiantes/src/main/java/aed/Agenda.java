package aed;

import java.util.Vector;

public class Agenda {
    private Fecha curr;
    private Vector<Recordatorio> recs = new Vector();


    public Agenda(Fecha fechaActual) {
        curr = new Fecha(fechaActual);
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        recs.addElement(recordatorio);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(curr);
        buf.append("\n");
        buf.append("=====");
        buf.append("\n");
        for(Recordatorio r : recs){
            buf.append(r.toString());
            buf.append("\n");
        }
        return buf.toString();
    }

    public void incrementarDia() {
        curr.incrementarDia();

    }

    public Fecha fechaActual() {
        return new Fecha(curr);
    }

}
