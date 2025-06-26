public class Runner {
    public static void main(String[] args) {
        Visitor zookeeper = new Visitor();
        Cat c = new Cat(zookeeper);
        c.printType();
        Dog d = new Dog(zookeeper);
        d.printType();
        Programmer p = new Programmer(zookeeper);
        p.printType();
    }

}

class Visitor {
    public String whatAmI(Object thing) {
        if (thing instanceof Dog) {
            return "I am a dog";
        } else if (thing instanceof Cat) {
            return "I am a cat";
        } else {
            return "I don't know";
        }
    }

}

class Cat {
    private Visitor info;

    Cat(Visitor info) {
        this.info = info;
    }

    void printType() {
        System.out.println(this.info.whatAmI(this));
    }

}

class Dog {
    private Visitor info;

    Dog(Visitor info) {
        this.info = info;
    }

    void printType() {
        System.out.println(this.info.whatAmI(this));
    }
}

class Programmer {
    private Visitor info;

    Programmer(Visitor info) {
        this.info = info;
    }

    void printType() {
        System.out.println(this.info.whatAmI(this));
    }
}
