package Clases;

import org.json.JSONObject;
public interface JSONable<T>{
    JSONObject toJSON ();
    T fromJSON (JSONObject t);
}
