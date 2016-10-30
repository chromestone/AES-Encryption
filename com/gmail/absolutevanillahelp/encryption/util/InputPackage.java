package com.gmail.absolutevanillahelp.encryption.util;

public class InputPackage {

	private final int number;
	private final String string;
	
	public InputPackage(int number, String string) {
	
		this.number = number;
		this.string = string;
	}
	
	public int getNumber() {
		
		return number;
	}
	
	public String getString() {
	
		return string;
	}
}
