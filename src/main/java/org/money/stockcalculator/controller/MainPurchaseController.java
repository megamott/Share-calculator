package org.money.stockcalculator.controller;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.dao.SharePurchaseRepo;
import org.money.stockcalculator.service.utilities.DollarCurrencyComparison;
import org.money.stockcalculator.service.utilities.UsSharesComparison;
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
    private final UsSharesComparison usSharesComparison;
    private final DollarCurrencyComparison dollarCurrencyComparison;

    public MainPurchaseController(DollarPurchaseRepo dollarPurchaseRepo, SharePurchaseRepo sharePurchaseRepo, UsSharesComparison usSharesComparison, DollarCurrencyComparison dollarCurrencyComparison) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
        this.sharePurchaseRepo = sharePurchaseRepo;
        this.usSharesComparison = usSharesComparison;
        this.dollarCurrencyComparison = dollarCurrencyComparison;
    }

    @GetMapping("/purchases")
    public String getAllPurchases(Model model) {

        model.addAttribute("dollarPurchases", dollarPurchaseRepo.findAll());
        model.addAttribute("sharePurchases", sharePurchaseRepo.findAll());
        model.addAttribute("shareDifferences", usSharesComparison.getComparisonsList());
        model.addAttribute("currencyDifferences", dollarCurrencyComparison.getComparisonsList());

        return "purchases";
    }

}
