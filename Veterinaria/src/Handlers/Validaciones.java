    package Handlers;

    import Clases.Cita;
    import Excepciones.CitaInvalidaExcep;
    import Excepciones.ExcepcionFormatoNoValido;
    import Excepciones.ExcepcionNoCoincide;

    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.regex.Pattern;

    public class Validaciones {

        public static boolean validarFormatoEmail(String email)throws ExcepcionFormatoNoValido{
            String regexPermitidos = "^[a-zA-Z0-9.]+$";
            boolean emailValido = false;
            if(email.toLowerCase().endsWith("@gmail.com")){
                String nombreUsuario = email.substring(0,email.length() - 10);
                if(nombreUsuario.length()>5 && nombreUsuario.length()<31){
                    if (nombreUsuario.startsWith(".")){
                        throw new ExcepcionFormatoNoValido("El email no puede comenzar con punto.");
                    } else {
                        if(!nombreUsuario.matches(regexPermitidos)){
                            throw new ExcepcionFormatoNoValido("El email solo puede contener letras, numeros y puntos.");
                        }
                        else {
                            emailValido = true;
                        }
                    }
                } else {
                    throw new ExcepcionFormatoNoValido("El email debe tener entre 6 y 30 caracteres.");
                }
            }else {
                throw new ExcepcionFormatoNoValido("El email debe terminar en @gmail.com.");
            }


            return emailValido;
        }

        public static boolean validarFormatoContrasenia(String contrasenia)throws ExcepcionFormatoNoValido{
            boolean contraseniaValida = false;

            if(contrasenia.length()<6 || contrasenia.length()>15){
               throw new ExcepcionFormatoNoValido("La contrasenia debe tener entre 6 y 15 caracteres");
            }else {
                contraseniaValida = true;
            }
            return contraseniaValida;
        }

        public static boolean validarMismoEmail(String mailUno, String mailDos)throws ExcepcionNoCoincide{ /// EXC valida?
            boolean mismoEmail = false;

            if(mailUno.equalsIgnoreCase(mailDos)){
                mismoEmail = true;
            } else {
                throw new ExcepcionNoCoincide("El mail no coincide");
            }
            return mismoEmail;
        }
        public static boolean validarMismaContrasenia(String contraseniaUno, String contraseniaDos)throws ExcepcionNoCoincide{
            boolean mismaContrasenia = false;

            if(contraseniaUno.equals(contraseniaDos)){
                mismaContrasenia = true;
            }else {
               throw new ExcepcionNoCoincide("Las contrasenias no coinciden");
            }
            return mismaContrasenia;
        }

        public static boolean validarFormatoDNI(int dni)throws ExcepcionFormatoNoValido{
            boolean valido = true;
            if(dni < 10000000 || dni > 99999999){
            throw new ExcepcionFormatoNoValido("El dni debe tener 8 digitos");
            }
            return valido;
        }
        public static boolean validarFormatoMatricula(String matricula)throws ExcepcionFormatoNoValido{
            boolean valida = false;
            String regex = "^[a-zA-Z]{3}[0-9]{3}$";

            if (!Pattern.matches(regex, matricula)) {
                throw new ExcepcionFormatoNoValido("El formato de la matrícula es inválido. Debe ser de 3 letras seguidas de 3 números (Ej: ABC123).");
            }else {valida = true;}
            return valida;
        }

        public static void validarRangoAnio(LocalDate fecha)throws CitaInvalidaExcep, ExcepcionNoCoincide {
            int anioActual = LocalDate.now().getYear();
            int anioProximo = anioActual + 1;
            int anioIngresado = fecha.getYear();
            if (fecha != null){
                if (anioIngresado != anioActual && anioIngresado != anioProximo){
                    throw new ExcepcionNoCoincide("El anio ingresado debe ser el año actual o el siguiente");
                }
            }else {
                throw new CitaInvalidaExcep ("Debe ingresar una fecha");
            }
        }

        public static void validarFecha(LocalDate fecha) throws CitaInvalidaExcep {
            if (fecha == null){
                throw new CitaInvalidaExcep("Debe ingresar una fecha.");
            } else if (fecha.isBefore(LocalDate.now())) {
                throw new CitaInvalidaExcep("La fecha no puede ser anterior a hoy.");
            }
        }

        public static void validarHorarioRango(LocalTime horario) throws CitaInvalidaExcep {
            LocalTime inicio = LocalTime.of(8, 0);
            LocalTime fin = LocalTime.of(19, 0);
            if (horario == null){
                throw new CitaInvalidaExcep("Debe ingresar un horario.");
            } else if (horario.isBefore(inicio) || horario.isAfter(fin)) {
                throw new CitaInvalidaExcep("El horario debe estar entre las 08:00 y las 19:00.");
            }
        }

        public static boolean validarEdad(int edad) throws ExcepcionFormatoNoValido {
            boolean valida = false;
            if(edad<18 || edad>99){
                throw new ExcepcionFormatoNoValido ("La persona debe tener entre 18 y 99 años.");
            } else{
                valida = true;
            }
            return valida;
        }
    }
