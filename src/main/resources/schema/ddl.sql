-- Tabla Proveedores
CREATE SEQUENCE id_proveedor_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE proveedores(
    id_proveedor INT DEFAULT nextval('id_proveedor_seq'),
    nombre VARCHAR(255),
    telefono VARCHAR(10),
    correo VARCHAR(255)
);

-- Llave Primaria
ALTER TABLE proveedores ADD CONSTRAINT pk_proveedor PRIMARY KEY(id_proveedor);

-- No nulos
ALTER TABLE proveedores ALTER COLUMN id_proveedor SET NOT NULL;
ALTER TABLE proveedores ALTER COLUMN nombre SET NOT NULL;
ALTER TABLE proveedores ALTER COLUMN telefono SET NOT NULL;
ALTER TABLE proveedores ALTER COLUMN correo SET NOT NULL;

-- Restricciones de cada columna
ALTER TABLE proveedores ADD CONSTRAINT chk_telefono_format_proveedores CHECK (telefono ~ '^[0-9]{10}$');
ALTER TABLE proveedores ADD CONSTRAINT chk_correo_format_proveedores CHECK (correo ~ '^[^@]+@[^@]+\.[^@]{2,}$');
ALTER SEQUENCE id_proveedor_seq OWNED BY proveedores.id_proveedor;


-- Tabla Categorias
CREATE SEQUENCE id_categoria_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE categorias(
    id_categoria INT DEFAULT nextval('id_categoria_seq'),
    nombre VARCHAR(255)
);

-- Llave Primaria
ALTER TABLE categorias ADD CONSTRAINT pk_categoria PRIMARY KEY(id_categoria);


-- No nulos
ALTER TABLE categorias ALTER COLUMN id_categoria SET NOT NULL;
ALTER TABLE categorias ALTER COLUMN nombre SET NOT NULL;

-- Restricciones
ALTER TABLE categorias ADD CONSTRAINT uq_nombre_categoria UNIQUE (nombre);
ALTER SEQUENCE id_categoria_seq OWNED BY categorias.id_categoria;


-- Tabla Ventas
CREATE SEQUENCE id_ventas_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE ventas(
    id_venta INT DEFAULT nextval('id_ventas_seq'),
    fecha TIMESTAMP,
    metodo_pago VARCHAR(30),
    subtotal NUMERIC(10,2),
    total NUMERIC(10,2)
);

-- Llave primaria
ALTER TABLE ventas ADD CONSTRAINT pk_ventas PRIMARY KEY (id_venta);

-- No nulos
ALTER TABLE ventas ALTER COLUMN id_venta SET NOT NULL;
ALTER TABLE ventas ALTER COLUMN fecha SET NOT NULL;
ALTER TABLE ventas ALTER COLUMN metodo_pago SET NOT NULL;
ALTER TABLE ventas ALTER COLUMN subtotal SET NOT NULL;
ALTER TABLE ventas ALTER COLUMN total SET NOT NULL;

-- Restricciones de cada columna
ALTER TABLE ventas ADD CONSTRAINT chk_fecha_ventas CHECK( EXTRACT(YEAR FROM(fecha)) >= 2024 );
ALTER TABLE ventas ADD CONSTRAINT chk_subtotal_ventas CHECK (subtotal > 0);
ALTER TABLE ventas ADD CONSTRAINT chk_total_ventas CHECK (total > 0);
ALTER SEQUENCE id_ventas_seq OWNED BY ventas.id_venta;
ALTER TABLE ventas ADD CONSTRAINT chk_metodo_pago CHECK ( metodo_pago IN ('TARJETA', 'EFECTIVO') );


-- Tabla productos
CREATE SEQUENCE id_producto_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE productos(
    id_producto INT DEFAULT nextval('id_producto_seq'),
    id_categoria INT,
    id_proveedor INT,
    sku VARCHAR(50),
    nombre VARCHAR(255),
    precio NUMERIC(10,2),
    stock INT,
    activo BOOLEAN DEFAULT TRUE
);

-- Llave primaria y Llaves Foraneas
ALTER TABLE productos ADD CONSTRAINT pk_productos PRIMARY KEY (id_producto);
ALTER TABLE productos ADD CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria);
ALTER TABLE productos ADD CONSTRAINT fk_producto_proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor);


-- No nulos
ALTER TABLE productos ALTER COLUMN id_producto SET NOT NULL;
ALTER TABLE productos ALTER COLUMN id_categoria SET NOT NULL;
ALTER TABLE productos ALTER COLUMN id_proveedor SET NOT NULL;
ALTER TABLE productos ALTER COLUMN sku SET NOT NULL;
ALTER TABLE productos ALTER COLUMN nombre SET NOT NULL;
ALTER TABLE productos ALTER COLUMN precio SET NOT NULL;
ALTER TABLE productos ALTER COLUMN stock SET NOT NULL;
ALTER TABLE productos ALTER COLUMN activo SET NOT NULL;


