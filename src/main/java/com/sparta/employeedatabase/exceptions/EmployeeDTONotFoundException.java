package com.sparta.employeedatabase.exceptions;

public class EmployeeDTONotFoundException extends Exception{
    public EmployeeDTONotFoundException(String name) {
        super("Could not find Author with name: " + name);
    }
}