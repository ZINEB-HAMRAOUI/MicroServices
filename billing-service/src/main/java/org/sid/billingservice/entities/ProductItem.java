package org.sid.billingservice.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;

import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class ProductItem {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double quantity;
	
	private double price;
	private long productID;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne 
	private Bill bill;
	@Transient
	private Product product;
	@Transient
	private String ProductName;

}
