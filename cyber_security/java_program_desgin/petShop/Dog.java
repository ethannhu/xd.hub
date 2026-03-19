package petShop;

public class Dog extends Pet{
    public void feed() {
        System.out.println("Wer!Dog is fed.");
    }

    @Override
    public String toString() {
        return "Dog: " + super.toString();
    }
}
