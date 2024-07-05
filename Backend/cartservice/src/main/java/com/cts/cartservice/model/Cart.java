package com.cts.cartservice.model;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@Component
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long cartId;
	
	private Long userId;
	
	@ElementCollection
	private List<Long> cartItemId;
	private Double totalAmount;
	private Date dateCreated;

}
