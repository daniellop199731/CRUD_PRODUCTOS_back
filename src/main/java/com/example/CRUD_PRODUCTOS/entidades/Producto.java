package com.example.CRUD_PRODUCTOS.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tlb_productos")
/**
 * Clase Producto, que representa un producto
 * Se realiza mapeo con JPA para facilitar el acceso a datos
 */
public class Producto {

    /**
     * Nuemro entero Identificador unico del producto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clm_id")
    private int id;

    /**
     * Texto corto que resume la descripcion del producto
     */
    @Column(name = "clm_nombre")
    private String nombre;

    /**
     * Texto medio con la especificacion del producto
     */
    @Column(name = "clm_descripcion")
    private String descripcion;

    /**
     * Numero decial que indica el valor en moneda del producto
     */
    @Column(name = "clm_precio")
    private double precio;

    /**
     * Numero entero que indica el stock del producto
     */
    @Column(name = "clm_cantidad")
    private int cantidad;

}
