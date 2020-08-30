package org.money.stockcalculator.dao;

import org.money.stockcalculator.model.DollarPurchase;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Matvey Konoplyov
 */
public interface DollarPurchaseRepo extends CrudRepository<DollarPurchase, Long> {
}
