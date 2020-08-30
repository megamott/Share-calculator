package org.money.stockcalculator.dao;

import org.money.stockcalculator.model.SharePurchase;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Matvey Konoplyov
 */
public interface SharePurchaseRepo extends CrudRepository<SharePurchase, Long> {
}
