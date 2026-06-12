package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Cita;
import com.hospital.hospitalapp.model.Medico;
import com.hospital.hospitalapp.model.Paciente;
import com.hospital.hospitalapp.repository.CitaRepository;
import com.hospital.hospitalapp.repository.MedicoRepository;
import com.hospital.hospitalapp.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaController(CitaRepository citaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("citas", citaRepository.findAll());
        return "citas/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("pacientes", pacienteRepository.findAll());
        model.addAttribute("medicos", medicoRepository.findAll());
        return "citas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cita cita, @RequestParam(required = false) List<Long> medicoIds) {
        if (medicoIds != null) {
            List<Medico> medicos = medicoIds.stream().map(medicoRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
            cita.setMedicos(medicos);
        }
        citaRepository.save(cita);
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<Cita> c = citaRepository.findById(id);
        if (c.isPresent()) {
            model.addAttribute("cita", c.get());
            model.addAttribute("pacientes", pacienteRepository.findAll());
            model.addAttribute("medicos", medicoRepository.findAll());
            return "citas/form";
        }
        return "redirect:/citas";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        citaRepository.deleteById(id);
        return "redirect:/citas";
    }
}
