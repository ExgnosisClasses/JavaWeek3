package accounting;

public class AccountingDepartment implements AccountingService{

    private AccountsPayable payables = null;
    private AccountsReceivable receivables = null;

    AccountingDepartment(AccountsPayable p, AccountsReceivable r) {
        if (p == null) {
            this.payables = new AccountsPayable();
        } else this.payables = p;

        if (r == null ) {
            this.receivables = new AccountsReceivable();
        } else {
            this.receivables = r;
        }
        System.out.println("Accounting Department open for business");
    }

    @Override
    public Report process(Transaction t) {
        if (t instanceof Invoice) {
            return payables.process(t);
        } else if (t instanceof Payment) {
            return receivables.process(t);
        }
        return new ErrorReport();
    }
}


