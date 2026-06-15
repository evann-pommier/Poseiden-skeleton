package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class CurveController {
    private final CurveService service;
    @Autowired
    public CurveController(CurveService service ) {
        this.service = service;
    }

    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", service.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        service.save(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("curvePoint", service.findById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable Integer id, @Valid CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }
        service.update(id,curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
