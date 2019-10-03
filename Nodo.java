
public class Nodo {

    String nombre, direccion;

    Object dato;

    Nodo ant;
    Nodo sig;


    public Nodo(String nombre, String direccion, Object dato) {

        this.dato = dato;
        this.nombre = nombre;
        this.direccion = direccion;

        ant = null;
        sig = null;

    }


    public String toString() {
        return "<--" + dato + "-->";
    }

}



