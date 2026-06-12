package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.repository.EspecialidadRepository;
import com.hospital.hospitalapp.repository.MedicoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadController {

    private final EspecialidadRepository especialidadRepository;
    private final MedicoRepository medicoRepository;

    public EspecialidadController(EspecialidadRepository especialidadRepository, MedicoRepository medicoRepository) {
        this.especialidadRepository = especialidadRepository;
        this.medicoRepository = medicoRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("especialidades", especialidadRepository.findAll());
        return "especialidades/list";
    }

    @GetMapping("/{id}/medicos")
    public String medicosPorEspecialidad(@PathVariable Long id, Model model) {
        model.addAttribute("medicos", medicoRepository.findByEspecialidadId(id));
        model.addAttribute("titulo", "Médicos - Especialidad");
        return "medicos/list";
    }
}
