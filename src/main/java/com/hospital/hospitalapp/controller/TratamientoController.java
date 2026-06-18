package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Tratamiento;
import com.hospital.hospitalapp.model.Cita;
import com.hospital.hospitalapp.service.CitaService;
import com.hospital.hospitalapp.service.MedicamentoService;
import com.hospital.hospitalapp.service.TratamientoService;
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
@RequestMapping("/tratamientos")
public class TratamientoController {

    private final TratamientoService tratamientoService;
    private final MedicamentoService medicamentoService;
    private final CitaService citaService;

    public TratamientoController(TratamientoService tratamientoService,
                                 MedicamentoService medicamentoService,
                                 CitaService citaService) {
        this.tratamientoService = tratamientoService;
        this.medicamentoService = medicamentoService;
        this.citaService = citaService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tratamientos", tratamientoService.obtenerTodos());
        return "tratamientos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setCita(new Cita());
        model.addAttribute("tratamiento", tratamiento);
        populateFormModel(model, new ArrayList<>());
        return "tratamientos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("tratamiento") Tratamiento tratamiento,
                          BindingResult bindingResult,
                          @RequestParam(name = "medicamentoIds", required = false) List<Long> medicamentoIds,
                          Model model) {
        if (bindingResult.hasErrors()) {
            populateFormModel(model, medicamentoIds);
            return "tratamientos/form";
        }
        if (tratamiento.getCita() != null && tratamiento.getCita().getId() == null) {
            tratamiento.setCita(null);
        }
        tratamientoService.guardar(tratamiento, medicamentoIds);
        return "redirect:/tratamientos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Tratamiento tratamiento = tratamientoService.obtenerParaFormulario(id)
            .orElseThrow(() -> new EntityNotFoundException("Tratamiento no encontrado"));
        if (tratamiento.getCita() == null) {
            tratamiento.setCita(new Cita());
        }
        model.addAttribute("tratamiento", tratamiento);
        populateFormModel(model, obtenerIdsMedicamentos(tratamiento));
        return "tratamientos/form";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        tratamientoService.eliminar(id);
        return "redirect:/tratamientos";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Tratamiento tratamiento = tratamientoService.obtenerPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Tratamiento no encontrado"));
        model.addAttribute("tratamiento", tratamiento);
        return "tratamientos/view";
    }

    private void populateFormModel(Model model, List<Long> selectedMedicamentoIds) {
        model.addAttribute("medicamentos", medicamentoService.obtenerTodos());
        model.addAttribute("citas", citaService.obtenerTodas());
        model.addAttribute("selectedMedicamentos", selectedMedicamentoIds == null ? new ArrayList<>() : selectedMedicamentoIds);
    }

    private List<Long> obtenerIdsMedicamentos(Tratamiento tratamiento) {
        if (tratamiento.getMedicamentos() == null) {
            return new ArrayList<>();
        }
        return tratamiento.getMedicamentos().stream()
            .map(medicamento -> medicamento.getId())
            .toList();
    }
}
