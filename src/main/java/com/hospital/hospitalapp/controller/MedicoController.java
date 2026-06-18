package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Medico;
import com.hospital.hospitalapp.model.Especialidad;
import com.hospital.hospitalapp.service.EspecialidadService;
import com.hospital.hospitalapp.service.MedicoService;
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

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;
    private final EspecialidadService especialidadService;

    public MedicoController(MedicoService medicoService, EspecialidadService especialidadService) {
        this.medicoService = medicoService;
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medicos", medicoService.obtenerTodos());
        return "medicos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        Medico medico = new Medico();
        medico.setEspecialidad(new Especialidad());
        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidadService.obtenerTodas());
        return "medicos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("medico") Medico medico,
                          BindingResult bindingResult,
                          Model model) {
        if (medico.getEspecialidad() == null || medico.getEspecialidad().getId() == null) {
            bindingResult.rejectValue("especialidad", "required", "Debe seleccionar una especialidad");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("especialidades", especialidadService.obtenerTodas());
            return "medicos/form";
        }
        medicoService.guardar(medico);
        return "redirect:/medicos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Medico medico = medicoService.obtenerParaFormulario(id)
            .orElseThrow(() -> new EntityNotFoundException("Medico no encontrado"));
        if (medico.getEspecialidad() == null) {
            medico.setEspecialidad(new Especialidad());
        }
        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidadService.obtenerTodas());
        return "medicos/form";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        medicoService.eliminar(id);
        return "redirect:/medicos";
    }
}
