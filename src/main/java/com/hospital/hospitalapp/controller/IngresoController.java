package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.repository.IngresoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingresos")
public class IngresoController {

    private final IngresoRepository ingresoRepository;

    public IngresoController(IngresoRepository ingresoRepository) {
        this.ingresoRepository = ingresoRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ingresos", ingresoRepository.findAll());
        model.addAttribute("activos", ingresoRepository.findByFechaAltaIsNull());
        return "ingresos/list";
    }
}
