package lsp;

public class Runner1 {

    public static void main(String[] args) {
        Bird owl = new Owl();
        Bird ostrich = new Ostrich();
        if (owl instanceof Flyer) {
            ((Flyer) owl).fly();
        }
        if (owl instanceof Runner) {
            ((Runner) owl).run();
        }
        if (ostrich instanceof Runner) {
            ((Runner) ostrich).run();
        }

    }
}

class Bird {
    public void eat() {
        System.out.println("Peck peck peck...");
    }
}

interface Flyer {
    void fly();
}

interface Runner {
    void run();
}


class Ostrich extends Bird implements Runner {
    @Override
    public void run() {
        System.out.println("Running fast on the ground.");
    }
}

class Owl extends Bird implements Flyer, Runner {
    @Override
    public void fly() {
        System.out.println("Flying high in the sky");
    }
    @Override
    public void run() {
        System.out.println("Running with a sort of a waddle on the ground.");
    }
}