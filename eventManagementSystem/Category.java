package eventmanagementsystem.dataClasses;

import eventmanagementsystem.services.CRUD;

import java.util.Collections;
import java.util.List;

public class Category implements CRUD {
    private String CategoryID ;
    private String name ;


    // Constructor
    public Category(String CategoryID, String name) {
        this.CategoryID = CategoryID;
        this.name = name;
    }

    public static String getCategory() {
        return null;
    }

    // Getters
    public String getCategoryID() {
        return CategoryID;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setCategoryID(String CategoryID) {
        this.CategoryID = CategoryID;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    @Override
    public void create(Object entity) {
        if (entity instanceof Category) {
            Database.categories.add((Category) entity);
        }
    }

    @Override
    public Object read(int index) {
        if (index >= 0 && index < Database.categories.size()) {
            return Database.categories.get(index);
        }
        return null;
    }

    @Override
    public void update(int index, Object updatedEntity) {
        if (updatedEntity instanceof Category && index >= 0 && index < Database.categories.size()) {
            Database.categories.set(index, (Category) updatedEntity);
        }
    }

    @Override
    public void delete(int index) {
        if (index >= 0 && index < Database.categories.size()) {
            Database.categories.remove(index);
        }
    }
}
