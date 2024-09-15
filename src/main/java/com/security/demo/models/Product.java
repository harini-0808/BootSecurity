package com.security.demo.models;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import javax.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Product")
public class Product {
	@Id
	//identity strategy is most suitable for mysql db and it differs for other dbs!
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productID;
	private String description;
	private double price;
	private String brand;
	private String category;
}
