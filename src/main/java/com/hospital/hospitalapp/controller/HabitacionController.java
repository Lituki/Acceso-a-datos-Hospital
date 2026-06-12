package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.repository.HabitacionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionRepository habitacionRepository;

    public HabitacionController(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("habitaciones", habitacionRepository.findAll());
        model.addAttribute("disponibles", habitacionRepository.findDisponibles());
        return "habitaciones/list";
    }
}
