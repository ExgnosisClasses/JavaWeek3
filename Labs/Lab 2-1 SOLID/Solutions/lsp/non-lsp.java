package lsp;

public class Runner {

    public static void main(String[] args) {
        Bird myBird = new Ostrich();
        myBird.fly(); // Expecting it to fly, but throws exception
    }
}

class Bird {
    public void fly() {
        System.out.println("Flying high in the sky.");
    }
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        // Ostriches cannot fly
        throw new UnsupportedOperationException("Ostriches can't fly!");
    }
}