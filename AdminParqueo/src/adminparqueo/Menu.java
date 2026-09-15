/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminparqueo;

/**
 *
 * @author dylnr
 */


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {

    private Parqueo parqueo;
    private Scanner scanner;
    private DateTimeFormatter formato;

    public Menu() {
        parqueo = new Parqueo();
        scanner = new Scanner(System.in);
        formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    public void ejecutar() {

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    ingresarVehiculo();
                    break;

                case 2:
                    consultarVehiculo();
                    break;

                case 3:
                    salidaVehiculo();
                    break;

                case 4:
                    consultarParqueo();
                    break;

                case 5:
                    consultarHistorico();
                    break;

                case 6:
                    cierreDelDia();
                    break;

                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 7);
    }

    private void mostrarMenu() {

        System.out.println("\n========= PARQUEO =========");
        System.out.println("1. Ingresar vehiculo");
        System.out.println("2. Consultar vehiculo");
        System.out.println("3. Salida de vehiculo");
        System.out.println("4. Consultar parqueo");
        System.out.println("5. Consultar historico de vehiculo");
        System.out.println("6. Cierre del dia");
        System.out.println("7. Salir");
    }

    private void ingresarVehiculo() {

        System.out.println("\n1. Liviano");
        System.out.println("2. Grande");
        System.out.println("3. Motocicleta");
        System.out.println("4. Bicicleta");

        int opcion = leerEntero("Tipo: ");

        String tipo;
        String placa = null;
        String descripcion = null;
        int cantidadEspacios = 1;

        switch (opcion) {

            case 1:
                tipo = Vehiculo.LIVIANO;
                break;

            case 2:
                tipo = Vehiculo.GRANDE;
                cantidadEspacios = leerEntero("Cuantos espacios necesita?: ");
                break;

            case 3:
                tipo = Vehiculo.MOTO;
                break;

            case 4:
                tipo = Vehiculo.BICICLETA;
                break;

            default:
                System.out.println("Tipo invalido.");
                return;
        }

        if (tipo.equals(Vehiculo.BICICLETA)) {
            descripcion = leerTexto("Descripcion: ");
        } else {
            placa = leerTexto("Placa: ");
        }

        boolean ingreso = parqueo.ingresar(tipo, placa, descripcion, cantidadEspacios);

        if (ingreso) {
            System.out.println("Vehiculo ingresado correctamente.");
        } else {
            System.out.println("No se pudo ingresar el vehiculo.");
        }
    }

    private void consultarVehiculo() {

        System.out.println("\n1. Buscar por placa");
        System.out.println("2. Buscar bicicleta por descripcion");

        int opcion = leerEntero("Opcion: ");

        if (opcion == 1) {

            String placa = leerTexto("Placa: ");
            Vehiculo vehiculo = parqueo.buscarActivoPorPlaca(placa);

            if (vehiculo == null) {
                System.out.println("No se encontro el vehiculo.");
            } else {
                mostrarVehiculo(vehiculo);
            }

        } else if (opcion == 2) {

            String texto = leerTexto("Descripcion a buscar: ");
            Vehiculo[] bicicletas = parqueo.buscarBicicletas(texto);

            boolean encontro = false;

            for (int i = 0; i < bicicletas.length; i++) {

                if (bicicletas[i] != null) {
                    mostrarVehiculo(bicicletas[i]);
                    encontro = true;
                }
            }

            if (!encontro) {
                System.out.println("No se encontraron bicicletas.");
            }

        } else {
            System.out.println("Opcion invalida.");
        }
    }

    private void mostrarVehiculo(Vehiculo vehiculo) {

        Movimiento movimiento = vehiculo.getMovimientoActual();
        LocalDateTime ahora = LocalDateTime.now();

        double horas = movimiento.horasTranscurridasHasta(ahora);
        double monto = movimiento.calcularHorasCobradas(ahora) * parqueo.tarifa(vehiculo);

        System.out.println("\n========= VEHICULO =========");
        System.out.println("Tipo: " + vehiculo.getTipo());
        System.out.println("Placa/Descripcion: " + vehiculo.getID());
        System.out.println("Entrada: " + movimiento.getEntrada().format(formato));

        System.out.printf("Horas en parqueo: %.2f%n", horas);
        System.out.printf("Monto hasta el momento: ₡%.0f%n", monto);
    }

    private void salidaVehiculo() {

        System.out.println("\n1. Salida por placa");
        System.out.println("2. Salida por posicion");

        int opcion = leerEntero("Opcion: ");

        Vehiculo vehiculo = null;

        if (opcion == 1) {

            String placa = leerTexto("Placa: ");
            vehiculo = parqueo.buscarActivoPorPlaca(placa);

        } else if (opcion == 2) {

            String posicion = leerTexto("Posicion (ejemplo: 5 o M3): ");
            vehiculo = parqueo.buscarPorPosicion(posicion);

        } else {

            System.out.println("Opcion invalida.");
            return;
        }

        if (vehiculo == null) {
            System.out.println("Vehiculo no encontrado.");
            return;
        }

        Movimiento movimiento = vehiculo.getMovimientoActual();

        parqueo.sacar(vehiculo);

        System.out.println("\n========= SALIDA =========");
        System.out.println("Vehiculo: " + vehiculo.getID());
        System.out.println("Entrada: " + movimiento.getEntrada().format(formato));
        System.out.println("Salida: " + movimiento.getSalida().format(formato));

        System.out.printf("Horas cobradas: %.1f%n", movimiento.getHorasCobradas());
        System.out.printf("Tarifa por hora: ₡%.0f%n", parqueo.tarifa(vehiculo));
        System.out.printf("Monto a pagar: ₡%.0f%n", movimiento.getMonto());
    }

    private void consultarParqueo() {

        Vehiculo[] espacios = parqueo.getEspacios();
        LocalDateTime ahora = LocalDateTime.now();

        System.out.println("\n========= ESTADO DEL PARQUEO =========");

        for (int i = 0; i < espacios.length; i++) {

            System.out.print(parqueo.nombrePosicion(i) + ": ");

            if (espacios[i] == null) {

                System.out.println("Disponible");

            } else {

                Vehiculo vehiculo = espacios[i];
                Movimiento movimiento = vehiculo.getMovimientoActual();

                double horas = movimiento.horasTranscurridasHasta(ahora);

                System.out.printf(
                        "%s | %s | Entrada: %s | Horas: %.2f%n",
                        vehiculo.getTipo(),
                        vehiculo.getID(),
                        movimiento.getEntrada().format(formato),
                        horas
                );
            }
        }
    }

    private void consultarHistorico() {

        String placa = leerTexto("Placa: ").trim().toUpperCase();

        Vehiculo vehiculo = parqueo.buscarPorPlaca(placa);

        if (vehiculo == null) {
            System.out.println("No existen registros para esa placa.");
            return;
        }

        System.out.println("\n========= HISTORICO " + placa + " =========");

        for (int i = 0; i < vehiculo.getCantidadMovimientos(); i++) {

            Movimiento movimiento = vehiculo.getMovimientos()[i];

            System.out.println("\nVisita " + (i + 1));
            System.out.println("Entrada: " + movimiento.getEntrada().format(formato));

            if (movimiento.estaActivo()) {

                System.out.println("Actualmente esta en el parqueo.");

            } else {

                System.out.println("Salida: " + movimiento.getSalida().format(formato));
                System.out.printf("Horas cobradas: %.1f%n", movimiento.getHorasCobradas());
                System.out.printf("Monto pagado: ₡%.0f%n", movimiento.getMonto());
            }
        }
    }

    private void cierreDelDia() {

        Vehiculo[] vehiculos = parqueo.getVehiculos();
        int cantidad = parqueo.getCantidadVehiculos();

        // Primero saca todos los vehiculos que todavia estan adentro.
        for (int i = 0; i < cantidad; i++) {

            if (vehiculos[i].estaEnParqueo()) {
                parqueo.sacar(vehiculos[i]);
            }
        }

        LocalDate hoy = LocalDate.now();
        double total = 0;

        System.out.println("\n========= CIERRE DEL DIA =========");

        for (int i = 0; i < cantidad; i++) {

            Vehiculo vehiculo = vehiculos[i];

            for (int j = 0; j < vehiculo.getCantidadMovimientos(); j++) {

                Movimiento movimiento = vehiculo.getMovimientos()[j];

                if (movimiento.getSalida() != null
                        && movimiento.getSalida().toLocalDate().equals(hoy)) {

                    System.out.println("\nVehiculo: " + vehiculo.getID());
                    System.out.println("Entrada: " + movimiento.getEntrada().format(formato));
                    System.out.println("Salida: " + movimiento.getSalida().format(formato));

                    System.out.printf("Horas: %.1f%n", movimiento.getHorasCobradas());
                    System.out.printf("Monto: ₡%.0f%n", movimiento.getMonto());

                    total += movimiento.getMonto();
                }
            }
        }

        System.out.printf("\nTOTAL COBRADO: ₡%.0f%n", total);
    }

    private int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número valido.");
            }
        }
    }

    private String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return scanner.nextLine();
    }
}