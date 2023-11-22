package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "shipping_address")
public class ShippingAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name_of_recipient")
	private String nameOfRecipient;
	@Column(name = "phone_of_recipient")
	private String phoneOfRecipient;
	@Column(name = "street_house_number")
	private String streetHouseNumber;
	private String district;
	private String city;
	private String nation;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User_ user;
}
