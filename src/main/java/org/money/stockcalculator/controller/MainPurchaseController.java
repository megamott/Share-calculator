package org.money.stockcalculator.controller;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.dao.SharePurchaseRepo;
import org.money.stockcalculator.service.Impl.CurrencyAdder;
import org.money.stockcalculator.service.Impl.CurrencyCommissionAdder;
import org.money.stockcalculator.service.Impl.ShareAdder;
import org.money.stockcalculator.service.Impl.ShareCommissionAdder;
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
    private final CurrencyAdder currencyAdder;
    private final CurrencyCommissionAdder currencyCommissionAdder;
    private final ShareAdder shareAdder;
    private final ShareCommissionAdder shareCommissionAdder;


    public MainPurchaseController(DollarPurchaseRepo dollarPurchaseRepo,
                                  SharePurchaseRepo sharePurchaseRepo,
                                  UsSharesComparison usSharesComparison,
                                  DollarCurrencyComparison dollarCurrencyComparison, CurrencyAdder currencyAdder, CurrencyCommissionAdder currencyCommissionAdder, ShareAdder shareAdder, ShareCommissionAdder shareCommissionAdder) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
        this.sharePurchaseRepo = sharePurchaseRepo;
        this.usSharesComparison = usSharesComparison;
        this.dollarCurrencyComparison = dollarCurrencyComparison;
        this.currencyAdder = currencyAdder;
        this.currencyCommissionAdder = currencyCommissionAdder;
        this.shareAdder = shareAdder;
        this.shareCommissionAdder = shareCommissionAdder;
    }

    @GetMapping("/purchases")
    public String getAllPurchases(Model model) {

        model.addAttribute("dollarPurchases", dollarPurchaseRepo.findAll());
        model.addAttribute("sharePurchases", sharePurchaseRepo.findAll());
        model.addAttribute("shareDifferences", usSharesComparison.getComparisonsList());
        model.addAttribute("currencyDifferences", dollarCurrencyComparison.getComparisonsList());
        model.addAttribute("currencyAdder", currencyAdder.getSum())
             .addAttribute("currencyCommissionAdder", currencyCommissionAdder.getSum())
             .addAttribute("shareAdder", shareAdder.getSum())
             .addAttribute("shareCommissionAdder", shareCommissionAdder.getSum());

        return "purchases";
    }

}
