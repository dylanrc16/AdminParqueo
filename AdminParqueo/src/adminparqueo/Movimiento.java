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
    private double monto;

    public Movimiento(LocalDateTime entrada) {
        this.entrada = entrada;
        this.salida = null;
        this.monto = 0;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSalida() {
        return salida;
    }

    public double getMonto() {
        return monto;
    }

    public boolean estaActivo() {
        return salida == null;
    }

    public double horasTranscurridasHasta(LocalDateTime momento) {
        long segundos = Duration.between(entrada, momento).getSeconds();

        if (segundos < 0) {
            segundos = 0;
        }

        return segundos / 3600.0;
    }

    public double horasCobradasHasta(LocalDateTime momento) {
        long segundos = Duration.between(entrada, momento).getSeconds();

        if (segundos <= 0) {
            return 0;
        }

        int horasCompletas = (int) (segundos / 3600);
        long segundosSobrantes = segundos % 3600;

        if (segundosSobrantes == 0) {
            return horasCompletas;
        }

        if (segundosSobrantes <= 1800) {
            return horasCompletas + 0.5;
        }

        return horasCompletas + 1.0;
    }

    public void cerrar(LocalDateTime salida, double tarifaPorHora) {
        this.salida = salida;
        this.monto = horasCobradasHasta(salida) * tarifaPorHora;
    }
}
    
    
    

