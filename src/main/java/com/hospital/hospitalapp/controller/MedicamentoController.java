package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.repository.MedicamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoController(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        return "medicamentos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("medicamento", new com.hospital.hospitalapp.model.Medicamento());
        return "medicamentos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute com.hospital.hospitalapp.model.Medicamento medicamento) {
        medicamentoRepository.save(medicamento);
        return "redirect:/medicamentos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<com.hospital.hospitalapp.model.Medicamento> m = medicamentoRepository.findById(id);
        if (m.isPresent()) {
            model.addAttribute("medicamento", m.get());
            return "medicamentos/form";
        }
        return "redirect:/medicamentos";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        medicamentoRepository.deleteById(id);
        return "redirect:/medicamentos";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Optional<com.hospital.hospitalapp.model.Medicamento> m = medicamentoRepository.findById(id);
        if (m.isPresent()) {
            model.addAttribute("medicamento", m.get());
            return "medicamentos/view";
        }
        return "redirect:/medicamentos";
    }
}
