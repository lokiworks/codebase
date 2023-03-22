package org.example;

import lombok.Data;

import java.util.List;

@Data
public class OrderItem {
    List<Goods> goods;
    String id;
}
