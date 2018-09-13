package main.java.com.systop.base;

public class HelloWorld {
    private String name;

    public HelloWorld(String name) {
        this.name = name;
    }

    public HelloWorld() {
        System.out.println("This is HelloWorld constructor");
    }

    public void sayHello(){

        System.out.println("Hello " + name );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("This is HelloWorld setName().");
        this.name = name;
    }
}
