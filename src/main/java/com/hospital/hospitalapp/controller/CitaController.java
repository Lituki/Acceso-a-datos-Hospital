package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Cita;
import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.service.CitaService;
import com.hospital.hospitalapp.service.MedicoService;
import com.hospital.hospitalapp.service.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    public CitaController(CitaService citaService, PacienteService pacienteService, MedicoService medicoService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("citas", citaService.obtenerTodas());
        return "citas/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        Cita cita = new Cita();
        cita.setPaciente(new Paciente());
        model.addAttribute("cita", cita);
        populateFormModel(model, new ArrayList<>());
        return "citas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cita") Cita cita,
                          BindingResult bindingResult,
                          @RequestParam(required = false) List<Long> medicoIds,
                          Model model) {
        if (cita.getPaciente() == null || cita.getPaciente().getId() == null) {
            bindingResult.rejectValue("paciente", "required", "Debe seleccionar un paciente");
        }
        if (bindingResult.hasErrors()) {
            populateFormModel(model, medicoIds);
            return "citas/form";
        }
        citaService.guardar(cita, medicoIds);
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Cita cita = citaService.obtenerParaFormulario(id)
            .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));
        if (cita.getPaciente() == null) {
            cita.setPaciente(new Paciente());
        }
        model.addAttribute("cita", cita);
        populateFormModel(model, obtenerIdsMedicos(cita));
        return "citas/form";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        citaService.eliminar(id);
        return "redirect:/citas";
    }

    private void populateFormModel(Model model, List<Long> selectedMedicoIds) {
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        model.addAttribute("medicos", medicoService.obtenerTodos());
        model.addAttribute("selectedMedicoIds", selectedMedicoIds == null ? new ArrayList<>() : selectedMedicoIds);
    }

    private List<Long> obtenerIdsMedicos(Cita cita) {
        if (cita.getMedicos() == null) {
            return new ArrayList<>();
        }
        return cita.getMedicos().stream()
            .map(medico -> medico.getId())
            .toList();
    }
}
