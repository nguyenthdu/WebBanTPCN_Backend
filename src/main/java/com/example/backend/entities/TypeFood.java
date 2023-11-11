package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "type_food")
public class TypeFood {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "typeFood", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Category> categories;
}
