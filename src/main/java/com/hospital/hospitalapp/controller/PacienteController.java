package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Paciente;
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

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        return "pacientes/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("paciente") Paciente paciente,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "pacientes/form";
        }
        pacienteService.guardar(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteService.obtenerPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        model.addAttribute("paciente", paciente);
        return "pacientes/form";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return "redirect:/pacientes";
    }
}
