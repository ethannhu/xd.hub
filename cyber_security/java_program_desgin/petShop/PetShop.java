package petShop;

public abstract class PetShop {
    protected static final int MAX_PETS = 100;
    protected int petCount = 0;
    protected static int petShopCount = 0;

    public abstract void addPet(Pet pet);
    // public abstract void addPet(Pet pet, int position);
    public abstract void displayByType(String type);
    public abstract void displayById(int id);
    public abstract void displayAll();
    public abstract void feed(int id);
    public abstract void removePetById(int index);
    // public abstract void removeAllPets();
    // public abstract void removePetByType(String type);
}
