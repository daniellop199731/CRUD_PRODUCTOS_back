package com.example.CRUD_PRODUCTOS.controladores;

import com.example.CRUD_PRODUCTOS.entidades.Producto;
import com.example.CRUD_PRODUCTOS.repositorio.RepositorioProducto;
import com.example.CRUD_PRODUCTOS.utilidades.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Clase controladora que expone los servicios disponibles
 */
@RestController
public class ControladorPrincipal {

    @Autowired
    RepositorioProducto repositorioProducto;

    @GetMapping("holaMundo")
    public String saludo(){
        return "hola mundo";
    }

    /**
     * Metodo GET que optiene todos los productos registrados
     * @return Lista con todos los productos registrados
     */
    @GetMapping("productos")
    @ResponseBody
    public ResponseEntity listarTodosLosProductos(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body((List<Producto>) repositorioProducto.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo que optiene productos por un criterio de busqueda, primero por id, y si el parametro no es numerico,
     * realiza la busqueda por coincidencia por nombre o por descripcion
     * @param busqueda criterio de busqueda
     * @return Lista con todos los productos encontrados que coincidan con el criterio de busqueda
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("productos/{busqueda}")
    @ResponseBody
    public ResponseEntity productoPorId(@PathVariable("busqueda") String busqueda){
        try{
            try {
                return ResponseEntity.status(HttpStatus.OK).body(repositorioProducto.findById(Integer.valueOf(busqueda)));
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.OK).body(repositorioProducto.buscarProductos(busqueda));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo que optiene el valor total del inventario
     * @return valor numerico que indica el valor total del inventario
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("valorInventario")
    public ResponseEntity valorInventario(){
        double valorInventario = 0;
        List<Producto> listaProductos = new ArrayList<Producto>();
        try{
            listaProductos = (List<Producto>) repositorioProducto.findAll();
            for (Producto producto : listaProductos ) {
                valorInventario += producto.getCantidad() * producto.getPrecio();
            }
            return ResponseEntity.status(HttpStatus.OK).body(valorInventario);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo que ejecuta el ejercicio logico
     * Haz una función que reciba un dato numérico y que retorne un listado con las combinaciones de nombres de
     * productos cuya suma de precios sea menor o igual al valor ingresado, e incluir el valor de dicha suma (mínimo
     * 2 y máximo 3 productos en la combinación). El listado debe estar ordenado descendentemente por la suma
     * de los precios de los productos, y ser de máximo 5 elementos. En caso de no haber combinaciones que
     * cumplan con este criterio, el listado será vacío. Por ejemplo, para cuatro productos A, B, C y D con valores de
     * 2, 4, 6 y 8 respectivamente, al ingresar el valor 10 se debe obtener el listado con las combinaciones [[A, D, 10],
     * [B, C, 10], [A, C, 8], [A, B, 6]].
     * @param valor valor tope y de referencia para formas las combinaciones
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("ejercicioLogico/{param}")
    public ResponseEntity ejercicioLogico(@PathVariable("param") int valor){

        Utilidades utilidades = new Utilidades();
        List<Producto> listaProductos = new ArrayList<Producto>();

        try{
            listaProductos = (List<Producto>) repositorioProducto.productosEjecicioLogicp(valor);
            return ResponseEntity.status(HttpStatus.OK).body(utilidades.ejercicioLogico(listaProductos,valor));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo POST que crea un nuevo producto
     * @param producto Objeto con los parametros del producto a crear
     * @return Objeto con los datos del producto creado
     */
    @PostMapping("producto")
    public ResponseEntity crearProducto(@RequestBody Producto producto){
        try{
            if(producto.getNombre() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un nombre");
            }
            if(producto.getNombre().equals("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un nombre");
            }
            if(producto.getDescripcion() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar una descripcion");
            }
            if(producto.getDescripcion().equals("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar una descripcion");
            }
            if(producto.getPrecio() <= 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un precio");
            }
            if(producto.getCantidad() <= 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresas una cantidad");
            }
            return ResponseEntity.status(HttpStatus.OK).body(repositorioProducto.save(producto));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo que actualiza un producto
     * @param producto Objeto con los datos del producto a actualizar
     * @return Objeto con los datos del producto ya actualizado
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("producto")
    public ResponseEntity modificarProducto(@RequestBody Producto producto){
        try{
            if(producto.getId() <= 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un id");
            }
            if(producto.getNombre() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un nombre");
            }
            if(producto.getNombre().equals("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un nombre");
            }
            if(producto.getDescripcion() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar una descripcion");
            }
            if(producto.getDescripcion().equals("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar una descripcion");
            }
            if(producto.getPrecio() <= 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresar un precio");
            }
            if(producto.getCantidad() <= 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ingresas una cantidad");
            }
            return ResponseEntity.status(HttpStatus.OK).body(repositorioProducto.save(producto));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo que elimina un producto
     * @param id Id del producto a eliminar
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("producto/{id}")
    public ResponseEntity eliminarProducto(@PathVariable("id") int id){
        try{
            if(!repositorioProducto.findById(id).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Producto con id "+id+" no existe");
            }
            repositorioProducto.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Producto con id "+id+" eliminado");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
