/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistema;

import Modelo.Usuario;
import Excepciones.*;

/**
 *
 * @author najma
 */
public class MiniWindowsClass {
    
    // atributos
    private SistemaArchivos sistemaArchivos;
    private GestorUsuarios gestorUsuarios;
    private static MiniWindowsClass instancia;
    
    // constructor privado
    private MiniWindowsClass() {
        this.gestorUsuarios = new GestorUsuarios();
        this.sistemaArchivos = new SistemaArchivos();
        
        if (!sistemaArchivos.cargar()) {
            try {
                sistemaArchivos.crearCarpetaUsuario("admin");
            } catch (ArchivoNoValidoException e) {
                System.err.println("Error al crear carpeta admin: "+e.getMessage());
            }
        }
    }
    
    // obtener instancia única del sistema (Singleton)
    public static MiniWindowsClass getInstance() {
        if (instancia == null) {
            instancia = new MiniWindowsClass();
        }
        return instancia;
    }
    
    // iniciar sesión en el sistema
    public Usuario login(String username, String password) 
            throws UsuarioNoEncontradoException, ArchivoNoValidoException, PermisosDenegadosException {
        
        Usuario usuario = gestorUsuarios.login(username, password);
        sistemaArchivos.establecerUsuario(usuario);
        return usuario;
    }
    
    // cerrar sesión
    public void logout() {
        gestorUsuarios.logout();
        guardarSistema();
    }
    
    // crear nuevo usuario en el sistema
    public Usuario crearUsuario(String nombreCompleto, String username, String password) 
            throws ArchivoNoValidoException {
        
        if (gestorUsuarios.haySesionActiva() && !gestorUsuarios.getUsuarioActual().esAdmin()) {
            throw new ArchivoNoValidoException("Solo el administrador puede crear usuarios");
        }
        
        Usuario nuevoUsuario = gestorUsuarios.crearUsuario(nombreCompleto, username, password);
        
        sistemaArchivos.crearCarpetaUsuario(username);
        
        guardarSistema();
        
        return nuevoUsuario;
    }
    
    // getters
    public SistemaArchivos getSistemaArchivos() {
        return sistemaArchivos;
    }
    
    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }
    
    public boolean haySesionActiva() {
        return gestorUsuarios.haySesionActiva();
    }
    
    public Usuario getUsuarioActual() {
        return gestorUsuarios.getUsuarioActual();
    }
    
    public void guardarSistema() {
        sistemaArchivos.guardar();
        gestorUsuarios.guardarUsuarios();
    }
    
    // reiniciar el sistema
    public void reiniciarSistema() {
        java.io.File archivoSistema = new java.io.File("sistema_archivos.sop");
        java.io.File archivoUsuarios = new java.io.File("usuarios.sop");
        
        if (archivoSistema.exists()) archivoSistema.delete();
        if (archivoUsuarios.exists()) archivoUsuarios.delete();
        
        instancia = null;
        getInstance();
    }
}