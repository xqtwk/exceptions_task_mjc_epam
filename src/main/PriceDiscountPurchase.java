package main;

import main.exceptions.OutOfBoundArgumentException;
import main.exceptions.ZeroArgumentException;

public class PriceDiscountPurchase extends Purchase{
    private final Euro discount;
    public PriceDiscountPurchase(Product product, int purchasedUnits, Euro discount) {
        super(product,purchasedUnits);
        if(product.getPrice().compareTo(discount) <= 0)
            throw new OutOfBoundArgumentException("Discount can't be bigger than price");
        if (discount.equals(new Euro(0)))
            throw new ZeroArgumentException("Discount can't be zero");
        this.discount = discount;
    }

    public PriceDiscountPurchase(String[] fields) {
        this((PriceDiscountPurchase) getValidPurchase(fields));
    }

    public PriceDiscountPurchase(PriceDiscountPurchase priceDiscountPurchase){
        this(priceDiscountPurchase.getProduct(), priceDiscountPurchase.getPurchasedUnits(), priceDiscountPurchase.discount);
    }

    private static Purchase getValidPurchase(String[] fields) {
        if(fields.length != PurchasesFactory.getDiscountPurchaseFieldsNumber()) {
            throw new OutOfBoundArgumentException("wrong args number");
        }
        return new PriceDiscountPurchase(new Product(fields[0], new Euro(fields[1])), Integer.parseInt(fields[2]), new Euro(fields[3]));
    }

    @Override
    protected Euro getFinalCost(Euro cost) {
        return super.getFinalCost(cost).sub(discount.mul(purchasedUnits));
    }

    @Override
    public String toString() {
        return fieldsToString() + ";" + discount + ";" + getCost();
    }
}
