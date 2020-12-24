package org.sid.billingservice.entities;



import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.sid.billingservice.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Bill {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; private Date billingDate;

	@OneToMany(mappedBy = "bill")
	private Collection<ProductItem> productItems;
	
	private Long customerID;

	@Transient private Customer customer;
}
