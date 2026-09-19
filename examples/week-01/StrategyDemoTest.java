public class StrategyDemoTest {
    private static void expectEqual(int expected, int actual, String scenario) {
        if (expected != actual) {
            throw new AssertionError(scenario + ": expected " + expected + ", got " + actual);
        }
    }

    public static void main(String[] args) {
        Checkout regular = new Checkout(new RegularPricing());
        Checkout student = new Checkout(new StudentPricing());

        expectEqual(10_000, regular.totalInCents(10_000), "Regular price");
        expectEqual(8_000, student.totalInCents(10_000), "Student discount");
        expectEqual(0, regular.totalInCents(0), "Empty regular checkout");
        expectEqual(0, student.totalInCents(0), "Empty student checkout");
        expectEqual(800, student.totalInCents(999), "Whole-cent rounding");
        expectEqual(4, student.totalInCents(4), "Discount below one cent");
        expectEqual(1_717_986_918, student.totalInCents(Integer.MAX_VALUE), "Large subtotal");

        try {
            student.totalInCents(-1);
            throw new AssertionError("Negative subtotal should be rejected");
        } catch (IllegalArgumentException expected) {
            // Expected: Checkout protects the input contract.
        }

        try {
            new Checkout(null);
            throw new AssertionError("Missing strategy should be rejected");
        } catch (NullPointerException expected) {
            // Expected: every Checkout must have a strategy.
        }

        // A third implementation works without modifying Checkout.
        PricingStrategy halfPrice = new PricingStrategy() {
            @Override
            public int priceInCents(int subtotalCents) {
                return subtotalCents / 2;
            }
        };
        expectEqual(5_000, new Checkout(halfPrice).totalInCents(10_000), "Injected policy");

        System.out.println("All 10 checks passed.");
    }
}
