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
public class Card {
    public enum Color{
        Red, Blue, Green, Yellow, Wild;
        
        private static final Color[] colors = Color.values();
        
        public static Color getColor(int i){
            return Color.colors[i];
        }
    }
    
    public enum Value{
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip, Reverse, Wild, Wild_Four;
        
        private static final Value[] values = Value.values();
        
        public static Value getvalue(int i){
            return Value.values[i];
        }
    }
    
    private final Color color;
    private final Value value;
    
    public Card(final Color color, final Value value){
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public Value getValue() {
        return value;
    }
    
    public String toString(){
        return color + "_" + value;
    }
}
