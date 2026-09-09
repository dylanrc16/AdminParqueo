/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminparqueo;

/**
 *
 * @author dylnr
 */
import java.time.Duration;
import java.time.LocalDateTime;

public class Movimiento {
    private LocalDateTime entrada;
    private LocalDateTime salida;
    private int[] espacios;
    private double horasCobradas;
    private double montoFinal;

    public Movimiento(LocalDateTime entrada, int[] espacios) {
        this.entrada = entrada;
        this.espacios = espacios;
    }
    
    
    public boolean sigueActivo(){
        if (this.salida == null){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void cerrar(LocalDateTime salida, double tarifaXHora){
        this.salida = salida;
        this.horasCobradas = calcularHorasCobradas(entrada,salida);
        this.montoFinal = horasCobradas * tarifaXHora;
    }
    
    public static double calcularHorasCobradas(LocalDateTime entrada, LocalDateTime fin) {
    long segundos = Duration.between(entrada, fin).getSeconds();

    if (segundos <= 0) {
        return 0;
    }

    double horasExactas = segundos / 3600.0;
    int horasEnteras = (int) horasExactas;
    double fraccion = horasExactas - horasEnteras;

    // Si no hay fracción, cobra solamente las horas completas.
    if (fraccion < 0.000001) {
        return horasEnteras;
    }

    // Hasta media hora se cobra media hora.
    if (fraccion <= 0.5) {
        return horasEnteras + 0.5;
    }

    // Más de media hora se cobra la hora completa.
    return horasEnteras + 1.0;
    }

    
    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSalida() {
        return salida;
    }

    public void setSalida(LocalDateTime salida) {
        this.salida = salida;
    }

    public int[] getEspacios() {
        return espacios;
    }

    public void setEspacios(int[] espacios) {
        this.espacios = espacios;
    }

    public double getHorasCobradas() {
        return horasCobradas;
    }

    public void setHorasCobradas(double horasCobradas) {
        this.horasCobradas = horasCobradas;
    }

    public double getMonto() {
        return montoFinal;
    }

    public void setMonto(double monto) {
        this.montoFinal = monto;
    }
    
    
    
    
}
