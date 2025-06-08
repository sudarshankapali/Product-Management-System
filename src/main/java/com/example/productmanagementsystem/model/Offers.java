package com.example.productmanagementsystem.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Offers {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    String title;
    String description;
    List<Product> products;
}
