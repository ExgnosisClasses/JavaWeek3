package accounting;

public class InvoiceReport implements Report {
    private String invNumber = null;

    InvoiceReport(String id) {
        this.invNumber = id;
    }

    public String toString() {
        return "Processed Invoice #" + this.invNumber;
    }

    public String getInvNumber() {
        return this.invNumber;
    }

}
