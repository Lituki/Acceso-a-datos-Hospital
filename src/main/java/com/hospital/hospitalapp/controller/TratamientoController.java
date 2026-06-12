package com.hospital.hospitalapp.controller;

import com.hospital.hospitalapp.model.Tratamiento;
import com.hospital.hospitalapp.repository.CitaRepository;
import com.hospital.hospitalapp.repository.MedicamentoRepository;
import com.hospital.hospitalapp.repository.TratamientoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tratamientos")
public class TratamientoController {

    private final TratamientoRepository tratamientoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final CitaRepository citaRepository;

    public TratamientoController(TratamientoRepository tratamientoRepository,
                                 MedicamentoRepository medicamentoRepository,
                                 CitaRepository citaRepository) {
        this.tratamientoRepository = tratamientoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.citaRepository = citaRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tratamientos", tratamientoRepository.findAll());
        return "tratamientos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("tratamiento", new Tratamiento());
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        model.addAttribute("citas", citaRepository.findAll());
        model.addAttribute("selectedMedicamentos", new ArrayList<Long>());
        return "tratamientos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Tratamiento tratamiento,
                          @RequestParam(name = "medicamentoIds", required = false) List<Long> medicamentoIds) {
        if (medicamentoIds != null && !medicamentoIds.isEmpty()) {
            tratamiento.setMedicamentos(medicamentoRepository.findAllById(medicamentoIds));
        } else {
            tratamiento.setMedicamentos(new ArrayList<>());
        }
        tratamientoRepository.save(tratamiento);
        return "redirect:/tratamientos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<Tratamiento> t = tratamientoRepository.findById(id);
        if (t.isPresent()) {
            Tratamiento tr = t.get();
            model.addAttribute("tratamiento", tr);
            model.addAttribute("medicamentos", medicamentoRepository.findAll());
            model.addAttribute("citas", citaRepository.findAll());
            List<Long> selected = new ArrayList<>();
            if (tr.getMedicamentos() != null) {
                for (var m : tr.getMedicamentos()) selected.add(m.getId());
            }
            model.addAttribute("selectedMedicamentos", selected);
            return "tratamientos/form";
        }
        return "redirect:/tratamientos";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        tratamientoRepository.deleteById(id);
        return "redirect:/tratamientos";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Optional<Tratamiento> t = tratamientoRepository.findById(id);
        if (t.isPresent()) {
            model.addAttribute("tratamiento", t.get());
            return "tratamientos/view";
        }
        return "redirect:/tratamientos";
    }
}
