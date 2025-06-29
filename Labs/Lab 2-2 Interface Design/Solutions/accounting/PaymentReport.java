package accounting;

public class PaymentReport implements Report {
    private String id;
    PaymentReport(String id) {
        this.id = id;
    }
    public String toString() {
        return "Processed Payment #" + this.id;
    }
}

