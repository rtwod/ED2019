
public class LDLC <T> {

    /**
     * PROPIEDADES DE LA LISTA
     **/
    int size = 0;
    Nodo inicio;
    Nodo fin;

    LDLC() {
        inicio = null;
        fin = null;
    }

    public boolean empty() {
        if (inicio == null) return true;
        else return false;
    }

    public void insertar(T dato) {

        String nombre = "" ;
        String direccion = "" ;

        Nodo nuevo = new Nodo( nombre, direccion, dato);
        nuevo.dato = dato;


        if (inicio == null) {
            nuevo.sig = nuevo;
            nuevo.ant = nuevo;
            inicio = nuevo;
            size++;
        } else {
            Nodo fin = inicio.ant;
            nuevo.sig = inicio;
            nuevo.ant = fin;
            inicio.ant = nuevo;
            fin.sig = nuevo;
            inicio = nuevo;
            size++;
        }
    }

    public void imprimir() {
        if (!empty()) {
            Nodo p = inicio;
            do {
                System.out.print(p.dato + "-");
                p = p.sig;
            } while (p != inicio);
            System.out.println();
        }
    }

    /**
     * MÉTODO PARA IMPRIMIR EL TAMAÑO DE NUESTRA LISTA
     **/
    public void size() {
        System.out.println(" <-- " + size + " --> ");
    }

    public void delPos(int p) {
        if (inicio != null) {
            if (p > 0) {
                int cont = 1;
                Nodo aux = inicio;
                while ((aux.sig != inicio) && (cont < p)) {
                    cont++;
                    aux = aux.sig;
                }
                if (cont == 1) {
                    if (aux.sig == inicio)
                        inicio = null;
                    else {
                        Nodo ant = aux.ant;
                        ant.sig = aux.sig;
                        aux = aux.sig;
                        aux.ant = ant;
                        inicio = aux;
                    }
                } else {
                    Nodo ant = aux.ant;
                    aux.ant = null;
                    ant.sig = aux.sig;
                    aux = aux.sig;
                    aux.ant = ant;
                }
            }
        }
    }


    public int index(Nodo b) {
        Nodo aux = inicio;
        int con = 0;

        while (aux != null) {
            if (aux == b) {
                return con;
            }
            aux = aux.sig;
            con++;
        }
        return -1;
    }

    public Nodo get_cancion(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return null;
    }

    public void borrar(Nodo b) {
        if ( b == inicio ) {
            if ( size == 1 ) {
                inicio = null;
                size--;
                return;
            }
            inicio.sig.ant = null;
            inicio = inicio.sig;
            size--;
            return;
        }
        size--;
        if (b == fin) {
            fin.ant.sig = null;
            fin = fin.ant;
            return;
        }
        b.sig.ant = b.ant;
        b.sig.ant.sig = b.sig;
    }




}







