package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class TradeController {
    private final TradeService service;

    public TradeController(TradeService tradeService) {
        this.service = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", this.service.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        service.save(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("trade", this.service.findById(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable Integer id, @Valid Trade trade, BindingResult result,  Model model) {
        if(result.hasErrors()) {
            trade.setTradeId(id);
            return "trade/update";
        }
        service.update(id, trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/trade/list";
    }
}
