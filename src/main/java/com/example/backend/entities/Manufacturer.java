package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private String nameManufacturer;
	private String address;
	private String phoneNumber;
	private String email;
	private String description;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<FoodFunction> foodFunctions;
}
