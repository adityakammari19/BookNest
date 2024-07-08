package com.cts.orderservice.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@Component
public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    private double totalAmount;
    
    public Order(Long userId, LocalDate orderDate, List<OrderItem> orderItems, double totalAmount) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
    }

}
