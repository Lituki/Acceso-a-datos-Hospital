package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Habitacion;
import com.hospital.hospitalapp.service.HabitacionService;
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
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("habitaciones", habitacionService.obtenerTodas());
        model.addAttribute("disponibles", habitacionService.obtenerDisponibles());
        return "habitaciones/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("habitacion", new Habitacion());
        return "habitaciones/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("habitacion") Habitacion habitacion,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "habitaciones/form";
        }
        if (habitacion.getDisponible() == null) {
            habitacion.setDisponible(true);
        }
        habitacionService.guardar(habitacion);
        return "redirect:/habitaciones";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Habitacion habitacion = habitacionService.obtenerPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));
        model.addAttribute("habitacion", habitacion);
        return "habitaciones/form";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        habitacionService.eliminar(id);
        return "redirect:/habitaciones";
    }
}
