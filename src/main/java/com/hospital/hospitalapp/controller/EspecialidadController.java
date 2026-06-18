package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.service.EspecialidadService;
import com.hospital.hospitalapp.service.MedicoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;
    private final MedicoService medicoService;

    public EspecialidadController(EspecialidadService especialidadService, MedicoService medicoService) {
        this.especialidadService = especialidadService;
        this.medicoService = medicoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("especialidades", especialidadService.obtenerTodas());
        return "especialidades/list";
    }

    @GetMapping("/{id}/medicos")
    public String medicosPorEspecialidad(@PathVariable Long id, Model model) {
        especialidadService.obtenerPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Especialidad no encontrada"));
        model.addAttribute("medicos", medicoService.obtenerPorEspecialidad(id));
        model.addAttribute("titulo", "Medicos - Especialidad");
        return "medicos/list";
    }
}
