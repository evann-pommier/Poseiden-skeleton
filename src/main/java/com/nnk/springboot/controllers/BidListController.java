package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class BidListController {
    private final BidListService  service;

    public BidListController(BidListService service) {
        this.service = service;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidList", service.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm() {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result) {
        if (!result.hasErrors()) {
            service.save(bid);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("bidList", service.findById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable Integer id, @Valid BidList bidList, BindingResult result) {
        if(result.hasErrors()){
            bidList.setBidListId(id);
            return "bidList/update";
        }
        service.update(id, bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/bidList/list";
    }
}
