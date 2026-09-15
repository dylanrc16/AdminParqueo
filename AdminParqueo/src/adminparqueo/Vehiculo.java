/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminparqueo;


/**
 *
 * @author dylnr
 */
import java.time.LocalDateTime;

public class Vehiculo {

    public static final String LIVIANO = "LIVIANO";
    public static final String GRANDE = "GRANDE";
    public static final String MOTO = "MOTO";
    public static final String BICICLETA = "BICICLETA";

    private String tipo;
    private String placa;
    private String descripcion;

    private Movimiento[] movimientos;
    private int cantidadMovimientos;

    private int[] espaciosActuales;

    public Vehiculo(String tipo, String placa, String descripcion) {
        this.tipo = tipo;
        this.placa = placa;
        this.descripcion = descripcion;

        movimientos = new Movimiento[50];
        cantidadMovimientos = 0;

        espaciosActuales = null;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Movimiento[] getMovimientos() {
        return movimientos;
    }

    public int getCantidadMovimientos() {
        return cantidadMovimientos;
    }

    public int[] getEspaciosActuales() {
        return espaciosActuales;
    }

    public String getIdentificador() {
        if (tipo.equals(BICICLETA)) {
            return descripcion;
        }

        return placa;
    }

    public Movimiento getMovimientoActual() {
        if (cantidadMovimientos == 0) {
            return null;
        }

        return movimientos[cantidadMovimientos - 1];
    }

    public boolean estaEnParqueo() {
        Movimiento actual = getMovimientoActual();

        return actual != null && actual.estaActivo();
    }

    public boolean entrar(LocalDateTime entrada, int[] espacios) {

        if (cantidadMovimientos >= movimientos.length) {
            return false;
        }

        movimientos[cantidadMovimientos] =
                new Movimiento(entrada);

        cantidadMovimientos++;

        espaciosActuales = espacios;

        return true;
    }

    public void salir(LocalDateTime salida, double tarifa) {

        Movimiento actual = getMovimientoActual();

        if (actual != null && actual.estaActivo()) {
            actual.cerrar(salida, tarifa);
        }
    }

    public void liberarEspacios() {
        espaciosActuales = null;
    }
}
    
    
    
    
    
    
    

