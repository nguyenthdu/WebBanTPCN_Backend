package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
public class User_ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;
    private String phone;
    private String email;

    private String username;

    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<ShippingAddress> shippingAddresses;
    @OneToOne(mappedBy = "user")
    private Cart shoppingCarts;
    @OneToMany(mappedBy = "user")
    private Set<Order_> orders;
//    @JsonIgnore
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Rating> ratings = new ArrayList<>();
//    @JsonIgnore
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();
}
