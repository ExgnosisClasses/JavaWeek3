package accounting;

public class AccountsReceivable implements AccountingService {

    @Override
    public Report process(Transaction t) {
        // check to see if the Transaction one the AP module is responsible for
    
        if (t instanceof Payment) {
            String id  =  ((Payment)t).getPaymentID();
            // if there is a null ID, return an error
            // This simulates corrupt data
            if (null == id) {
                return new ErrorReport();
            }
            return new PaymentReport(id);
        } else {
            return new ErrorReport();
        }

       
    }

}
