package petShop;

import java.util.ArrayList;

public class AdvancedPetShop extends PetShop {
    private ArrayList<Pet> pets;

    public AdvancedPetShop() {
        if (petShopCount >= 1) {
            throw new IllegalStateException("Only one pet shop is allowed.");
        }
        pets = new ArrayList<Pet>(MAX_PETS);
        petShopCount++;
    }

    public void addPet(Pet pet) {
        if (petCount < MAX_PETS) {
            pet.isAdopted = true;
            pet.index = petCount;
            pets.add(pet);
            petCount++;
        } else {
            throw new ArrayIndexOutOfBoundsException("Pet shop is full!");
        }
    }

    public void displayByType(String type) {
        for (int i = 0; i < petCount; i++) {
            Pet pet = pets.get(i);
            if (pet.getClass().getSimpleName().equalsIgnoreCase(type)) {
                System.out.println(pet.toString());
            }
        }
    }

    public void displayById(int id) {
        Pet pet = pets.get(id);
        if (pet != null) {
            System.out.println(pet.toString());
        } else {
            throw new ArrayIndexOutOfBoundsException("Invalid ID: " + id);
        }
    }

    public void displayAll() {
        for (int i = 0; i < petCount; i++) {
            Pet pet = pets.get(i);
            System.out.println(pet.toString());
        }
    }

    public void feed(int id) {
        Pet pet = pets.get(id);
        if (pet != null) {
            pet.feed();
        } else {
            throw new ArrayIndexOutOfBoundsException("Invalid ID: " + id);
        }
    }

    public void addPet(Pet pet, int position) {
        if (petCount < MAX_PETS) {
            if (position < 0 || position > petCount) {
                throw new ArrayIndexOutOfBoundsException("Invalid position: " + position);
            }
            pet.isAdopted = true;
            pet.index = position;
            pets.add(position, pet);
            petCount++;
        } else {
            throw new ArrayIndexOutOfBoundsException("Pet shop is full!");
        }
    }

    public void getPetInfo(int position) {
        if (position < 0 || position >= petCount) {
            throw new ArrayIndexOutOfBoundsException("Invalid position: " + position);
        }
        Pet pet = pets.get(position);
        System.out.println(pet.toString());
    }

    public void removePetById(int index) {
        if (index < 0 || index >= petCount) {
            throw new ArrayIndexOutOfBoundsException("Invalid ID: " + index);
        }
        Pet p = pets.remove(index);
        p.isAdopted = false;
        petCount--;
    }

    public void removeAllPets() {
        pets.clear();
        petCount = 0;
    }

    public void removePetByType(String type) {
        for (int i = 0; i < petCount; i++) {
            Pet pet = pets.get(i);
            if (pet.getClass().getSimpleName().equalsIgnoreCase(type)) {
                pets.remove(i);
                petCount--;
                i--; // Adjust index after removal
            
            }
        }
    }
}
