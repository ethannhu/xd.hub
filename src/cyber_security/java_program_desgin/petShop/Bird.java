package petShop;

public class Bird extends Pet{
    public void feed() {
        System.out.println("Chirp! Bird is fed.");
    }
    @Override
    public String toString() {
        return "Bird: " + super.toString();
    }
}
