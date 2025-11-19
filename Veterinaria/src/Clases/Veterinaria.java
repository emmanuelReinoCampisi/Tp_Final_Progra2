package Clases;

import Enumeradores.ESPECIE;
    import Enumeradores.ESTADOCITA;
    import Enumeradores.TIPOCITA;
    import Enumeradores.TURNO;
    import Excepciones.*;
    import Handlers.*;
import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.Iterator;
    import java.util.List;

    public class Veterinaria {
        private final String NOMBRE_ARCHIVO = "veterinaria.json";
        private final String nombre = "VETPET"; /// Guardar en el archivo segun el profe y lo de gmailAdmin + contraAdmin tambien || COMO???
        private final String direccion = "Juan B. Justo 492"; ///  Guardar en el archivo segun el profe
        private final String emailAdmin = "vetpet@gmail.com";
        private final String contraseniaAdmin = "Perrunos123";
        private Gestor<Empleado> Personal; ///veterinarios y recepcionistas
        private Gestor<Duenio> Duenios;
        private Gestor<Cita> Citas;

        public Veterinaria(){
            Personal = new Gestor<>();
            Duenios = new Gestor<>();
            Citas = new Gestor<>();
        }

        public String getNombre() {
            return nombre;
        }

        public void agregarEmpleado(String nombre, int edad, int dni, String email, String contrasenia, TURNO turno)throws ExcepcionYaExistente {
            Empleado empleado = new Empleado(nombre, edad, dni, email, contrasenia, turno);
                    if(Personal.existe(empleado)){
                        throw new ExcepcionYaExistente("Empleado existente");
                    }else{
                        Personal.agregar(empleado);
                    }
        }

        public void agregarVeterinario(String nombre, int edad, int dni, String email, String contrasenia, TURNO turno, String matricula)throws ExcepcionYaExistente {
            Veterinario veterinario = new Veterinario(nombre,edad,dni,email,contrasenia,turno,matricula);
            if(Personal.existe(veterinario)){
                    throw new ExcepcionYaExistente("Veterinario ya existente");
                } else{
                Personal.agregar(veterinario);
            }
        }

        public void agregarEspecialidadVeterinario(int dni, ESPECIE especialidad)throws ExcepcionNoExistente, ExcepcionNoCoincide, ExcepcionYaExistente{/// Hay que verificar de que exosta el veterinario
            Empleado emp = Personal.obtenerPorIdentificador(dni);
            if(emp != null){
                if(emp instanceof Veterinario){
                    ((Veterinario) emp).agregarEspecialidad(especialidad);
                }else{
                    throw new ExcepcionNoCoincide("El dni no es de un veterinario");
                }
            }else {
                throw new ExcepcionNoExistente("El dni del veterinario no se encuentro en el sistema");
            }

        }

        public boolean eliminarEspecialidadAVeterinario(int dniVet, ESPECIE especie) throws ExcepcionNoExistente, ExcepcionColeccionVacia {

            boolean seElimino = false;
            Iterator<Empleado> it = Personal.getIterator();
            while (it.hasNext()) {
                Empleado emp = it.next();
                if (emp instanceof Veterinario && emp.getDni() == dniVet) {

                    Veterinario vet = (Veterinario) emp;
                    vet.eliminarEspecialidad(especie);
                    seElimino = true;
                }
            }

                if(seElimino==false){
                    throw new ExcepcionNoExistente("No se encontró un veterinario con ese DNI.");
                }

                return seElimino;
        }

        public String listarEmpleados(){
            String lista = "";
            return lista += Personal.listar();
        }

        public String listarDuenios(){

            String lista = "";
            return lista += Duenios.listar();
        }

    public void agregarCita (LocalDate fecha, LocalTime horario, TIPOCITA motivo,int idMascota, ESTADOCITA estadoCita, int  dniVet)throws CitaInvalidaExcep, ExcepcionNoExistente, ExcepcionNoCoincide {
            Cita c = new Cita(fecha, horario, motivo, estadoCita,idMascota,dniVet);
            Veterinario vet = (Veterinario) Personal.obtenerPorIdentificador(dniVet);
            Validaciones.validarFecha(fecha);
            Validaciones.validarRangoAnio(fecha);
            Validaciones.validarHorarioRango(horario);
            LocalTime finCitaNueva = c.getHorario().plusMinutes(c.getMotivo().getDuracionMinutos()); /// calculamos el horario aproximado del fin de la cita que queremos cargar

        // ahora vamos a comprobar que este el horario dispo y el veterinario tambien

        Iterator <Cita> it = Citas.getIterator();
        while(it.hasNext()) {
            Cita c2 = it.next(); // citas existentes

            LocalTime horarioInicioExistente = c2.getHorario(); // tomamos el horario que arranca
            LocalTime horarioFinExistente = c2.getFinCita(); // tomamos el horario que termina aprox

            // verifico la fecha de la cita
            if (c.getFecha().equals(c2.getFecha())) {
                // comprobar que el veterinario este libre/exista
                if (vet != null) {
                    if (c2.getVeterinario_dni() == dniVet) {

                        boolean vetOcupado = c.getHorario().isBefore(horarioFinExistente) && finCitaNueva.isAfter(horarioInicioExistente);
                        if (vetOcupado) {
                            throw new CitaInvalidaExcep("Veterinario ocupado en ese dia y horario: " + c.getMotivo());
                        }
                    }
                    // verifico que la mascota no tenga ninguna cita agendada en esa fecha y horario
                    if (c2.getMascota_id() == idMascota) {

                        boolean mascotaAsig = c.getHorario().isBefore(horarioFinExistente) && finCitaNueva.isAfter(horarioInicioExistente);
                        if (mascotaAsig) {
                            throw new CitaInvalidaExcep("La mascota ya tiene una cita en ese dia y horario: " + c.getMotivo());
                        }
                    }

                }else {throw new ExcepcionNoExistente("Veterinario no existente");}
            }

        }
        // si no se solapa con ninguna existente y es veterinario esta libre la agrega
        Citas.agregar(c);

        if(Personal.existe(vet)){
            vet.asignarCita(c);
        }
    } /// Este metodo de aca se tendria que rehacer con los cambios a cita


        // metodo para agregar un duenio nuevo que no este cargado en el sistema
        public void agregarDuenioNuevo(String nombre, int edad, int dni, long telefono, String direccion, String nombreM, int edadM, ESPECIE especie, String raza, int dniDuenio)throws ExcepcionYaExistente{
            Duenio nuevo = new Duenio(nombre, edad, dni, telefono, direccion);
            Mascota nueva = new Mascota(nombre, edad, especie, raza, dniDuenio);

            if(Duenios.existe(nuevo)){
                throw new ExcepcionYaExistente("Duenio ya existente");
            }else{
                nuevo.agregarMascota(nueva);
                Duenios.agregar(nuevo);
            }
        }


        public void asignarDiagnostico(int idCita, int dniVet, String diagnostico)throws ExcepcionNoExistente{

            boolean citaEncontrada = false;

            Iterator<Cita> it = Citas.getIterator();
            while(it.hasNext()) {
                Cita c = it.next();
                if(c.getId() == idCita && c.getVeterinario_dni() == dniVet){

                    c.setDiagnostico(diagnostico);
                    c.setEstadoCita(ESTADOCITA.ATENDIDA);
                    citaEncontrada = true;
                }
            }

            if(!citaEncontrada){
                throw new ExcepcionNoExistente("No se encontro la cita para asignar el diagnostico");
            }

        }

        public String buscarDuenioPorDNI(int dni)throws ExcepcionNoExistente
        {   String lista = " ";
            if(!lista.equals(Duenios.buscarPorId(dni))){
                lista= Duenios.buscarPorId(dni);
            }else {
                throw new ExcepcionNoExistente("El dni no pertenece a ningun dueño registrado.");
            }

            return lista;

        }

        public String buscarEmpleadoPorDNI(int dni) throws  ExcepcionNoExistente
        {
            String lista = " ";
            if(!lista.equals(Personal.buscarPorId(dni))){
                lista= Personal.buscarPorId(dni);
            }else {
                throw new ExcepcionNoExistente("El dni no pertenece a ningun empleado registrado.");
            }

            return lista = Personal.buscarPorId(dni);
        }

        public String listarMascotas() {
            String mensaje = "";

                Iterator<Duenio> it = Duenios.getIterator();
                while (it.hasNext()) {
                    Duenio duenio = it.next();
                    mensaje+=duenio.listarMascotas();
                }

            return mensaje;
        }

        public String listarMascotaEspecifica(int dni, String nombreMascota) {
            String mensaje = "";
            Duenio d = Duenios.obtenerPorIdentificador(dni);
            if (d != null) {
                Iterator<Duenio> it = Duenios.getIterator();
                while (it.hasNext()) {
                    Duenio duenio = it.next();
                    mensaje= duenio.listarMascotasEspecifica(nombreMascota);
                }
            }
            return mensaje;
        }


        public boolean agregarMascotaADuenio(String nombreMascota, int edadMascota, ESPECIE especie, String raza, int dniDuenio) throws ExcepcionNoExistente {
            Duenio duenio = Duenios.obtenerPorIdentificador(dniDuenio);
            if (duenio != null) {
                Mascota nuevaMascota = new Mascota(nombreMascota, edadMascota, especie, raza, dniDuenio);
                duenio.agregarMascota(nuevaMascota);
                return true;
            } else {
                throw new ExcepcionNoExistente("No se encontró el dueño con DNI: " + dniDuenio);
            }
        }


        public void cancelarCita(LocalDate fecha, LocalTime horario, int dniVet)throws CitaInvalidaExcep{

            Cita citaCancelar = null;

            Iterator<Cita> it = Citas.getIterator();
            while(it.hasNext()) {
                Cita c= it.next();

                if(c.getFecha().equals(fecha)&&c.getHorario().equals(horario)&& c.getVeterinario_dni() == dniVet) {
                    citaCancelar=c;
                    c.setEstadoCita(ESTADOCITA.CANCELADA);
                    c.setHorario(null);
                }
            }

            if(citaCancelar==null){
                throw new CitaInvalidaExcep("Cita no encontrada");
            }

            Empleado vet = Personal.obtenerPorIdentificador(dniVet);

            if(vet instanceof Veterinario){
                Veterinario vetCancelar = (Veterinario)vet;
                vetCancelar.cancelarCita(citaCancelar);
                System.out.println("se cancelo la cita");
            }
        }



      public String listarCitasPendientes()throws ExcepcionNoExistente{
            LocalDate fecha = LocalDate.now();
            String mensaje = "";
            boolean citasPendientes = false;
            Iterator<Cita> it = Citas.getIterator();
            while(it.hasNext()) {
                Cita c= it.next();
                if(c.getFecha().equals(fecha)|| c.getFecha().isAfter(fecha)) {
                    mensaje=c.toString();
                    citasPendientes=true;
                }
            }

            if(citasPendientes==false){
                throw new ExcepcionNoExistente("No hay citas pendientes");
            }

            return mensaje;
      }


        public String listarCitasAtendidas()throws ExcepcionNoExistente{
            String mensaje = "";
            boolean citasPendientes = false;
            Iterator<Cita> it = Citas.getIterator();
            while(it.hasNext()) {
                Cita c= it.next();
                if(c.getEstadoCita().equals(ESTADOCITA.ATENDIDA)) {
                    mensaje=c.toString();
                    citasPendientes=true;
                }
            }

            if(citasPendientes==false){
                throw new ExcepcionNoExistente("No hay citas atendidas");
            }
            return mensaje;
        }



        public JSONObject toJSONVET(){
            JSONObject jsonVet = new JSONObject();

            try {
                jsonVet.put("nombre", nombre);
                jsonVet.put("direccion",direccion);
                jsonVet.put("email_admin",emailAdmin);
                jsonVet.put("contrasenia_admin",contraseniaAdmin);
                //Empleados
                JSONArray empleadosJSON = jsonVet.getJSONArray("empleados");
                Iterator<Empleado> itE = Personal.getIterator();
                while (itE.hasNext()) {
                    empleadosJSON.put(itE.next().toJSON());
                }
                jsonVet.put("personal", empleadosJSON);


                // Duenios
                JSONArray dueniosJSON = jsonVet.getJSONArray("duenios");
                Iterator<Duenio> itD = Duenios.getIterator();
                while (itD.hasNext()) {
                    dueniosJSON.put(itD.next().toJSON());
                }
                jsonVet.put("duenios", dueniosJSON);

                // Citas

                JSONArray citasJSON = jsonVet.getJSONArray("citas");
                Iterator<Cita> itC = Citas.getIterator();
                while (itC.hasNext()) {
                    citasJSON.put(itC.next().toJSON());
                }
                jsonVet.put("citas", citasJSON);

            }catch(JSONException e){
                e.printStackTrace();
            }

            return jsonVet;
        }

        @Override
        public String toString() {
            return "Clases.Veterinaria{" +
                    "nombre='" + nombre + '\'' +
                    ", direccion='" + direccion + '\'' +
                    ", emailAdmin='" + emailAdmin + '\'' +
                    ", contraseniaAdmin='" + contraseniaAdmin + '\'' +
                    ", Personal=" + Personal +
                    ", Duenios=" + Duenios +
                    ", Citas=" + Citas +
                    '}';
        }
        public boolean ingresarEmpleado(String email, String contra)throws ExcepcionFormatoNoValido,ExcepcionNoExistente,ExcepcionNoCoincide,ExcepcionCuentaInactiva{
            boolean ingreso = false;

            Validaciones.validarFormatoContrasenia(contra);

            Iterator<Empleado> it = Personal.getIterator();
            while(it.hasNext()){
                Empleado emp = it.next();
                if(emp.getEmail().equalsIgnoreCase(email)){
                    if (!emp.isCuenta_activa()) {
                        throw new ExcepcionCuentaInactiva("La cuenta está desactivada. Contacte al administrador.");
                    }

                    ingreso = true;
                    Validaciones.validarMismaContrasenia(contra, emp.getContrasenia());
                }
            }

            if(!ingreso){
                throw new ExcepcionNoExistente("El email ingresado no se encuentra en el sistema");
            }
            return ingreso;
        }
        /// Metodos de admin
        public boolean ingresarAdmin(String email, String contrasenia){
            boolean ingresado = false;
            try {
                if (Validaciones.validarMismoEmail(email, emailAdmin) && Validaciones.validarMismaContrasenia(contrasenia, contraseniaAdmin)) {
                ingresado = true;
                }
            } catch (ExcepcionNoCoincide e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            return ingresado;
        }

        public boolean desactivarCuenta(String email){
            boolean desactivado = false;///false: la cuenta no se desactivo | true: se desactivo
            try{
            Iterator<Empleado> it = Personal.getIterator();
            while(it.hasNext()){
                Empleado emp = it.next();
                if(emp.getEmail().equalsIgnoreCase(email)){
                    if(emp.isCuenta_activa() == false){
                        throw new ExcepcionNoCoincide("La cuenta ya se encuentra desactivada");
                    }
                    emp.setCuenta_activa(false);
                    desactivado = true;
                }
            }
            if(!desactivado){
                throw new ExcepcionNoExistente("No se encontro el mail");
            }
            } catch(ExcepcionNoExistente e){
                System.out.println(e.getMessage());
            } catch (ExcepcionNoCoincide e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            return desactivado;
        }
        public boolean activarCuenta(String email){
            boolean activado = false;
            try{
                Iterator<Empleado> it = Personal.getIterator();
                while(it.hasNext()){
                    Empleado emp = it.next();
                    if(emp.getEmail().equalsIgnoreCase(email)){
                        if(emp.isCuenta_activa() == true){
                            throw new ExcepcionNoCoincide("La cuenta ya se encuentra activada");
                        }
                        emp.setCuenta_activa(true);
                        activado = true;
                    }
                }
                if(!activado){
                    throw new ExcepcionNoExistente("No se encontro el mail");
                }
            } catch(ExcepcionNoExistente e){
                System.out.println(e.getMessage());
            } catch (ExcepcionNoCoincide e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            return activado;
        }

        public void guardarDatos(){
            JSONObject datosRaiz = new JSONObject();


                JSONArray dueniosArray = JSONUtiles.gestorToArray(this.Duenios);
                JSONArray personalArray = JSONUtiles.gestorToArray(this.Personal);
                JSONArray citasArray = JSONUtiles.gestorToArray(this.Citas);

                datosRaiz.put("duenios",dueniosArray);
                datosRaiz.put("personal",personalArray);
                datosRaiz.put("citas",citasArray);

                String jsonString = JSONUtiles.serializar(datosRaiz);
                FileHandler.guardarTexto(jsonString,NOMBRE_ARCHIVO);

        } ///Consulta sobre bloque de try catch

        public void cargarDatos(){
            String jsonString = "";

                jsonString = FileHandler.cargarTexto(NOMBRE_ARCHIVO);
            /// Consulta sobre bloque try catch

            try{
            JSONObject datosRaiz = JSONUtiles.obtenerJSONObject(jsonString);
            if (datosRaiz.has("duenios")){
                JSONArray dueniosArray = datosRaiz.getJSONArray("duenios");
                List<Duenio> dueniosCargados = JSONUtiles.arrayToObjetos(dueniosArray,new Duenio());
                this.Duenios.cargarColeccion(dueniosCargados);
            }
            if (datosRaiz.has("personal")){
                JSONArray personalArray = datosRaiz.getJSONArray("personal");
                Empleado empleadoMolde = new Empleado();
                Veterinario veteterinarioMolde = new Veterinario();

                for (int i = 0; i<personalArray.length(); i++){
                    JSONObject empJSON = personalArray.getJSONObject(i);
                    if (empJSON.has("matricula")){
                        Veterinario veterinario = veteterinarioMolde.fromJSON(empJSON);
                        this.Personal.agregar(veterinario);
                    }else {
                      Empleado empleado = empleadoMolde.fromJSON(empJSON);
                      this.Personal.agregar(empleado);
                    }
                }
            }

            if (datosRaiz.has("citas")){
                JSONArray citasArray = datosRaiz.getJSONArray("citas");
                List<Cita> citasCargadas = JSONUtiles.arrayToObjetos(citasArray,new Cita());
                this.Citas.cargarColeccion(citasCargadas);
            }
            }catch (JSONException e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
/*        public boolean crearCuenta(String email, String contrasenia, String contraseniaDos)throws ExcepcionFormatoNoValido, ExcepcionNoCoincide{
            boolean cuentaValida = false;

            if(Handlers.Validaciones.validarFormatoEmail(email)){
                if (Handlers.Validaciones.validarFormatoContrasenia(contrasenia)){
                    if(Handlers.Validaciones.validarMismaContrasenia(contrasenia,contraseniaDos)){
                        cuentaValida = true;
                    }
                }
            }
            return cuentaValida;
        } */
    }
