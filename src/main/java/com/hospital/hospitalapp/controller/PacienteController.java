package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pacientes", pacienteRepository.findAll());
        return "pacientes/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Paciente paciente) {
        pacienteRepository.save(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<Paciente> p = pacienteRepository.findById(id);
        if (p.isPresent()) {
            model.addAttribute("paciente", p.get());
            return "pacientes/form";
        }
        return "redirect:/pacientes";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        pacienteRepository.deleteById(id);
        return "redirect:/pacientes";
    }
}
