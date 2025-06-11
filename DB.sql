CREATE SCHEMA `reparacion_dispositivos` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'Administrador123';

GRANT ALL PRIVILEGES ON reparacion_dispositivos.* TO 'admin'@'localhost';

FLUSH PRIVILEGES;

CREATE TABLE Dispositivo(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(60) NOT NULL,
    tipo_dispo ENUM('LAPTOP','PC','CELULAR','TABLET') NOT NULL,
    observaciones VARCHAR(200)
);

CREATE TABLE Clientes(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(70) NOT NULL
);

CREATE TABLE Tecnicos(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(60) NOT NULL,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    contraseña VARCHAR(100) NOT NULL,
    especialidad ENUM('HARDWARE','SOFTWARE') NOT NULL
);

CREATE TABLE Piezas(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
	descripcion VARCHAR(150) NOT NULL,
    stock INT NOT NULL,
    costo DECIMAL(10,2) NOT NULL
);


CREATE TABLE Garantias(
	id INT PRIMARY KEY AUTO_INCREMENT,
    fecha_inicio DATE NOT NULL,
    duracion INT NOT NULL,
    fecha_fin DATE GENERATED ALWAYS AS (DATE_ADD(fecha_inicio, INTERVAL duracion DAY)) STORED,
    cobertura VARCHAR(300) NOT NULL
);

-- Almacena las herramientas usadas por las ordenes de reparacion y cuantas están en cada estado
CREATE TABLE Herramientas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    stock_disponible INT NOT NULL,
    stock_en_uso INT NOT NULL,
    stock_mantenimiento INT NOT NULL
);

CREATE TABLE Proveedores(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(40) NOT NULL,
    correo VARCHAR(60) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    direccion VARCHAR(70)
);

-- Almacena 'revisiones' y ordenes de reparacion
-- Al registrar una revision el estado es PREORDEN
-- En caso de que no quiera la reparación se usa CANCELADO
-- Si autoriza la reparacion se usa PENDIENTE
-- Al asignar un técnico se usa PROGRESO
-- Al terminar la reparación se usa FINALIZADO
-- Una revision se puede registrar sin asignar un técnico al instante, obviamente no tendría garantía aún, ni fecha de egreso, 
-- 		para esto se usarían valores NULL en la inserción
CREATE TABLE Orden_reparacion(
	id INT PRIMARY KEY AUTO_INCREMENT,
    fecha_ing DATE NOT NULL,
    fecha_eg DATE,
    tipo_falla ENUM('SOFTWARE', 'HARDWARE') NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    estado ENUM('PREORDEN','PENDIENTE','PROGRESO','FINALIZADO','CANCELADO') NOT NULL,
    fk_cliente INT,
    fk_dispositivo INT,
    fk_tecnico INT,
    fk_garantia INT,
    
    CONSTRAINT FK_CLIENTE FOREIGN KEY(fk_cliente) REFERENCES Clientes(id),
    CONSTRAINT FK_DISPOSITIVO FOREIGN KEY(fk_dispositivo) REFERENCES Dispositivo(id),
    CONSTRAINT FK_TECNICO FOREIGN KEY (fk_tecnico) REFERENCES Tecnicos	(id),
    CONSTRAINT FK_GARANTIA FOREIGN KEY (fk_garantia) REFERENCES Garantias(id)
);

-- Almacena los pagos hechos con razón de una orden de reparacion o preorden
CREATE TABLE Pagos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fk_orden INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    metodo_pago ENUM('EFECTIVO', 'TARJETA', 'TRANSFERENCIA') NOT NULL,
    
    CONSTRAINT FK_ORDEN_PAGO FOREIGN KEY (fk_orden) REFERENCES Orden_reparacion(id)
);

-- Almacena compras de piezas a los proveedores
CREATE TABLE Compras(
	id INT PRIMARY KEY AUTO_INCREMENT,
    cantidad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    total DECIMAL(12, 2) GENERATED ALWAYS AS (precio * cantidad) STORED,
    
    fk_proveedor INT,
    fk_pieza INT,
    CONSTRAINT FK_PROVEEDOR FOREIGN KEY(fk_proveedor) REFERENCES Proveedores(id),
    CONSTRAINT FK_PIEZA FOREIGN KEY(fk_pieza) REFERENCES Piezas(id)
);

-- Almacena cuantas piezas se usaron en una reparación, ejemplo: (pantalla, centro de carga,etc)
CREATE TABLE Orden_piezas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fk_orden INT NOT NULL,
    fk_pieza INT NOT NULL,
    cantidad INT NOT NULL,
    
    FOREIGN KEY (fk_orden) REFERENCES Orden_reparacion(id),
    FOREIGN KEY (fk_pieza) REFERENCES Piezas(id)
);

CREATE TABLE Orden_herramientas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fk_orden INT NOT NULL,
    fk_herramienta INT NOT NULL,
    cantidad_usada INT NOT NULL,

    FOREIGN KEY (fk_orden) REFERENCES Orden_reparacion(id),
    FOREIGN KEY (fk_herramienta) REFERENCES Herramientas(id)
);