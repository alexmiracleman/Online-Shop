package org.alex.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ItemDeptType {
    GRO("Groceries", "Groceries"),
    ELE("Electronics", "Electronics"),
    CAKE("Pets Food", "Pets Food"),
    POTATO("Gardening", "Gardening"),
    JUICE("Clothing", "Clothing"),
    CANDY("Cosmetics", "Cosmetics"),
    FURN("Furniture", "Furniture");


    private final String id;
    private final String name;

    public static ItemDeptType getById(String id) {
        for (ItemDeptType itemDeptType : values()) {
            if (itemDeptType.id.equalsIgnoreCase(id)) {
                return itemDeptType;
            }
        }
        throw new IllegalArgumentException("No itemDeptType was found for id: " + id);
    }

}
