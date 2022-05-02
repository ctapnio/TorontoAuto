/*
 * Christian Tapnio
 * 991359879
 * */
package ca.sheridancollege.tapnioc.beans;

import java.io.Serializable;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3932313967928529007L;
	private int manufacturerID;
	private String manufacturer;

}