-- Restricciones de cada columna
ALTER TABLE productos ADD CONSTRAINT chk_precio_producto CHECK (precio > 0);
ALTER TABLE productos ADD CONSTRAINT chk_stock_producto CHECK (stock >= 0);
ALTER TABLE productos ADD CONSTRAINT uq_sku_producto UNIQUE (sku);
ALTER TABLE productos ADD CONSTRAINT chk_sku_producto CHECK (sku ~ '^[A-Z0-9-]{3,50}$');

ALTER SEQUENCE id_producto_seq OWNED BY productos.id_producto;


-- Tabla venta_producto
CREATE SEQUENCE id_venta_producto_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE venta_producto(
    id_venta_producto INT DEFAULT nextval('id_venta_producto_seq'),
    id_producto INT,
    id_venta INT,
    unidades INT,
    precio_unitario NUMERIC(10,2),
    subtotal NUMERIC(10,2)
);

-- Llave primaria y Llaves Foraneas
ALTER TABLE venta_producto ADD CONSTRAINT pk_venta_producto PRIMARY KEY (id_venta_producto);
ALTER TABLE venta_producto ADD CONSTRAINT fk_venta_producto_producto FOREIGN KEY (id_producto) REFERENCES productos(id_producto);
ALTER TABLE venta_producto ADD CONSTRAINT fk_venta_producto_venta FOREIGN KEY (id_venta) REFERENCES ventas(id_venta);

-- No nulos
ALTER TABLE venta_producto ALTER COLUMN id_venta_producto SET NOT NULL;
ALTER TABLE venta_producto ALTER COLUMN id_producto SET NOT NULL;
ALTER TABLE venta_producto ALTER COLUMN id_venta SET NOT NULL;
ALTER TABLE venta_producto ALTER COLUMN unidades SET NOT NULL;
ALTER TABLE venta_producto ALTER COLUMN precio_unitario SET NOT NULL;
ALTER TABLE venta_producto ALTER COLUMN subtotal SET NOT NULL;


-- Restricciones de cada columna
ALTER TABLE venta_producto ADD CONSTRAINT chk_unidades_venta_producto  CHECK (unidades > 0);
ALTER TABLE venta_producto ADD CONSTRAINT chk_precio_unitario_venta_producto CHECK (precio_unitario > 0);
ALTER TABLE venta_producto ADD CONSTRAINT chk_subtotal_venta_producto CHECK (subtotal > 0);
ALTER TABLE venta_producto ADD CONSTRAINT uq_detalle_venta_producto UNIQUE (id_venta, id_producto);
ALTER SEQUENCE id_venta_producto_seq OWNED BY venta_producto.id_venta_producto;



-- Tabla movimientos_inventario
CREATE SEQUENCE id_movimiento_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE movimientos_inventario(
    id_movimiento INT DEFAULT nextval('id_movimiento_seq'),
    id_producto INT,
    tipo_movimiento VARCHAR(30),
    cantidad INT,
    fecha TIMESTAMP
);


-- Llave primaria y Llaves Foraneas
ALTER TABLE movimientos_inventario ADD CONSTRAINT pk_movimientos_inventario PRIMARY KEY (id_movimiento);
ALTER TABLE movimientos_inventario ADD CONSTRAINT fk_movimientos_inventario_producto FOREIGN KEY (id_producto) REFERENCES productos(id_producto);

-- No nulos
ALTER TABLE movimientos_inventario ALTER COLUMN id_producto SET NOT NULL;
ALTER TABLE movimientos_inventario ALTER COLUMN id_movimiento SET NOT NULL;
ALTER TABLE movimientos_inventario ALTER COLUMN tipo_movimiento SET NOT NULL;
ALTER TABLE movimientos_inventario ALTER COLUMN cantidad SET NOT NULL;
ALTER TABLE movimientos_inventario ALTER COLUMN fecha SET NOT NULL;


-- Restricciones de cada columna
ALTER TABLE movimientos_inventario ADD CONSTRAINT chk_cantidades_movimientos_inventario CHECK (cantidad > 0);
ALTER TABLE movimientos_inventario ADD CONSTRAINT chk_fecha_movimientos_inventario CHECK ( EXTRACT(YEAR FROM fecha) >= 2024 );
ALTER SEQUENCE id_movimiento_seq OWNED BY movimientos_inventario.id_movimiento;
ALTER TABLE movimientos_inventario ADD CONSTRAINT chk_tipo_movimiento CHECK ( tipo_movimiento IN ('ENTRADA', 'SALIDA') );


