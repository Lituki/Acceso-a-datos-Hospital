package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Medico;
import com.hospital.hospitalapp.repository.MedicoRepository;
import com.hospital.hospitalapp.repository.EspecialidadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    public MedicoController(MedicoRepository medicoRepository, EspecialidadRepository especialidadRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medicos", medicoRepository.findAll());
        return "medicos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("medico", new Medico());
        model.addAttribute("especialidades", especialidadRepository.findAll());
        return "medicos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Medico medico) {
        medicoRepository.save(medico);
        return "redirect:/medicos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<Medico> m = medicoRepository.findById(id);
        if (m.isPresent()) {
            model.addAttribute("medico", m.get());
            model.addAttribute("especialidades", especialidadRepository.findAll());
            return "medicos/form";
        }
        return "redirect:/medicos";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        medicoRepository.deleteById(id);
        return "redirect:/medicos";
    }
}
