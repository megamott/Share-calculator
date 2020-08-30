package org.money.stockcalculator.controller;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Matvey Konoplyov
 */
@Controller
public class MainPurchaseController {

    private final DollarPurchaseRepo dollarPurchaseRepo;

    public MainPurchaseController(DollarPurchaseRepo dollarPurchaseRepo) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
    }

    @GetMapping("/purchases")
    public String getAllPurchases(Model model){

        model.addAttribute("dollarPurchases", dollarPurchaseRepo.findAll());

        return "purchases";
    }

}
