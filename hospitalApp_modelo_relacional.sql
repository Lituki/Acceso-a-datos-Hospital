-- ============================================================
--  Sistema de Gestión Hospitalaria — HospitalApp
--  Modelo Relacional / Script de creación (MySQL / MariaDB)
-- ============================================================

CREATE DATABASE IF NOT EXISTS hospital
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE hospital;

-- 1. ESPECIALIDAD
CREATE TABLE especialidad (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    PRIMARY KEY (id)
);

-- 2. MEDICO
CREATE TABLE medico (
    id               BIGINT      NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(100) NOT NULL,
    apellido         VARCHAR(100) NOT NULL,
    num_colegiado    VARCHAR(50) NOT NULL UNIQUE,
    telefono         VARCHAR(100),
    email            VARCHAR(100),
    id_especialidad  BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_medico_especialidad
        FOREIGN KEY (id_especialidad) REFERENCES especialidad(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- 3. PACIENTE
CREATE TABLE paciente (
    id               BIGINT      NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(100) NOT NULL,
    apellido         VARCHAR(100) NOT NULL,
    dni              VARCHAR(50) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    telefono         VARCHAR(100),
    email            VARCHAR(100),
    direccion        VARCHAR(255),
    PRIMARY KEY (id)
);

-- 4. CITA
CREATE TABLE cita (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    fecha_hora    DATETIME     NOT NULL,
    motivo        VARCHAR(255) NOT NULL,
    observaciones TEXT,
    id_paciente   BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cita_paciente
        FOREIGN KEY (id_paciente) REFERENCES paciente(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- 5. Tabla puente CITA <-> MEDICO  (N:M)
CREATE TABLE cita_medico (
    id_cita   BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    PRIMARY KEY (id_cita, id_medico),
    CONSTRAINT fk_cm_cita   FOREIGN KEY (id_cita)   REFERENCES cita(id)   ON DELETE CASCADE,
    CONSTRAINT fk_cm_medico FOREIGN KEY (id_medico) REFERENCES medico(id) ON DELETE CASCADE
);

-- 6. HABITACION
CREATE TABLE habitacion (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    numero      VARCHAR(50) NOT NULL UNIQUE,
    tipo        VARCHAR(50) NOT NULL,   -- Individual, Doble, Triple, UCI...
    capacidad   INT NOT NULL,
    planta      VARCHAR(50) NOT NULL,
    disponible  TINYINT(1)  NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

-- 7. INGRESO
CREATE TABLE ingreso (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    fecha_ingreso   DATE   NOT NULL,
    fecha_alta      DATE,
    motivo          VARCHAR(255) NOT NULL,
    observaciones   TEXT,
    id_paciente     BIGINT NOT NULL,
    id_habitacion   BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_ingreso_paciente   FOREIGN KEY (id_paciente)   REFERENCES paciente(id)   ON DELETE RESTRICT,
    CONSTRAINT fk_ingreso_habitacion FOREIGN KEY (id_habitacion) REFERENCES habitacion(id) ON DELETE RESTRICT
);

-- 8. MEDICAMENTO
CREATE TABLE medicamento (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(150) NOT NULL UNIQUE,
    composicion VARCHAR(255),
    precio      DECIMAL(10,2),
    unidad      VARCHAR(50),  -- Comprimido, ml, mg
    PRIMARY KEY (id)
);

-- 9. TRATAMIENTO
CREATE TABLE tratamiento (
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    descripcion    VARCHAR(255) NOT NULL,
    duracion       INT NOT NULL,         -- en días
    frecuencia     VARCHAR(100) NOT NULL, -- 3 veces al día, cada 8 horas...
    indicaciones   TEXT,
    id_cita        BIGINT       NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_tratamiento_cita FOREIGN KEY (id_cita) REFERENCES cita(id) ON DELETE SET NULL
);

-- 10. Tabla puente TRATAMIENTO <-> MEDICAMENTO  (N:M)
CREATE TABLE tratamiento_medicamento (
    id_tratamiento BIGINT NOT NULL,
    id_medicamento BIGINT NOT NULL,
    PRIMARY KEY (id_tratamiento, id_medicamento),
    CONSTRAINT fk_tm_tratamiento FOREIGN KEY (id_tratamiento) REFERENCES tratamiento(id) ON DELETE CASCADE,
    CONSTRAINT fk_tm_medicamento FOREIGN KEY (id_medicamento) REFERENCES medicamento(id) ON DELETE CASCADE
);
