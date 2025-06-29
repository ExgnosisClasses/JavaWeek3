package accounting;

public class Invoice implements Transaction {
    private String invoiceNumber = null;

    Invoice(String number) {
        this.invoiceNumber = number;
    }
    
    public String toString() {
        return "Invoice #"+this.invoiceNumber;
    }

    public String getInvNumber() {
        return this.invoiceNumber;
    }

}
