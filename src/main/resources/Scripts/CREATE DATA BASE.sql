CREATE DATABASE IF NOT EXISTS crud_app;
USE crud_app;

/*tlb_productos: Tabla donde se almacendad los productos
clm_id: 			Nuemro entero Identificador unico del producto
clm_nombre:			Texto corto que resume la descripcion del producto
clm_descripcion:	Texto medio con la especificacion del producto
clm_precio:			Numero decial que indica el valor en moneda del producto
clm_cantidad:		Numero entero que indica el stock del producto
*/
CREATE TABLE IF NOT EXISTS tlb_productos(
	clm_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    clm_nombre VARCHAR(50),
    clm_descripcion VARCHAR(100),
    clm_precio DECIMAL,
    clm_cantidad INT
);