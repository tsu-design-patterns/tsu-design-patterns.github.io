import java.util.Objects;

interface PricingStrategy {
    // Contract: subtotalCents >= 0; result is between 0 and subtotalCents.
    int priceInCents(int subtotalCents);
}

final class RegularPricing implements PricingStrategy {
    @Override
    public int priceInCents(int subtotalCents) {
        return subtotalCents;
    }
}

final class StudentPricing implements PricingStrategy {
    @Override
    public int priceInCents(int subtotalCents) {
        // 20% discount; round the discount down to a whole cent.
        return subtotalCents - subtotalCents / 5;
    }
}

final class Checkout {
    private final PricingStrategy pricing;

    Checkout(PricingStrategy pricing) {
        this.pricing = Objects.requireNonNull(pricing, "pricing");
    }

    int totalInCents(int subtotalCents) {
        if (subtotalCents < 0) {
            throw new IllegalArgumentException("Subtotal must be non-negative");
        }
        return pricing.priceInCents(subtotalCents);
    }
}

public class StrategyDemo {
    public static void main(String[] args) {
        Checkout regular = new Checkout(new RegularPricing());
        Checkout student = new Checkout(new StudentPricing());

        System.out.println("Regular: " + regular.totalInCents(10_000) + " cents");
        System.out.println("Student: " + student.totalInCents(10_000) + " cents");
    }
}
