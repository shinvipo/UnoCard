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
public class InvalidValueSubmittion extends Exception{
    private Card.Value expected;
    private Card.Value actual;

    public InvalidValueSubmittion(Card.Value expected, Card.Value actual, String message) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }
}