package Handlers;

import java.io.*;

public class FileHandler {

    // Metodos de lectura y escritura de texto en el archivo para mantener una correcta separacion de responsabilidades entre
    //  nuestra clase Handlers.FileHandler y nuestra clase Handlers.JSONUtiles

    /// Lectura de nuestro archivo
    public static String cargarTexto(String nombreArchivo){
        BufferedReader entrada = null;
        StringBuilder contenido = new StringBuilder();
        String linea;

        try{
            entrada = new BufferedReader(new FileReader(nombreArchivo));
            while((linea = entrada.readLine()) != null){
            contenido.append(linea);
            }
        } catch (IOException e){
            System.out.println("Error al leer el archivo"+ nombreArchivo+ " "+ e.getMessage());
        } finally {
            if(entrada != null){
                try{
                    entrada.close();
                } catch (IOException e){
                    System.out.println("Error al cerrar el archivo");
                }
            }
        }

        return contenido.toString(); // Devuelve todo el archivo en un string para que otra clase (En Handlers.JSONUtiles) lo utilice
    }

    /// Guardado de nuestro archivo
    public static void guardarTexto (String contenido, String nombreArchivo){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(nombreArchivo));
            bw.write(contenido);
            bw.flush();
        } catch (IOException e){
            System.out.println("Error al guardar el archivo" + nombreArchivo + " "+e.getMessage());
        } finally { // Cerrado del archivo
            if(bw != null){
                try{
                    bw.close();
                }catch (IOException e){
                    System.out.println("Error al cerrar el archivo");
                }
            }
        }
    }
}
