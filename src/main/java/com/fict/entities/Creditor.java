package com.fict.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "creditor", uniqueConstraints = { @UniqueConstraint(columnNames = {"transaction_number"}) })
public class Creditor implements Serializable {

	private static final long serialVersionUID = 1159109307068508175L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Basic(optional = false)
	private String name;

	@Basic(optional = false)
	private String address;

	@Column(name = "transaction_number")
	@Basic(optional = false)
	private String transactionNumber;
	
	@Column(name = "ime_na_banka")
	@Basic(optional = false)
	private String imeNaBanka;
	
	public String getImeNaBanka() {
		return imeNaBanka;
	}

	public void setImeNaBanka(String imeNaBanka) {
		this.imeNaBanka = imeNaBanka;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

}
