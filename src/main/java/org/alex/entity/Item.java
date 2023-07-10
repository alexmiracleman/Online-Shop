package org.alex.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class Item {
    private int id;
    private String name;
    private int price;
    private ItemDepartmentType itemDepartmentType;
    private LocalDateTime creationDate;
}

