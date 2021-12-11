/*
 * Christian Tapnio
 * 991359879
 * Assignment 3
 * */
package ca.sheridancollege.tapnioc.beans;

import java.io.Serializable;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4948111270318913044L;
	private int carID;
	private int manufacturerID;
	private String model;
	private int year;
	private String color;
	private Double price;

}
