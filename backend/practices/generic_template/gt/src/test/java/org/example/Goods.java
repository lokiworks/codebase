package org.example;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Goods {
    private int count;
    private String name;
    private String desc;
    private int discount;
    private int tax;
    private int price;
    private int totalPrice;
}
