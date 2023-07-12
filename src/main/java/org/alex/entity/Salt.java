package org.alex.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class Salt {
    private int id;
    private String email;
    private String passSalt;
    }

