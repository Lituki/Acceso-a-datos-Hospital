package com.hospital.hospitalapp.repository;

import com.hospital.hospitalapp.model.Cita;
import com.hospital.hospitalapp.model.Especialidad;
import com.hospital.hospitalapp.model.Habitacion;
import com.hospital.hospitalapp.model.Ingreso;
import com.hospital.hospitalapp.model.Medicamento;
import com.hospital.hospitalapp.model.Medico;
import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.model.Tratamiento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {
    "spring.datasource.url=jdbc:h2:mem:hospitaltest;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=false"
})
@Transactional
class RepositoryIntegrationTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Test
    void pacienteRepositoryShouldSaveAndSearchByDni() {
        Paciente paciente = persistPaciente("Ana", "Lopez", "12345678A");

        assertThat(pacienteRepository.findByDni("12345678A")).contains(paciente);
        assertThat(pacienteRepository.findAll()).extracting(Paciente::getDni).contains("12345678A");
    }

    @Test
    void medicoRepositoryShouldFindByEspecialidadAndCollegiateNumber() {
        Especialidad especialidad = persistEspecialidad("Cardiologia");
        Medico medico = persistMedico("Laura", "Gomez", "COL-001", especialidad);

        assertThat(medicoRepository.findByNumColegiado("COL-001")).contains(medico);
        assertThat(medicoRepository.findByEspecialidadId(especialidad.getId())).contains(medico);
        assertThat(medicoRepository.findAll()).extracting(Medico::getNumColegiado).contains("COL-001");
    }

    @Test
    void citaRepositoryShouldFindByPacienteAndMedico() {
        Paciente paciente = persistPaciente("Luis", "Perez", "87654321B");
        Especialidad especialidad = persistEspecialidad("Dermatologia");
        Medico medico = persistMedico("Marta", "Ruiz", "COL-002", especialidad);

        Cita cita = new Cita();
        cita.setFechaHora(LocalDateTime.of(2026, 6, 18, 10, 30));
        cita.setMotivo("Revision");
        cita.setPaciente(paciente);
        cita.setMedicos(List.of(medico));
        cita = citaRepository.saveAndFlush(cita);

        assertThat(citaRepository.findByPacienteIdOrderByFechaHoraDesc(paciente.getId())).contains(cita);
        assertThat(citaRepository.findCitasByMedicoId(medico.getId())).contains(cita);
        assertThat(citaRepository.findAll()).contains(cita);
    }

    @Test
    void habitacionAndIngresoRepositoriesShouldTrackAvailabilityAndActiveAdmissions() {
        Paciente paciente = persistPaciente("Sara", "Diaz", "11223344C");
        Habitacion habitacion = persistHabitacion("101", "Individual", 1, "1");

        Ingreso ingreso = new Ingreso();
        ingreso.setFechaIngreso(LocalDate.now());
        ingreso.setMotivo("Observacion");
        ingreso.setPaciente(paciente);
        ingreso.setHabitacion(habitacion);
        ingreso = ingresoRepository.saveAndFlush(ingreso);

        habitacion.setDisponible(false);
        habitacionRepository.saveAndFlush(habitacion);

        assertThat(habitacionRepository.findDisponibles()).doesNotContain(habitacion);
        assertThat(ingresoRepository.findByFechaAltaIsNull()).contains(ingreso);
        assertThat(ingresoRepository.findByPacienteId(paciente.getId())).contains(ingreso);
        assertThat(ingresoRepository.findAll()).contains(ingreso);
    }

    @Test
    void medicamentoAndTratamientoRepositoriesShouldHandleJoinTables() {
        Paciente paciente = persistPaciente("Elena", "Soto", "55667788D");
        Especialidad especialidad = persistEspecialidad("Pediatria");
        Medico medico = persistMedico("Jorge", "Martin", "COL-003", especialidad);

        Cita cita = new Cita();
        cita.setFechaHora(LocalDateTime.of(2026, 6, 19, 9, 0));
        cita.setMotivo("Chequeo");
        cita.setPaciente(paciente);
        cita.setMedicos(List.of(medico));
        cita = citaRepository.saveAndFlush(cita);

        Medicamento medicamento = new Medicamento();
        medicamento.setNombre("Paracetamol");
        medicamento.setComposicion("500mg");
        medicamento.setPrecio(new BigDecimal("3.50"));
        medicamento.setUnidad("comprimido");
        medicamento = medicamentoRepository.saveAndFlush(medicamento);

        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setDescripcion("Tratamiento base");
        tratamiento.setDuracion(5);
        tratamiento.setFrecuencia("Cada 8 horas");
        tratamiento.setCita(cita);
        tratamiento.setMedicamentos(List.of(medicamento));
        tratamiento = tratamientoRepository.saveAndFlush(tratamiento);

        assertThat(medicamentoRepository.findByNombre("Paracetamol")).contains(medicamento);
        assertThat(medicamentoRepository.findAll()).contains(medicamento);
        assertThat(tratamientoRepository.findByCitaId(cita.getId())).contains(tratamiento);
        assertThat(tratamientoRepository.findAll()).contains(tratamiento);
    }

    private Paciente persistPaciente(String nombre, String apellido, String dni) {
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setTelefono("600000000");
        paciente.setEmail(nombre.toLowerCase() + "@example.com");
        paciente.setDireccion("Calle Falsa 123");
        return pacienteRepository.saveAndFlush(paciente);
    }

    private Especialidad persistEspecialidad(String nombre) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(nombre);
        especialidad.setDescripcion(nombre + " descripcion");
        return especialidadRepository.saveAndFlush(especialidad);
    }

    private Medico persistMedico(String nombre, String apellido, String colegiado, Especialidad especialidad) {
        Medico medico = new Medico();
        medico.setNombre(nombre);
        medico.setApellido(apellido);
        medico.setNumColegiado(colegiado);
        medico.setTelefono("611111111");
        medico.setEmail(nombre.toLowerCase() + "@hospital.com");
        medico.setEspecialidad(especialidad);
        return medicoRepository.saveAndFlush(medico);
    }

    private Habitacion persistHabitacion(String numero, String tipo, Integer capacidad, String planta) {
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(numero);
        habitacion.setTipo(tipo);
        habitacion.setCapacidad(capacidad);
        habitacion.setPlanta(planta);
        habitacion.setDisponible(true);
        return habitacionRepository.saveAndFlush(habitacion);
    }
}
