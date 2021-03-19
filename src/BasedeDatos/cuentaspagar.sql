CREATE DOMAIN dom_direcion AS VARCHAR(100);
CREATE DOMAIN dom_telefono AS INT;

--1) Tabla de proveedores

CREATE TABLE proveedores(
	rif INT NOT NULL,
	razonsocial VARCHAR(30) NOT NULL,
	direccionp dom_direcion NOT NULL,
	telefonop dom_telefono NOT NULL,
	statusp VARCHAR(1) NOT NULL
);



ALTER TABLE proveedores 
ADD CONSTRAINT pk_rif PRIMARY KEY(rif);

ALTER TABLE proveedores
ADD CONSTRAINT uq_rsocial UNIQUE(razonsocial);

ALTER TABLE proveedores
ADD CONSTRAINT ck_statusp 
CHECK (statusp IN ('A','E')); --A = activo ; E = eliminado

--2 tabla tipo_articulos

CREATE TABLE tipo_articulos(
	codigota VARCHAR(10) NOT NULL,
	descripcionta VARCHAR(40) NOT NULL,
	categoriata CHAR(1) -- CHECK VALUE B= articulos basicos , A = Regulares
);

ALTER TABLE tipo_articulos 
ADD CONSTRAINT pk_codigota
PRIMARY KEY(codigota);

ALTER TABLE tipo_articulos
ADD CONSTRAINT uq_descripcion
UNIQUE(descripcionta);

ALTER TABLE tipo_articulos
ADD CONSTRAINT ck_categoriata
CHECK  (categoriata IN('B','A'));

--3) Tabla de articulos

CREATE TABLE articulos(
	codigoa VARCHAR(10) NOT NULL,
	nombrea VARCHAR(20) NOT NULL,
	descripciona VARCHAR(100) NOT NULL,
	precio FLOAT NOT NULL,
	existencia INT NOT NULL,
        codigota VARCHAR(10) NOT NULL
);

ALTER TABLE articulos 
ADD CONSTRAINT pk_codigoa PRIMARY KEY(codigoa);

ALTER TABLE articulos
ADD CONSTRAINT uq_nombrea UNIQUE(nombrea);

ALTER TABLE articulos
ADD CONSTRAINT fk_codigocat FOREIGN KEY(codigota)
REFERENCES tipo_articulos(codigota);

--4) Facturas


CREATE TABLE facturas(
	n_factura SERIAL NOT NULL,
	rifproveedor INT NOT NULL,
	fecha_creada DATE NOT NULL,
	fecha_vencimiento DATE NOT NULL,
	tipoPago CHAR(1) NOT NULL,   --CHECK VALUE ('D','E','O') D = dolares ; E = Euros ; O = Otro
	statusf CHAR(1) NOT NULL, --CHECK VALUE ('A','P','V'); A= ACTIVA ; P = PAGADA ; V = VENCIDA
        total FLOAT NOT NULL
);

ALTER TABLE facturas 
ADD CONSTRAINT pk_nfactura
PRIMARY KEY(n_factura);

ALTER TABLE facturas
ADD CONSTRAINT fk_rif 
FOREIGN KEY (rifproveedor)
REFERENCES proveedores(rif);

ALTER TABLE facturas
ADD CONSTRAINT ck_statusf
CHECK (facturas.statusf IN('A','P','V'));

--5) tabla elementos_facturas

CREATE TABLE elementos_facturas(
	n_factura SERIAL NOT NULL,
	codigoa VARCHAR(10) NOT NULL,
	cantidad INT NOT NULL,
	precio FLOAT NOT NULL
);

ALTER TABLE elementos_facturas
ADD CONSTRAINT pk_nfacturas_codigoa
PRIMARY KEY(n_factura,codigoa);

ALTER TABLE elementos_facturas
ADD CONSTRAINT fk_nfactura
FOREIGN KEY (n_factura)
REFERENCES facturas(n_factura);

ALTER TABLE elementos_facturas
ADD CONSTRAINT fk_codigoa 
FOREIGN KEY (codigoa)
REFERENCES articulos(codigoa);





