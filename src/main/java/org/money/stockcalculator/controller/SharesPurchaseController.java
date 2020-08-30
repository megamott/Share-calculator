package org.money.stockcalculator.controller;

import org.money.stockcalculator.dao.SharePurchaseRepo;
import org.money.stockcalculator.model.SharePurchase;
import org.money.stockcalculator.service.utilities.Commission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO Добавить замену запятая на точку
 *
 * @author Matvey Konoplyov
 */
@Controller
public class SharesPurchaseController {

    private final SharePurchaseRepo sharePurchaseRepo;
    private final Commission commission;

    public SharesPurchaseController(SharePurchaseRepo sharePurchaseRepo, Commission commission) {
        this.sharePurchaseRepo = sharePurchaseRepo;
        this.commission = commission;
    }

    @PostMapping("/add-share-purchase")
    public String addSharePurchase(@RequestParam(name = "ticker") String ticker,
                                   @RequestParam(name = "quantity") int quantity,
                                   @RequestParam(name = "price") double price) {

        SharePurchase sharePurchase = new SharePurchase(ticker,
                                                        quantity,
                                                        price,
                                                        commission.getMyCommission(quantity, price));
        sharePurchaseRepo.save(sharePurchase);

        return "redirect:/purchases";
    }

}
