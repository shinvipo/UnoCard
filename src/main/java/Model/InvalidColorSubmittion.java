/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author tranh
 */
public class InvalidColorSubmittion extends Exception{
    private Card.Color expected;
    private Card.Color actual;

    public InvalidColorSubmittion(String message, Card.Color expected, Card.Color actual) {
        this.expected = expected;
        this.actual = actual;
    }
    
}