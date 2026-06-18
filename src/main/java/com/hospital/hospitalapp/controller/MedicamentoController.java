package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Medicamento;
import com.hospital.hospitalapp.service.MedicamentoService;
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
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medicamentos", medicamentoService.obtenerTodos());
        return "medicamentos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("medicamento", new Medicamento());
        return "medicamentos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("medicamento") Medicamento medicamento,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "medicamentos/form";
        }
        medicamentoService.guardar(medicamento);
        return "redirect:/medicamentos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Medicamento medicamento = medicamentoService.obtenerPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));
        model.addAttribute("medicamento", medicamento);
        return "medicamentos/form";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return "redirect:/medicamentos";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Medicamento medicamento = medicamentoService.obtenerPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));
        model.addAttribute("medicamento", medicamento);
        return "medicamentos/view";
    }
}
