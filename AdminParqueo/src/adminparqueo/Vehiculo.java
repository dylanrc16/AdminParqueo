/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminparqueo;

/**
 *
 * @author dylnr
 */
public class Vehiculo {
    public static final String LIVIANO = "LIVIANO";
    public static final String GRANDE = "GRANDE";
    public static final String MOTO = "MOTO";
    public static final String BICICLETA = "BICICLETA";
    
    private String tipo;
    private String placa;
    private String descripcion;
    private Movimiento[] movimientos;
    private int contadorMovimientos;

    public Vehiculo(String tipo, String placa, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.placa = placa;
        this.movimientos = new Movimiento[100];
        
    }
    
    
    public String getID(){
        if (BICICLETA.equals(tipo)){
            return descripcion;
        }
        else{
            return placa;
        }
    }
    
    
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Movimiento[] getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimiento[] movimientos) {
        this.movimientos = movimientos;
    }

    public int getContadorMovimientos() {
        return contadorMovimientos;
    }

    public void setContadorMovimientos(int contadorMovimientos) {
        this.contadorMovimientos = contadorMovimientos;
    }
    
    
    
    
    
    
    
    
}
