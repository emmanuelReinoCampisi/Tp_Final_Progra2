//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import Clases.*;
import Enumeradores.ESPECIE;
import Enumeradores.ESTADOCITA;
import Enumeradores.TIPOCITA;
import Enumeradores.TURNO;
import Excepciones.*;
import Handlers.Validaciones;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        //System.out.println("Hacer dos listar empleados, uno con empleados activos y otros con empleados desactivados");
        Scanner sc = new Scanner(System.in);
        Veterinaria veterinaria = new Veterinaria();
        veterinaria.cargarDatos();
        boolean salir = false;

        System.out.println("🐾----Bienvenido al sistema de: " + veterinaria.getNombre() + " ----🐾");
        while (!salir) {
            System.out.println("💠 MENU PRINCIPAL 💠");
            System.out.println("1. Ingresar como Recepcionista");
            System.out.println("2. Ingresar como Veterinario");
            System.out.println("3. Ingresar modo Admin");
            System.out.println("4. Cerrar programa");
            System.out.println("Ingrese una opcion....");
            int opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {

                case 1:
                    String mailRecepcionista;
                    String contraseniaRecepcionista;
                    System.out.println("===Ingresando como Empleado===");
                    System.out.println("Ingrese el mail");
                    mailRecepcionista = sc.nextLine();
                    System.out.println("Ingrese la contrasenia");
                    contraseniaRecepcionista = sc.nextLine();
                    try {
                        if (veterinaria.ingresarEmpleado(mailRecepcionista, contraseniaRecepcionista)) {
                            int opcionRecep;
                            do {
                                System.out.println("==== MENU RECEPCIONISTA ====");
                                System.out.println("1. Asignar una cita");
                                System.out.println("2. Cancelar una cita");
                                System.out.println("3. Listar citas ");
                                System.out.println("4. Registrar nuevo Dueño");
                                System.out.println("5. Registrar nueva Mascota");
                                System.out.println("6. Listar Mascota ");
                                System.out.println("7. Guardar y salir");
                                System.out.println("Ingrese una opcion....");
                                opcionRecep = sc.nextInt();
                                sc.nextLine();
                                switch (opcionRecep) {

                                    case 1:
                                        try {
                                            System.out.print("Ingrese fecha o 0 para cancelar: ");
                                            String fechaInput = sc.nextLine();
                                            if (fechaInput.equals("0")) break;
                                            LocalDate fecha = LocalDate.parse(fechaInput);
                                            Validaciones.validarFecha(fecha);
                                            System.out.print("Ingrese hora: ");
                                            LocalTime hora = LocalTime.parse(sc.nextLine());
                                            System.out.println("Seleccione tipo de cita: 1.Control 2.Vacunación 3.Consulta General, 4.Cirugia, 5.Emergencia");
                                            TIPOCITA tipocita = null;
                                            int opcionCita = sc.nextInt();
                                            sc.nextLine();

                                            switch (opcionCita) {
                                                case 1 -> tipocita = TIPOCITA.CONTROL;
                                                case 2 -> tipocita = TIPOCITA.VACUNACION;
                                                case 3 -> tipocita = TIPOCITA.CONSULTA_GENERAL;
                                                case 4 -> tipocita = TIPOCITA.CIRUGIA;
                                                case 5 -> tipocita = TIPOCITA.EMERGENCIA;
                                                default -> System.out.println("OPCION INCORRECTA");
                                            }
                                            System.out.print("Ingrese ID de mascota: ");
                                            int idMascota = sc.nextInt();
                                            sc.nextLine();
                                            System.out.print("Ingrese DNI del veterinario: ");
                                            int dniVet = sc.nextInt();
                                            sc.nextLine();
                                            veterinaria.agregarCita(fecha, hora, tipocita, idMascota, ESTADOCITA.PENDIENTE, dniVet);
                                            System.out.println("Cita asignada correctamente.");
                                        } catch (ExcepcionNoCoincide e) {
                                            System.out.println(e.getMessage());
                                        } catch (CitaInvalidaExcep e) {
                                            System.out.println(e.getMessage());
                                        } catch (ExcepcionNoExistente e) {
                                            System.out.println(e.getMessage());
                                        } catch (Exception e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                        break;
                                    case 2:
                                        try {
                                            System.out.println("-------CITAS PENDIENTES---------");
                                            System.out.println(veterinaria.listarCitasPendientes()+"\n\n");
                                            System.out.print("Ingrese fecha (YYYY-MM-DD): ");
                                            LocalDate fechaCancel = LocalDate.parse(sc.nextLine());
                                            System.out.print("Ingrese hora (HH:MM): ");
                                            LocalTime horaCancel = LocalTime.parse(sc.nextLine());
                                            System.out.print("Ingrese DNI del veterinario: ");
                                            int dniVetCancel = sc.nextInt();
                                            sc.nextLine();
                                            veterinaria.cancelarCita(fechaCancel, horaCancel, dniVetCancel);
                                            System.out.println("Cita cancelada.");
                                        } catch (Exception e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                        break;
                                    case 3:
                                            int opcionCitas = 0;

                                            do {
                                                System.out.println("1. Listar citas pendientes");
                                                System.out.println("2. Listar citas atendidas");
                                                System.out.println("3. Salir");
                                                switch (opcionCitas) {
                                                    case 1:
                                                        try {
                                                            System.out.println(veterinaria.listarCitasPendientes());
                                                        } catch (ExcepcionNoExistente e) {
                                                            System.out.println(e.getMessage());
                                                        }

                                                        break;

                                                    case 2:
                                                        try {
                                                            System.out.println(veterinaria.listarCitasAtendidas());
                                                        }catch (ExcepcionNoExistente e) {
                                                            System.out.println(e.getMessage());
                                                        }
                                                        break;

                                                    case 3:
                                                        System.out.println("Volviendo al menu");
                                                        break;

                                                    default:
                                                        System.out.println("OPCION INCORRECTA...");
                                                        break;
                                                }
                                            }while (opcionCitas != 3);
                                        break;
                                    case 4:
                                        try {
                                            System.out.print("Ingrese nombre del dueño: ");
                                            String nombreD = sc.nextLine();
                                            System.out.print("Ingrese edad del dueño: ");
                                            int edadD = sc.nextInt();
                                            sc.nextLine();
                                            System.out.print("Ingrese DNI del dueño: ");
                                            int dniD = sc.nextInt();
                                            sc.nextLine();
                                            System.out.print("Ingrese teléfono: ");
                                            long tel = sc.nextLong();
                                            sc.nextLine();
                                            System.out.print("Ingrese dirección: ");
                                            String direccion = sc.nextLine();
                                            System.out.print("Ingrese nombre de la mascota: ");
                                            String nombreM = sc.nextLine();
                                            System.out.print("Ingrese edad de la mascota: ");
                                            int edadM = sc.nextInt();
                                            sc.nextLine();
                                            System.out.println("Seleccione especie: 1.Canino 2.Felino 3.Ave 4.Roedor 5.Reptil");
                                            ESPECIE especie = null;
                                            int opcionEspecie = sc.nextInt();
                                            sc.nextLine();

                                            switch (opcionEspecie) {
                                                case 1 -> especie = ESPECIE.CANINO;
                                                case 2 -> especie = ESPECIE.FELINO;
                                                case 3 -> especie = ESPECIE.AVE;
                                                case 4 -> especie = ESPECIE.ROEDOR;
                                                case 5 -> especie = ESPECIE.REPTIL;
                                                default -> System.out.println("OPCION INCORRECTA");
                                            }
                                            System.out.print("Ingrese raza: ");
                                            String raza = sc.nextLine();
                                            veterinaria.agregarDuenioNuevo(nombreD, edadD, dniD, tel, direccion, nombreM, edadM, especie, raza, dniD);
                                            System.out.println("Dueño y mascota registrados.");
                                        } catch (Exception e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                        break;
                                    case 5:
                                        try {
                                            System.out.print("Ingrese nombre de la mascota: ");
                                            String nombreM = sc.nextLine();
                                            System.out.print("Ingrese edad de la mascota: ");
                                            int edadM = sc.nextInt();
                                            sc.nextLine();
                                            System.out.println("Seleccione especie: 1.Canino 2.Felino 3.Ave 4.Roedor 5.Reptil");
                                            ESPECIE especie = null;
                                            int opcionEspecie = sc.nextInt();
                                            sc.nextLine();

                                            switch (opcionEspecie) {
                                                case 1 -> especie = ESPECIE.CANINO;
                                                case 2 -> especie = ESPECIE.FELINO;
                                                case 3 -> especie = ESPECIE.AVE;
                                                case 4 -> especie = ESPECIE.ROEDOR;
                                                case 5 -> especie = ESPECIE.REPTIL;
                                                default -> System.out.println("OPCION INCORRECTA");
                                            }
                                            System.out.print("Ingrese raza: ");
                                            String raza = sc.nextLine();
                                            System.out.print("Ingrese DNI del dueño: ");
                                            int dniDue = sc.nextInt();
                                            sc.nextLine();
                                            veterinaria.agregarMascotaADuenio(nombreM, edadM, especie, raza, dniDue);
                                            System.out.println("Mascota registrada.");
                                        } catch (Exception e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                        break;

                                    case 6:
                                            int opcionListado = 0;
                                            do{
                                                System.out.println("1. Listar todas las mascotas registradas");
                                                System.out.println("2. Listar una mascota en especifico");
                                                System.out.println("3. Salir");
                                                System.out.println("Ingrese opcion...");
                                                opcionListado = sc.nextInt();

                                                switch (opcionListado) {

                                                    case 1:
                                                        System.out.println(veterinaria.listarMascotas());
                                                        break;


                                                    case 2:
                                                        System.out.println("Ingrese el DNI del dueño de la mascota: ");
                                                        int dniDue = sc.nextInt();
                                                        sc.nextLine();
                                                        System.out.println("Ingrese el nombre de la mascota: ");
                                                        String nombreM = sc.nextLine();
                                                        System.out.println(veterinaria.listarMascotaEspecifica(dniDue,nombreM));


                                                        break;

                                                    case 3:
                                                        System.out.println("Volviendo....");
                                                        break;

                                                    default:
                                                        System.out.println("OPCION INCORRECTA");
                                                        break;
                                                }

                                            }while(opcionListado != 3);
                                        break;
                                    case 7:
                                        System.out.println("Cerrando sesion....");
                                        break;

                                    default:
                                        System.out.println("Opcion inavalida, ingrese una nueva opcion...");
                                        break;

                                }
                            } while (opcionRecep != 7);
                        }
                    }catch (ExcepcionFormatoNoValido e){
                        System.out.println(e.getMessage());
                    }catch (ExcepcionNoExistente e){
                        System.out.println(e.getMessage());
                    }catch (ExcepcionCuentaInactiva e){
                        System.out.println(e.getMessage());
                    } catch (ExcepcionNoCoincide e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    String mailVet;
                    String contraseniaVet;
                    System.out.println("===Ingresando como Veterinario===");
                    System.out.println("Ingrese el mail");
                    mailVet = sc.nextLine();
                    System.out.println("Ingrese la contrasenia");
                    contraseniaVet = sc.nextLine();
                    try {
                        if (veterinaria.ingresarEmpleado(mailVet, contraseniaVet)) {
                    int opcionVet;

                    do {
                        System.out.println("==== MENU VETERINARIO ===");
                        System.out.println("1. Listar citas pendientes");
                        System.out.println("2. Listar animales atendidos");
                        System.out.println("3. Agregar diagnostico");
                        System.out.println("3. Agregar diagnostico");
                        System.out.println("4. Guardar y salir");
                        System.out.println();
                        opcionVet = sc.nextInt();
                        sc.nextLine();

                        switch (opcionVet) {
                            case 1:

                                break;


                            case 2:

                                break;


                            case 3:

                                break;


                            case 4:
                                System.out.println("Cerrando sesion....");
                                break;

                            default:
                                System.out.println("Opcion inavlida, ingrese una nueva opcion...");
                                break;
                        }
                    } while (opcionVet != 4);
                        }
                    }catch (ExcepcionFormatoNoValido e){
                        System.out.println(e.getMessage());
                    }catch (ExcepcionNoExistente e){
                        System.out.println(e.getMessage());
                    }catch (ExcepcionCuentaInactiva e){
                        System.out.println(e.getMessage());
                    } catch (ExcepcionNoCoincide e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    String mailConsola;
                    String contrasenia;
                    System.out.println("===Ingresando como ADMIN===");
                    System.out.println("Ingrese el mail");
                    mailConsola = sc.nextLine();
                    System.out.println("Ingrese la contrasenia");
                    contrasenia = sc.nextLine();

                    if (veterinaria.ingresarAdmin(mailConsola, contrasenia)) {
                        int opcionAdmin;
                        do {
                            System.out.println("===== MODO ADMIN =====");
                            System.out.println("1. Registrar un nuevo empleado");
                            System.out.println("2. Listar Empleados");
                            System.out.println("3. Listar Duenios");
                            System.out.println("4. Desactivar Cuenta");
                            System.out.println("5. Activar Cuenta");
                            System.out.println("6. Buscar por DNI");
                            System.out.println("7. Agregar especialidad Veterinario");
                            System.out.println("8. Eliminar especialidad Veterinario");
                            System.out.println("9. Cerrar sesion");///
                            System.out.println("Opcion...");
                            opcionAdmin = sc.nextInt();
                            sc.nextLine();
                            switch (opcionAdmin) {
                                case 1:
                                    int opcionRegistro;
                                    System.out.println("1.Crear nuevo recepcionista");
                                    System.out.println("2.Crear nuevo veterinario");
                                    System.out.println("3.Salir");
                                    System.out.println("Ingrese una opcion....");
                                    opcionRegistro = sc.nextInt();
                                    switch (opcionRegistro) {
                                        case 1:
                                            System.out.println("================Creando recepcionista================");
                                            System.out.println("=Ingrese 0 en cualquier momento para cancelar el registro=");
                                            registroRec: /// label o etiqueta, sirve para salir de multiples niveles de anidamiento de bucles: nos permitió salir de un while, switch y de un try con una sola instruccion
                                            try {
                                                //System.out.println("Ingrese 0 para cancelar el ingreso."); Falta agregar la cancelacion del ingreso
                                                System.out.println("Ingrese el nombre del Empleado: ");
                                                String nombreE = sc.nextLine();
                                                if(nombreE.equals("0")) break registroRec;

                                                System.out.println("Ingrese la edad del Empleado: ");
                                                String edadString = sc.nextLine();
                                                if (edadString.equals("0")) break registroRec;
                                                int edadE = Integer.parseInt(edadString);
                                                Validaciones.validarEdad(edadE);

                                                System.out.println("Ingrese el DNI del Empleado: ");
                                                String dniString = sc.nextLine();
                                                if(dniString.equals("0")) break registroRec;
                                                int dniE = Integer.parseInt(dniString);
                                                Validaciones.validarFormatoDNI(dniE);

                                                System.out.println("Ingrese el email del nuevo Empleado: ");
                                                String emailE = sc.nextLine();
                                                if (emailE.equals("0")) break registroRec;
                                                Validaciones.validarFormatoEmail(emailE);

                                                System.out.println("Ingrese la contraseña predefinida para el Empleado: ");
                                                String contraE = sc.nextLine();
                                                if (contraE.equals("0")) break registroRec;
                                                Validaciones.validarFormatoContrasenia(contraE);

                                                System.out.println("Confirme la contraseña.");
                                                String contraConfirmadaE = sc.nextLine();
                                                if (contraConfirmadaE.equals("0")) break registroRec;
                                                Validaciones.validarMismaContrasenia(contraE, contraConfirmadaE);

                                                System.out.println("Seleccione el turno que va a ocupar el Empleado: ");
                                                System.out.println("1. Turno mañana");
                                                System.out.println("2. Turno tarde");
                                                System.out.println("3. Turno noche");
                                                TURNO turnoSeleccionadoE = null;
                                                String opcionTurnoEString = sc.nextLine();
                                                if (opcionTurnoEString.equals("0")) break registroRec;
                                                int opcionTurnoE = Integer.parseInt(opcionTurnoEString);

                                                switch (opcionTurnoE) {
                                                    case 1:
                                                        turnoSeleccionadoE = TURNO.MANIANA;
                                                        break;
                                                    case 2:
                                                        turnoSeleccionadoE = TURNO.TARDE;
                                                        break;
                                                    case 3:
                                                        turnoSeleccionadoE = TURNO.NOCHE;
                                                        break;

                                                    default:
                                                        System.out.println("OPCION INCORRECTA");
                                                        break;
                                                }

                                                veterinaria.agregarEmpleado(nombreE, edadE, dniE, emailE, contraE, turnoSeleccionadoE);
                                            } catch (ExcepcionYaExistente e) {
                                                System.out.println(e.getMessage());
                                            } catch (ExcepcionFormatoNoValido e) {
                                                System.out.println(e.getMessage());
                                            } catch (ExcepcionNoCoincide e) {
                                                System.out.println(e.getMessage());
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case 2:
                                            registroVet:
                                            try {
                                                sc.nextLine();
                                                System.out.println("================Creando Veterinario================");
                                                System.out.println("Ingrese 0 en cualquier momento previo al ingreso de especialidades para cancelar el ingreso.");
                                                System.out.println("Ingrese el nombre del Veterinario: ");
                                                String nombreV = sc.nextLine();
                                                if (nombreV.equals("0")) break registroVet;

                                                System.out.println("Ingrese la edad del Veterinario: ");
                                                String edadVString = sc.nextLine();
                                                int edadV = Integer.parseInt(edadVString);
                                                Validaciones.validarEdad(edadV);

                                                System.out.println("Ingrese el DNI del Verinario: ");
                                                String dniVString = sc.nextLine();
                                                if (dniVString.equals("0")) break registroVet;
                                                int dniV = Integer.parseInt(dniVString);
                                                Validaciones.validarFormatoDNI(dniV);

                                                System.out.println("Ingrese el email del nuevo Verinario: ");
                                                String emailV = sc.nextLine();
                                                if (emailV.equals("0")) break registroVet;
                                                Validaciones.validarFormatoEmail(emailV);

                                                System.out.println("Ingrese la contraseña predefinida para el empleado: ");
                                                String contraV = sc.nextLine();
                                                if(contraV.equals("0")) break registroVet;
                                                Validaciones.validarFormatoContrasenia(contraV);

                                                System.out.println("Confirme la contraseña.");
                                                String contraConfirmadaV = sc.nextLine();
                                                if(contraConfirmadaV.equals("0")) break registroVet;
                                                Validaciones.validarMismaContrasenia(contraV, contraConfirmadaV);

                                                System.out.println("Seleccione el turno que va a ocupar el Veterinario: ");
                                                System.out.println("1. Turno mañana");
                                                System.out.println("2. Turno tarde");
                                                System.out.println("3. Turno noche");
                                                TURNO turnoSeleccionadoV = null;
                                                String opcionTurnoVString = sc.nextLine();
                                                if(opcionTurnoVString.equals("0")) break registroVet;
                                                int opcionTurnoV = Integer.parseInt(opcionTurnoVString);

                                                switch (opcionTurnoV) {
                                                    case 1:
                                                        turnoSeleccionadoV = TURNO.MANIANA;
                                                        break;
                                                    case 2:
                                                        turnoSeleccionadoV = TURNO.TARDE;
                                                        break;
                                                    case 3:
                                                        turnoSeleccionadoV = TURNO.NOCHE;
                                                        break;

                                                    default:
                                                        System.out.println("OPCION INCORRECTA");
                                                        break;
                                                }

                                                System.out.println("Ingrese la Matricula del Veterinario: ");
                                                String matricula = sc.nextLine();
                                                if (matricula.equals("0")) break registroVet;
                                                Validaciones.validarFormatoMatricula(matricula);
                                                veterinaria.agregarVeterinario(nombreV, edadV, dniV, emailV, contraV, turnoSeleccionadoV, matricula);

                                                char seguirEspecialidades = 's';
                                                while (seguirEspecialidades == 's') {
                                                    System.out.println("Seleccione las especialidades del Veterinario: ");
                                                    System.out.println("1. Caninos");
                                                    System.out.println("2. Felinos");
                                                    System.out.println("3. Aves");
                                                    System.out.println("4. Roedores");
                                                    System.out.println("5. Reptiles");
                                                    ESPECIE especialidad = null;
                                                    int opcionEspecialidad = sc.nextInt();
                                                    sc.nextLine();

                                                    switch (opcionEspecialidad) {
                                                        case 1 -> especialidad = ESPECIE.CANINO;
                                                        case 2 -> especialidad = ESPECIE.FELINO;
                                                        case 3 -> especialidad = ESPECIE.AVE;
                                                        case 4 -> especialidad = ESPECIE.ROEDOR;
                                                        case 5 -> especialidad = ESPECIE.REPTIL;
                                                        default -> System.out.println("OPCION INCORRECTA");
                                                    }

                                                    veterinaria.agregarEspecialidadVeterinario(dniV, especialidad);
                                                    System.out.println("Ingrese s para cargar otra especialidad");
                                                    seguirEspecialidades = sc.nextLine().charAt(0);
                                                }

                                            } catch (ExcepcionYaExistente e) {
                                                System.out.println(e.getMessage());
                                            } catch (ExcepcionFormatoNoValido e) {
                                                System.out.println(e.getMessage());
                                            } catch (ExcepcionNoCoincide e) {
                                                System.out.println(e.getMessage());
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case 3:
                                            System.out.println("Volviendo...");
                                            break;
                                        default:
                                            System.out.println("Opcion invalida");
                                            break;
                                    }break;
                                case 2:
                                    System.out.println(veterinaria.listarEmpleados());
                                    break;

                                case 3:
                                    System.out.println(veterinaria.listarDuenios());
                                    break;

                                case 4:
                                    System.out.println("===Desactivar cuenta===");
                                    System.out.println("Ingrese el mail de la cuenta a desactivar");
                                    String mailDesactivar = sc.nextLine();
                                    if(veterinaria.desactivarCuenta(mailDesactivar)){
                                        System.out.println("La cuenta " + mailDesactivar + " ha sido desactivada correctamente");
                                    }
                                    break;
                                case 5:
                                    System.out.println("===Activar cuenta===");
                                    System.out.println("Ingrese el mail de la cuenta a activar");
                                    String mailActivar = sc.nextLine();
                                    if(veterinaria.activarCuenta(mailActivar)){
                                        System.out.println("La cuenta " + mailActivar + " ha sido activada correctamente");
                                    }
                                    break;
                                case 6:
                                    int opcionBuscarDni = 0;


                                    do {
                                        System.out.println("1. Buscar dueño por dni");
                                        System.out.println("2. Buscar empleado por dni");
                                        System.out.println("3. Salir del menú");
                                        System.out.println("Ingrese una opcion...");
                                        opcionBuscarDni = sc.nextInt();
                                        sc.nextLine();
                                        switch (opcionBuscarDni) {
                                            case 1:
                                                System.out.println("Ingrese el DNI del dueño");
                                                int dniDuenio = sc.nextInt();
                                                try {
                                                    veterinaria.buscarDuenioPorDNI(dniDuenio);
                                                } catch (ExcepcionNoExistente e) {
                                                    System.out.println(e.getMessage());
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Ingrese el DNI del empleado");
                                                int dniEmp = sc.nextInt();
                                                try {
                                                    System.out.println(veterinaria.buscarEmpleadoPorDNI(dniEmp));
                                                }catch (ExcepcionNoExistente e) {
                                                    System.out.println(e.getMessage());
                                                }
                                                break;
                                            case 3:
                                                System.out.println("Saliendo del menú buscar");
                                                break;
                                            default:
                                                System.out.println("Opcion invalida, ingrese una nueva opcion...");
                                                break;
                                        }
                                    }while(opcionBuscarDni != 3);
                                    break;

                                case 7:
                                    try {


                                    System.out.println(veterinaria.listarEmpleados());
                                    System.out.println("Ingrese el DNI del vet a agregar especialidad");
                                    int dniVet = sc.nextInt();
                                    char seguirEspecialidades = 's';
                                    while (seguirEspecialidades == 's') {
                                        System.out.println("Seleccione las especialidades del Veterinario: ");
                                        System.out.println("1. Caninos");
                                        System.out.println("2. Felinos");
                                        System.out.println("3. Aves");
                                        System.out.println("4. Roedores");
                                        System.out.println("5. Reptiles");
                                        ESPECIE especialidad = null;
                                        int opcionEspecialidad = sc.nextInt();
                                        sc.nextLine();

                                        switch (opcionEspecialidad) {
                                            case 1 -> especialidad = ESPECIE.CANINO;
                                            case 2 -> especialidad = ESPECIE.FELINO;
                                            case 3 -> especialidad = ESPECIE.AVE;
                                            case 4 -> especialidad = ESPECIE.ROEDOR;
                                            case 5 -> especialidad = ESPECIE.REPTIL;
                                            default -> System.out.println("OPCION INCORRECTA");
                                        }

                                        veterinaria.agregarEspecialidadVeterinario(dniVet, especialidad);
                                        System.out.println("Ingrese s para cargar otra especialidad");
                                        seguirEspecialidades = sc.nextLine().charAt(0);
                                    }
                                    } catch (ExcepcionNoExistente e) {
                                        System.out.println(e.getMessage());
                                    } catch (ExcepcionNoCoincide e) {
                                        System.out.println(e.getMessage());
                                    } catch (ExcepcionYaExistente e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;

                                case 8:
                                    try {
                                        System.out.println("Ingrese el DNI del veterinario: ");
                                        int dniVet = sc.nextInt();
                                        sc.nextLine();

                                        System.out.println("=== Elija la especialidad a eliminar ===");
                                        for (ESPECIE e : ESPECIE.values()) {
                                            System.out.println("- " + e);
                                        }

                                        String especIngresada = sc.nextLine().toUpperCase();
                                        ESPECIE especie = ESPECIE.valueOf(especIngresada);

                                        if (veterinaria.eliminarEspecialidadAVeterinario(dniVet, especie)) {
                                            System.out.println("Especialidad eliminada correctamente.");
                                        }

                                    } catch (ExcepcionNoExistente e) {
                                        System.out.println(e.getMessage());
                                    } catch (ExcepcionColeccionVacia e) {
                                        System.out.println(e.getMessage());
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("La especialidad ingresada no existe.");
                                    }
                                    break;
                                case 9:
                                    System.out.println("Cerrando sesion de administrador....");
                                    break;

                                default:
                                    System.out.println("Opcion invalida, ingrese una nueva opcion...");
                                    break;
                            }

                        } while (opcionAdmin != 9);
                    }
                    break;


                case 4:
                    veterinaria.guardarDatos();

                    salir = true;
                    break;
                default:
                    System.out.println("⚠️-INGRESE UNA OPCION VALIDA-⚠️");
            }


        }


        /// Testeos varios
 /*       LocalDate fechaCita = LocalDate.of(2025, 11, 8);
        LocalDate fechaCita2 = LocalDate.of(2025, 11, 8);
        LocalTime horarioCita = java.time.LocalTime.of(15, 30);
        LocalTime horarioCita2 = java.time.LocalTime.of(15, 30);
        String motivo = "Esta cita fue creada para testeo";

        Veterinario Vettester = new Veterinario("Juan", 28, 1234, "juan123@gmail.com", "1234", TURNO.TARDE, "Veterinario");
        Veterinario Vettester2 = new Veterinario("Pedro", 31, 412, "pepe533@gmail.com", "654", TURNO.TARDE, "Veterinario");
        Mascota TobyTester = new Mascota("TobyTester", 2, ESPECIE.CANINO, "Rottweiler", 11223344);
        Mascota CocoTester = new Mascota("CocoTester", 1, ESPECIE.CANINO, "Pastor Aleman", 11223344);
        Cita cita = new Cita(fechaCita, horarioCita, TIPOCITA.CONTROL, ESTADOCITA.PENDIENTE, TobyTester.getID(), Vettester.getDni());

        System.out.println(cita.toString());

        JSONObject jsonObject = cita.citaTOJson();
        System.out.println("json object cita:");
        System.out.println(jsonObject);


        try {    // asi tira eror por el veterinario ocupado
            //veterinaria.agregarCita(fechaCita,horarioCita, TIPOCITA.CONTROL, TobyTester, ESTADOCITA.PENDIENTE,Vettester);
            //veterinaria.agregarCita(fechaCita,horarioCita, TIPOCITA.CONTROL, CocoTester, ESTADOCITA.PENDIENTE,Vettester);


            //asi por la mascota que ya tiene asignada una cita
            //veterinaria.agregarCita(fechaCita,horarioCita, TIPOCITA.CONTROL, TobyTester, ESTADOCITA.PENDIENTE,Vettester);
            //veterinaria.agregarCita(fechaCita,horarioCita, TIPOCITA.CONTROL, TobyTester, ESTADOCITA.PENDIENTE,Vettester2);

            // aca anda bien
            //veterinaria.agregarCita(fechaCita,horarioCita, TIPOCITA.CONTROL, TobyTester, ESTADOCITA.PENDIENTE,Vettester);
            //veterinaria.agregarCita(fechaCita,horarioCita, TIPOCITA.CONTROL, CocoTester, ESTADOCITA.PENDIENTE,Vettester2);

        } catch (CitaInvalidaExcep c) {
            System.out.println("" + c.getMessage());
        }*/
    }

}