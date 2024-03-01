package com.example.CRUD_PRODUCTOS.repositorio;

import com.example.CRUD_PRODUCTOS.entidades.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface que extienede de CrudRepository que ayuda con el acceso a datos y consultas personalizadas
 */
public interface RepositorioProducto extends CrudRepository<Producto, Integer> {

    /**
     * Metodo que realiza una busqueda por nombre o por descripcion
     * @param busqueda texto que se busca posible conincidencia por nombre o por descripcion
     * @return lista de productos que coincidan en nombre o descripcion por el valor de busqueda
     */
    @Query("select p from Producto p where p.nombre like %:busqueda% or p.descripcion like %:busqueda%")
    List<Producto> buscarProductos(@Param("busqueda") String busqueda);

    /**
     * Metodo que realiza busqueda de los productos con precio menor al valor enviado por parametro
     * @param valor numero entero para la busqueda
     * @return lista de productos que coincidan con el critero de la busqueda
     */
    @Query("select p from Producto p where p.precio < :valor")
    List<Producto> productosEjecicioLogicp(@Param("valor") int valor);

}
