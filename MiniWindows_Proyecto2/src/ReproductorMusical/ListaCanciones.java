package ReproductorMusical;

import java.util.ArrayList;

/**
 *
 * @author marye
 */
public class ListaCanciones {
     private ArrayList<Cancion> canciones;

    public ListaCanciones() {
        this.canciones = new ArrayList<>();
    }

    public boolean isEmpty() {
        return canciones.isEmpty();
    }

    public void addListaCanciones(Cancion cancion) {
        canciones.add(cancion);
    }

    public Cancion getPrimeraCancion() {
        return isEmpty() ? null : canciones.get(0);
    }
    
    public int tamnio() {
        return canciones.size();
    }
    
    public Cancion obtenerCancion(int index) {
        if (index < 0 || index >= canciones.size()) {
            return null;
        }
        return canciones.get(index);
    }
}
