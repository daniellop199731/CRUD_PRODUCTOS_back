package com.example.CRUD_PRODUCTOS.utilidades;

import com.example.CRUD_PRODUCTOS.entidades.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Utilidades {

    public Utilidades(){};

    /**
     * Metodo que da solucion a :
     * Haz una función que reciba un dato numérico y que retorne un listado con las combinaciones de nombres de
     * productos cuya suma de precios sea menor o igual al valor ingresado, e incluir el valor de dicha suma (mínimo
     * 2 y máximo 3 productos en la combinación). El listado debe estar ordenado descendentemente por la suma
     * de los precios de los productos, y ser de máximo 5 elementos. En caso de no haber combinaciones que
     * cumplan con este criterio, el listado será vacío. Por ejemplo, para cuatro productos A, B, C y D con valores de
     * 2, 4, 6 y 8 respectivamente, al ingresar el valor 10 se debe obtener el listado con las combinaciones [[A, D, 10],
     * [B, C, 10], [A, C, 8], [A, B, 6]].
     * @param listaProductos lista de productos para encontrar las conbinaciones
     * @param valor valor tope y de referencia para formas las combinaciones
     * @return lista con las combinaciones encontradas
     * @throws Exception
     */
    public List<Vector<String>> ejercicioLogico(List<Producto> listaProductos, int valor) throws Exception{
        List<Vector<String>> listaResultado = new ArrayList<Vector<String>>();

        //Maximo de combinaciones
        int maxElemento = 5;

        //Variable para almacenar la suma de precios para la copmbinacion en cuestion
        double sumaPrecios = 0;

        //Vector de String que ayuda a almacenar la combinacion en la lista resultante
        Vector<String> combinacion = new Vector<>();

        //Vector con los items ya usados de la primera iteracion
        Vector<String> usadasLst1 = new Vector<>();
        //Vector con los items ya usados de la segunda iteracion
        Vector<String> usadasLst2 = new Vector<>();

        try{
            //Primera iteracion
            for (Producto producto1: listaProductos) {
                //Segunda iteracion
                for (Producto producto2: listaProductos) {
                    //Verifico que el elemento a iterar no haya sido usado en la primera iteracion
                    //esto con el fin de no repetir combinaciones
                    if(usadasLst1.contains(producto2.getNombre())){
                        continue;
                    }
                    //Verifico que los nombre de los productos no sean iguales
                    if(!producto1.getNombre().equals(producto2.getNombre())){
                        //Verifico que la suma de los precios de los dos primeros productos sea menor al valor pasado por parametro
                        //esto indica que posiblemente se pueda agregar un tercer producto a la combinacion
                        if(producto1.getPrecio()+producto2.getPrecio() < valor){
                            for (Producto producto3: listaProductos) {
                                //Verifico que el elemento a iterar no haya sido usado en la segunda iteracion
                                //esto con el fin de no repetir combinaciones
                                if(usadasLst2.contains(producto3.getNombre())){
                                    continue;
                                }
                                //Verifico que los nombre de los productos no sean iguales
                                if(!producto1.getNombre().equals(producto3.getNombre())
                                        && !producto2.getNombre().equals(producto3.getNombre())){
                                    //Verifico que la suma de los precios de los tres primeros productos sea menor o igual al valor pasado por parametro
                                    //Si esto se cumple se guarda la combinacion de los tres productos
                                    if(producto1.getPrecio()+producto2.getPrecio()+producto3.getPrecio() <= valor){
                                        combinacion.add(producto1.getNombre());
                                        combinacion.add(producto2.getNombre());
                                        combinacion.add(producto3.getNombre());
                                        sumaPrecios = producto1.getPrecio() + producto2.getPrecio()+producto3.getPrecio();
                                        combinacion.add(sumaPrecios+"");
                                        //Se verifica que la combinacion no se haya guardado antes
                                        if(listaResultado.contains(combinacion)){
                                            combinacion = new Vector<>();
                                            continue;
                                        }
                                        //Se guarda el nombre del producto como ya usado
                                        if(!usadasLst2.contains(producto2.getNombre())){
                                            usadasLst2.add(producto2.getNombre());
                                        }
                                        //Se guadar la combinacion
                                        listaResultado.add(combinacion);
                                        combinacion = new Vector<>();

                                    } else if(producto1.getPrecio()+producto2.getPrecio() <= valor){
                                        combinacion.add(producto1.getNombre());
                                        combinacion.add(producto2.getNombre());
                                        sumaPrecios = producto1.getPrecio() + producto2.getPrecio();
                                        combinacion.add(sumaPrecios+"");
                                        //Se verifica que la combinacion no se haya guardado antes
                                        if(listaResultado.contains(combinacion)){
                                            combinacion = new Vector<>();
                                            continue;
                                        }
                                        //Se guarda el nombre del producto como ya usado
                                        if(!usadasLst1.contains(producto1.getNombre())){
                                            usadasLst1.add(producto1.getNombre());
                                        }
                                        //Se guarda el nombre del producto como ya usado
                                        if(!usadasLst2.contains(producto2.getNombre())){
                                            usadasLst2.add(producto1.getNombre());
                                        }
                                        //Se guadar la combinacion
                                        listaResultado.add(combinacion);
                                        combinacion = new Vector<>();
                                    }
                                }
                            }
                        } else if(producto1.getPrecio()+producto2.getPrecio() == valor){
                            //Si no se pregunta si es igual lo que indica que se encontro una combinacion valida
                            combinacion.add(producto1.getNombre());
                            combinacion.add(producto2.getNombre());
                            sumaPrecios = producto1.getPrecio() + producto2.getPrecio();
                            combinacion.add(sumaPrecios+"");
                            //Se verifica que la combinacion no se haya guardado antes
                            if(listaResultado.contains(combinacion)){
                                combinacion = new Vector<>();
                                continue;
                            }
                            //Se guarda el nombre del producto como ya usado
                            if(!usadasLst1.contains(producto1.getNombre())){
                                usadasLst1.add(producto1.getNombre());
                            }
                            //Se guarda el nombre del producto como ya usado
                            if(!usadasLst2.contains(producto2.getNombre())){
                                usadasLst2.add(producto1.getNombre());
                            }
                            //Se guadar la combinacion
                            listaResultado.add(combinacion);
                            combinacion = new Vector<>();
                        }
                    }
                }
            }
            //Aca se realiza la acotacion del maximo de conbinaciones requeridas
            if(listaResultado.size() > maxElemento) {
                for (int i = listaResultado.size() - 1; i >= 0; i--) {
                    if (i == maxElemento - 1) {
                        break;
                    }
                    listaResultado.remove(i);
                }
            }

            //Aca se realiza el orden de la lista resultante
            Vector<String> vecAux;
            for(int i = 0; i < listaResultado.size(); i++){
                for(int j = 0; j < listaResultado.size(); j++){
                    if(Double.valueOf(listaResultado.get(i).get(listaResultado.get(i).size()-1))
                            >
                            Double.valueOf(listaResultado.get(j).get(listaResultado.get(j).size()-1))){
                        vecAux = listaResultado.get(i);
                        listaResultado.set(i, listaResultado.get(j));
                        listaResultado.set(j, vecAux);
                    }
                }
            }

        } catch (Exception e){
            throw e;
        }

        //Retorna la lista con las combinaciones validas
        return listaResultado;
    }
}
