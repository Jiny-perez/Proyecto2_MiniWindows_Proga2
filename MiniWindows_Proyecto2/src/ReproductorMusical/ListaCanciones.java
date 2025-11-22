package ReproductorMusical;

import java.util.ArrayList;

/**
 *
 * @author marye
 */
public class ListaCanciones {

    private final ArrayList<Cancion> canciones;

    public ListaCanciones() {
        this.canciones = new ArrayList<>();
    }

    public void addListaCanciones(Cancion cancion) {
        canciones.add(cancion);
    }

    public Cancion getPrimeraCancion() {
        if (canciones.isEmpty()) {
            return null;
        }
        return canciones.get(0);
    }

    public int tamanio() {
        return canciones.size();
    }

    public Cancion obtenerCancion(int index) {
        if (index < 0 || index >= canciones.size()) {
            return null;
        }
        return canciones.get(index);
    }
}
