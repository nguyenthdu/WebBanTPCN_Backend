package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manufacturer")
public class Manufacturer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name_manufacturer")
	private String nameManufacturer;
	private String address;
	@Column(name = "phone_number")
	private String phoneNumber;
	private String email;
	private String description;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	@JsonIgnore
	private Set<FoodFunction> foodFunctions;
}
