package Clases;

import java.util.HashSet;
import java.util.Iterator;

public class Gestor <T extends Identificable>  {
    private final HashSet<T> elementos;

    public Gestor(){
        elementos = new HashSet<>();
    }

    public void agregar(T t){
        elementos.add(t);
    }

    public boolean existe(T e){
        return elementos.contains(e);
    }

    public T obtenerPorIdentificador(int dniABuscar){
        for(T e: elementos){
            if(e.getId() ==dniABuscar){
                return e;
            }
        }
        return null;
    }

    public String listar(){
        StringBuilder sb = new StringBuilder(" ");
        for (T elemento: elementos){
            sb.append(elemento.toString()).append("\n");
        }
        return sb.toString();
    }



public Iterator <T> getIterator(){

        return elementos.iterator();
    }


    public String buscarPorId(int id) // con este buscamos por DNI
    {
        String mensaje = " ";
        for(T t: elementos) if(t.getId()==id) return t.toString();
        return mensaje;
    }

public HashSet<T> obtenerColeccion(){
        return new HashSet<>(elementos);
}

public void cargarColeccion(Iterable<T> nuevosElementos){
        for (T elemento : nuevosElementos){
            this.agregar(elemento);
        }
}

}
