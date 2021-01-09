package org.money.stockcalculator.service.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueRounderTest {

    @Test
    void roundValue_Should_Return_True() {
        ValueRounder valueRounder = new ValueRounder();
        double value = 4.4444;
        assertEquals(value, valueRounder.roundValue(4.4443333));
    }
}