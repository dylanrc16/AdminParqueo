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

public class Parqueo {

    private Vehiculo[] espacios;
    private Vehiculo[] vehiculos;
    private int cantidadVehiculos;

    public Parqueo() {

        // 0-24 = carros
        // 25-34 = motos y bicicletas
        espacios = new Vehiculo[35];

        vehiculos = new Vehiculo[200];

        cantidadVehiculos = 0;
    }

    public Vehiculo[] getEspacios() {
        return espacios;
    }

    public Vehiculo[] getVehiculos() {
        return vehiculos;
    }

    public int getCantidadVehiculos() {
        return cantidadVehiculos;
    }

    public boolean ingresar(String tipo,String placa,String descripcion,int cantidadEspacios) {

        Vehiculo vehiculo;
        if (!tipo.equals(Vehiculo.BICICLETA)) {
            placa = placa.trim().toUpperCase(); 
            vehiculo = buscarPorPlaca(placa);
            
            if (vehiculo != null
                    && vehiculo.estaEnParqueo()) {
                return false;
            }
        }   
        else{
            vehiculo = null;
        }
        int inicio;

        if (tipo.equals(Vehiculo.MOTO) || tipo.equals(Vehiculo.BICICLETA)) {
            inicio = buscarLibre(25, 34);
            cantidadEspacios = 1;

        } else if (tipo.equals(Vehiculo.GRANDE)) {

            if (cantidadEspacios <= 0) {
                return false;
            }
            inicio = buscarContiguos(cantidadEspacios);
        } 
        else {
            inicio = buscarLibre(0, 24);
            cantidadEspacios = 1;
        }
        if (inicio == -1) {
            return false;
        }
        if (vehiculo == null) {

            vehiculo = new Vehiculo(tipo,placa,descripcion);
            vehiculos[cantidadVehiculos] = vehiculo;
            cantidadVehiculos++;
        }

        int[] lugares = new int[cantidadEspacios];

        for (int i = 0; i < cantidadEspacios; i++){
            lugares[i] = inicio + i;}

        if (!vehiculo.entrar(LocalDateTime.now(),lugares)) {

            return false;
        }

        for (int i = 0; i < lugares.length; i++) {
            espacios[lugares[i]] = vehiculo;
        }

        return true;
    }

    public Vehiculo buscarPorPlaca(String placa) {

        for (int i = 0; i < cantidadVehiculos; i++) {

            if (vehiculos[i].getPlaca() != null
                    && vehiculos[i].getPlaca().equalsIgnoreCase(placa)) {

                return vehiculos[i];
            }
        }

        return null;
    }

    public Vehiculo buscarActivoPorPlaca(String placa) {

        Vehiculo vehiculo = buscarPorPlaca(placa.trim().toUpperCase());

        if (vehiculo != null && vehiculo.estaEnParqueo()) {

            return vehiculo;
        }
        return null;
    }

    public Vehiculo buscarPorPosicion(String posicion) {

        int indice = convertirPosicion(posicion);

        if (indice == -1) {
            return null;
        }

        return espacios[indice];
    }

    public Vehiculo[] buscarBicicletas(String texto) {

        Vehiculo[] encontradas = new Vehiculo[10];

        int contador = 0;

        String[] palabras = texto.toLowerCase().trim().split("\\s+");

        for (int i = 0; i < cantidadVehiculos; i++) {

            Vehiculo vehiculo = vehiculos[i];

            if (vehiculo.getTipo().equals(Vehiculo.BICICLETA) && vehiculo.estaEnParqueo()) {

                String descripcion = vehiculo.getDescripcion().toLowerCase();

                for (int j = 0; j < palabras.length; j++) {

                    if (descripcion.contains(palabras[j])) {

                        if (contador < encontradas.length) {

                            encontradas[contador] = vehiculo;

                            contador++;
                        }
                        break;
                    }
                }
            }
        }
        return encontradas;
    }
    public void sacar(Vehiculo vehiculo) {

        if (vehiculo == null || !vehiculo.estaEnParqueo()) {
            return;
        }

        int[] lugares = vehiculo.getEspaciosActuales();

        if (lugares != null) {

            for (int i = 0; i < lugares.length; i++) {

                espacios[lugares[i]] = null;
            }
        }

        vehiculo.salir(LocalDateTime.now(),tarifa(vehiculo));

        vehiculo.liberarEspacios();
    }

    public double tarifa(Vehiculo vehiculo) {

        if (vehiculo.getTipo().equals(Vehiculo.MOTO)|| vehiculo.getTipo().equals(Vehiculo.BICICLETA)) {

            return 800;
        }
        return 1000;
    }

    private int buscarLibre(int inicio,int fin) {

        for (int i = inicio;i <= fin;i++) {

            if (espacios[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private int buscarContiguos(int cantidad) {

        int posicion = buscarEnFila(0,9,cantidad );

        if (posicion != -1) {
            return posicion;
        }

        posicion = buscarEnFila( 10, 19, cantidad );

        if (posicion != -1) {
            return posicion;
        }

        return buscarEnFila( 20, 24, cantidad );
    }

    private int buscarEnFila(int inicio, int fin, int cantidad) {

        for (int i = inicio; i <= fin - cantidad + 1; i++) {

            boolean libres = true;

            for (int j = 0; j < cantidad;j++) {

                if (espacios[i + j] != null) {

                    libres = false;
                    break;
                }
            }
            if (libres) {
                return i;
            }
        }
        return -1;
    }

    public int convertirPosicion(String posicion) {

    posicion = posicion.trim().toUpperCase();

    try {

        // Espacios de motos y bicicletas
        if (posicion.startsWith("M")) {

            String textoNumero = posicion.substring(1);
            int numero = Integer.parseInt(textoNumero);

            if (numero >= 1 && numero <= 10) {
                return 24 + numero;
            }

        } else {

            // Espacios normales
            int numero = Integer.parseInt(posicion);

            if (numero >= 1 && numero <= 25) {
                return numero - 1;
            }
        }

    } catch (NumberFormatException e) {
        return -1;
    }

    return -1;
}
    public String nombrePosicion(
            int indice) {
        if (indice < 25) {
            return String.valueOf(indice + 1 );
        }
        return "M" + (indice - 24);
    }
}