package com.cts.orderservice.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@Embeddable
public class AddressDTO {

	private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
