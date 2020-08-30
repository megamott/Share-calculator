package org.money.stockcalculator.controller;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.dao.SharePurchaseRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для операций с акциями
 *
 * @author Matvey Konoplyov
 */
@Controller
public class MainPurchaseController {

    private final DollarPurchaseRepo dollarPurchaseRepo;
    private final SharePurchaseRepo sharePurchaseRepo;

    public MainPurchaseController(DollarPurchaseRepo dollarPurchaseRepo, SharePurchaseRepo sharePurchaseRepo) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
        this.sharePurchaseRepo = sharePurchaseRepo;
    }

    @GetMapping("/purchases")
    public String getAllPurchases(Model model){

        model.addAttribute("dollarPurchases", dollarPurchaseRepo.findAll());
        model.addAttribute("sharePurchases", sharePurchaseRepo.findAll());

        return "purchases";
    }

}
