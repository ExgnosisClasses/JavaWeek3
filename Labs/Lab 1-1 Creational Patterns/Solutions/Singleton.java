public class Singleton {

    private static Singleton theOne = null;

    public static Singleton getSingleton() {
        if (Singleton.theOne == null) {
            Singleton.theOne = new Singleton();
        }
        return Singleton.theOne;
    }

    private Singleton(){};

    public static void main(String[] args) {
        Singleton a = Singleton.getSingleton();
        Singleton b = Singleton.getSingleton();

        System.out.println("Address of a " + a);
        System.out.println("Address of b " + b);


    }

}
