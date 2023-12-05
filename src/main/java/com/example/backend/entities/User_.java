package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	@Enumerated(EnumType.STRING)
	private Roles role = Roles.USER;
	//cấu hình enabled mặt định là true
	@Column(columnDefinition = "boolean default true")
	private boolean enabled = true;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<ShippingAddress> shippingAddresses;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private ShoppingCart shoppingCart;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Order_> orders;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//	private LocalDate yearOfBirth;
//    @JsonIgnore
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Rating> ratings = new ArrayList<>();
//    @JsonIgnore
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();
}
