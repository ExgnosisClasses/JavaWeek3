package accounting;

public class AccountsPayable implements AccountingService {

    public Report process(Transaction t) {
        // check to see if the Transaction one the AP module is responsible for
    
        if (t instanceof Invoice) {
            String id  =  ((Invoice)t).getInvNumber();
            // if there is a null ID, return an error
            // This simulates corrupt data
            if (null == id) {
                return new ErrorReport();
            }
            return new InvoiceReport(id);
        } else {
            return new ErrorReport();
        }

       
    }
}
