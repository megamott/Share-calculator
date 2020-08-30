package org.money.stockcalculator.controller;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.model.DollarPurchase;
import org.money.stockcalculator.service.utilities.Commission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для операций с долларами
 *
 * @author Matvey Konoplyov
 */
@Controller
public class DollarPurchaseController {

    private final DollarPurchaseRepo dollarPurchaseRepo;
    private final Commission commission;

    public DollarPurchaseController(DollarPurchaseRepo dollarPurchaseRepo, Commission commission) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
        this.commission = commission;
    }

    @PostMapping("/add-dollar-purchase")
    public String addDollarPurchase(@RequestParam(name = "quantity") int quantity,
                                    @RequestParam(name = "price") double price) {

        DollarPurchase dollarPurchase = new DollarPurchase(quantity,
                                                           price,
                                                           commission.getMyCommission(quantity, price));
        dollarPurchaseRepo.save(dollarPurchase);

        return "redirect:/purchases";

    }


}
