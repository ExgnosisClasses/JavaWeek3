package lsp;

public class Runner {

    public static void main(String[] args) {
        Bird owl = new Owl();
        owl.eat();
        if (owl instanceof FlyingBird) {
            ((FlyingBird) owl).fly();
        }

    }
}

class Bird {
    public void eat() {
        System.out.println("Peck peck peck...");
    }
}

class FlyingBird extends Bird {
    public void fly() {
        System.out.println("Flying high in the sky.");
    }
}

class Ostrich extends Bird {
    public void run() {
        System.out.println("Running fast on the ground.");
    }
}

class Owl extends FlyingBird {
    public void run() {
        System.out.println("Running fast on the ground.");
    }
}