public class FactoryMethod {

    public static Shape createShape(String type) {
        switch (type.toLowerCase()) {
            case "circle":
                return new Circle();

            case "square":
                return new Square();
            default:
                return null;
        }

    }

    public static void main(String[] args) {

        System.out.println("Circle " + FactoryMethod.createShape("circle"));
        System.out.println("Square " + FactoryMethod.createShape("Square"));
        System.out.println("Triangle " + FactoryMethod.createShape("Triangle"));

    }

}

interface Shape {}

class Circle implements Shape {}

class Square implements Shape {}