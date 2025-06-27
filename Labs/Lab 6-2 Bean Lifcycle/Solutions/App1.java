package lab6;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("lab6/config.xml");

        Consultant dilbert = context.getBean("ITGuru", Consultant.class);
        Consultant ratbert = context.getBean("ITGuru", Consultant.class);
        System.out.println("Address of Dilbert = " + dilbert);
        System.out.println("Address of Ratbert = " + ratbert);


        context.close();
    }
}