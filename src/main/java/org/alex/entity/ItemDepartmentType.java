package org.alex.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ItemDepartmentType {
    GROCERIES("Groceries", "Groceries"),
    ELECTRONICS("Electronics", "Electronics"),
    PETS_FOOD("Pets Food", "Pets Food"),
    GARDENING("Gardening", "Gardening"),
    CLOTHING("Clothing", "Clothing"),
    COSMETICS("Cosmetics", "Cosmetics"),
    FURNITURE("Furniture", "Furniture");

    private final String id;
    private final String name;

    public static ItemDepartmentType getById(String id) {
        for (ItemDepartmentType itemDepartmentType : values()) {
            if (itemDepartmentType.id.equalsIgnoreCase(id)) {
                return itemDepartmentType;
            }
        }
        throw new IllegalArgumentException("No ItemDepartmentType was found for id: " + id);
    }

}
