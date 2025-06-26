public class Runner {
    public static void main(String[] args) {
        Original orig = new OriginalImp();
        System.out.println(orig.meth1("Original"));
        System.out.println(orig.meth2("Original"));

        Decorator decorated = new DecImp(orig);
        System.out.println(decorated.meth1("Decorated"));
        System.out.println(decorated.meth2("Decorated"));
        System.out.println(decorated.newMeth("Decorated"));
    }

}

interface Original {
    String meth1(String s);
    String meth2(String s);
}

class OriginalImp implements Original {

    @Override
    public String meth1(String s) {
        return "Original Method 1 " + s;
    }

    @Override
    public String meth2(String s) {
        return "Original Method 2 " + s;
    }

}

interface Decorator  extends Original {
    String newMeth(String s);
}

class DecImp implements Decorator {
    private Original myOrig;

    DecImp(Original o) {
        this.myOrig = o;
    }

    @Override
    public String meth1(String s) {
        return this.myOrig.meth1(s);
    }

    @Override
    public String meth2(String s) {
        return this.myOrig.meth2(s);
    }

    @Override
    public String newMeth(String s) {
        return "New Method " + s ;
    }

}