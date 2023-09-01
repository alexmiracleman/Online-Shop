package org.alex.web.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.alex.entity.Item;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Setter
@Getter
public class Session {
    private String token;
    private LocalDateTime expireDate;
    private List<Item> cart;
    private String userType;
}
