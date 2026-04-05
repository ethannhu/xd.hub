/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package petShop;

/**
 *
 * @author 20692
 */
public class BasicPetShop extends PetShop {
    private Pet[] pets;


    public BasicPetShop() {
        if (petShopCount >= 1) {
            throw new IllegalStateException("Only one pet shop is allowed.");
        }
        pets = new Pet[MAX_PETS];
    }
    
    public void addPet(Pet pet) {
        if (petCount < MAX_PETS) {
            pet.isAdopted = true;
            pet.index = petCount;
            pets[petCount] = pet;
            petCount++;
        } else {
            throw new ArrayIndexOutOfBoundsException("Pet shop is full!");
        }
    }

    public void addPet(Pet pet, int position) {
        if (petCount < MAX_PETS) {
            if (position < 0 || position > petCount) {
                throw new ArrayIndexOutOfBoundsException("Invalid position: " + position);
            }
            pet.isAdopted = true;
            pet.index = position;
            for (int i = petCount; i > position; i--) {
                pets[i] = pets[i - 1];
            }
            pets[position] = pet;
            petCount++;
        } else {
            throw new ArrayIndexOutOfBoundsException("Pet shop is full!");
        }
    }

    public void removePetById(int index) {
        if (index < 0 || index >= petCount) {
            throw new ArrayIndexOutOfBoundsException("Invalid ID: " + index);
        }
        Pet p = pets[index];
        p.isAdopted = false;
        for (int i = index; i < petCount - 1; i++) {
            pets[i] = pets[i + 1];
        }
        pets[petCount - 1] = null;
        petCount--;
    }

    public void removeAllPets() {
        for (int i = 0; i < petCount; i++) {
            pets[i].isAdopted = false;
        }
        pets = new Pet[MAX_PETS];
        petCount = 0;
    }

    public void removePetByType(String type) {
        for (int i = 0; i < petCount; i++) {
            Pet pet = pets[i];
            if (pet.getClass().getSimpleName().equalsIgnoreCase(type)) {
                pet.isAdopted = false;
                for (int j = i; j < petCount - 1; j++) {
                    pets[j] = pets[j + 1];
                }
                pets[petCount - 1] = null;
                petCount--;
                i--; // Adjust index after removal
            }
        }
    }

    public void displayByType(String type) {
        for (int i = 0; i < petCount; i++) {
            if (pets[i].getClass().getSimpleName().equalsIgnoreCase(type)) {
                System.out.println(pets[i]);
            }
        }
    }

    public void displayById(int id) {
        if (id < 0 || id >= petCount) {
            throw new IllegalArgumentException("Invalid pet ID: " + id);
        }
        for (int i = id; i < petCount; i++) {
            if (pets[i].index == id) {
                System.out.println(pets[i]);
            }
        }
    }

    public void displayAll() {
        for (int i = 0; i < petCount; i++) {
            System.out.println(pets[i]);
        }
    }
    
    public void feed(int id) {
        if (id < 0 || id >= petCount) {
            throw new IllegalArgumentException("Invalid pet ID: " + id);
        }
        for (int i = id; i < petCount; i++) {
            if (pets[i].index == id) {
                pets[i].feed();
            }
        }
    }

    public static void main(String[] args) {
        BasicPetShop petShop = new BasicPetShop();
        
        Dog dog = new Dog();
        dog.name = "Buddy";
        petShop.addPet(dog);
        
        Cat cat = new Cat();
        cat.name = "Whiskers";
        petShop.addPet(cat);
        
        Bird bird = new Bird();
        bird.name = "Tweety";
        petShop.addPet(bird);
        
        System.out.println("All pets:");
        petShop.displayAll();
        
        System.out.println("\nDogs:");
        petShop.displayByType("Dog");
        
        System.out.println("\nCats:");
        petShop.displayByType("Cat");
        
        System.out.println("\nBirds:");
        petShop.displayByType("Bird");
        
        System.out.println("\nFeeding pet with ID 1:");
        petShop.feed(1);
    }

}
