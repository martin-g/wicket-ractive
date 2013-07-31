package com.mycompany.model;

import java.io.Serializable;
import java.util.List;

import com.mycompany.ractive.RactiveObject;

/**
 *
 */
public class Person implements Serializable, RactiveObject
{
	public String firstName;
	public String lastName;
	public String email;

	public Address address;

	public List<String> cars;
}
