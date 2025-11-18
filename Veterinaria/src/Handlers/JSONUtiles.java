package Handlers;

import Clases.Gestor;
import Clases.Identificable;
import Clases.JSONable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class JSONUtiles {
    /// Aca serializamos y deserializamos nuestros datos
    /// Metodo que convierte el texto del archivo en un JSONObject
    public static JSONObject obtenerJSONObject(String contenidoJSON) throws JSONException {
        if (contenidoJSON == null || contenidoJSON.isEmpty()) {
            return new JSONObject(); //Se devuelve un new JSONObject para evitar manejar una NullPointerException en el llamado de Clases.Veterinaria
        }                            //Si el archivo está vacio, empieza con un conjuntos de datos vacío y no con un error
        JSONTokener tokener = new JSONTokener(contenidoJSON);
        return new JSONObject(tokener);
    }

    /// Metodo que nos sirve para agarrar nuestro JSONObject de veterinaria y pasarlo a un string para un posterior guardado en el archivo
    public static String serializar(JSONObject objeto) {
        return objeto.toString();
    }

    /// Metodo que nos permite conseguir un JSONArray desde una coleccion generica (Serializacion)
    public static <T extends JSONable<T> & Identificable> JSONArray gestorToArray(Gestor<T> gestor) {
        JSONArray jsonArray = new JSONArray();

        for (T elemento : gestor.obtenerColeccion()) {
            jsonArray.put(elemento.toJSON());
        }

        return jsonArray;
    }
    /// Metodo que nos permite conseguir una lista desde un JSONArray (Deserializacion)
    public static <T extends JSONable<T>> List<T> arrayToObjetos(JSONArray jsonArray, T tipoObjeto) throws JSONException {
        List<T> lista = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjeto = jsonArray.getJSONObject(i);
                lista.add(tipoObjeto.fromJSON(jsonObjeto));
            }
        }
        return lista;
    }
}
