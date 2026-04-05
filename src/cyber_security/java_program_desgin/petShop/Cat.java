package petShop;

public class Cat extends Pet{
    public void feed() {
        System.out.println("Meow! Cat is fed.");
    }

    @Override
    public String toString() {
        return "Cat: " + super.toString();
    }
}
