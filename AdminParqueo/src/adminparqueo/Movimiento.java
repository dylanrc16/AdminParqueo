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
    private double horasCobradas;
    private double monto;

    public Movimiento(LocalDateTime entrada) {
        this.entrada = entrada;
        this.salida = null;
        this.horasCobradas = 0;
        this.monto = 0;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSalida() {
        return salida;
    }

    public double getHorasCobradas() {
        return horasCobradas;
    }

    public double getMonto() {
        return monto;
    }

    public boolean estaActivo() {
        return salida == null;
    }

    public double horasTranscurridasHasta(LocalDateTime momento) {
        long minutos = Duration.between(entrada, momento).toMinutes();

        if (minutos < 0) {
            minutos = 0;
        }

        return minutos / 60.0;
    }

    public double calcularHorasCobradas(LocalDateTime momento) {
        long minutos = Duration.between(entrada, momento).toMinutes();

        if (minutos <= 0) {
            return 0;
        }

        long horasCompletas = minutos / 60;
        long minutosSobrantes = minutos % 60;

        if (minutosSobrantes == 0) {
            return horasCompletas;
        }

        if (minutosSobrantes <= 30) {
            return horasCompletas + 0.5;
        }

        return horasCompletas + 1;
    }

    public void cerrar(LocalDateTime salida, double tarifaPorHora) {
        this.salida = salida;
        this.horasCobradas = calcularHorasCobradas(salida);
        this.monto = horasCobradas * tarifaPorHora;
    }
}
    
    

