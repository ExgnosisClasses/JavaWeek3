package accounting;

public class Payment  implements Transaction {
    private String paymentID = null;

    Payment(String number) {
        this.paymentID = number;
    }
    public String toString() {
        return "Payment #"+this.paymentID;
    }
    public String getPaymentID() {
        return this.paymentID;
    }

}
