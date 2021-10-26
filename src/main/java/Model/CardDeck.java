/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author tranh
 */
public class CardDeck {
    private Card[] cards;
    private int cardsInDeck;

    public CardDeck() {
        cards = new Card[108];
        reset();
    }
    
    public void reset(){
        Card.Color[] colors = Card.Color.values();
                
        cardsInDeck = 0;

        for (int i=0 ; i < colors.length -1 ; ++i){
            Card.Color color = colors[i];
            
            cards[cardsInDeck++] = new Card(color, Card.Value.getvalue(0));
            
            for (int j=1; j< 10; ++j){
                cards[cardsInDeck++] = new Card(color, Card.Value.getvalue(j));
                cards[cardsInDeck++] = new Card(color, Card.Value.getvalue(j));
            }
            
            Card.Value[] values = new Card.Value[]{Card.Value.DrawTwo, Card.Value.Skip, Card.Value.Reverse } ;//, Card.Value.Wild, Card.Value.Wild_Four};
            
            for ( Card.Value value : values ){
                cards[cardsInDeck++] = new Card(color, value);
                cards[cardsInDeck++] = new Card(color, value);
            }
  
        }
        
        Card.Value[] values = new Card.Value[]{Card.Value.Wild, Card.Value.Wild_Four};
        
        for (Card.Value value : values){
            for(int i = 0 ; i < 4; ++i){
                cards[cardsInDeck++] = new Card(Card.Color.Wild, value);
            }
        }
    }
    
    public void replaceDeckWith(ArrayList<Card> cards){
        this.cards = cards.toArray(new Card[cards.size()]);
        this.cardsInDeck = this.cards.length;
    }
    
    public boolean isEmpty(){
        return cardsInDeck == 0;
    }
    
    public void shuffle(){
        int n = cards.length;
        Random random = new Random();
        
        for(int i = 0; i < cards.length; ++i){
            int randomvalue = (i + random.nextInt(n-1)) % 108;
            Card randomCard = cards[randomvalue];
            cards[randomvalue] = cards[i];
            cards[i] = randomCard;
        }
    }
    
    public Card drawCard() throws IllegalArgumentException{
        if(isEmpty()){
            throw  new IllegalArgumentException("Cannot Draw a crad since there are no card in deck");
        }
        return cards[--cardsInDeck];
    }
    
    public ImageIcon drawCardImage() throws IllegalArgumentException{
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot Draw a crad since the deck is empty");
        }
        return new ImageIcon(cards[--cardsInDeck].toString() + ".png");
    }
    
    public Card[] drawCard(int n){
        if(n < 0){
            throw new IllegalArgumentException("Must Draw Positive Cards But Tried To Draw " + n + " cards.");
        }
        
        if(n > cardsInDeck){
            throw new IllegalArgumentException("Cannot draw " + n + " cards since there are only " + cardsInDeck + " cards.");
        }
        
        Card[] ret = new Card[n];
        for(int i=0; i<n;++i){
            ret[i] = cards[--cardsInDeck];
        }
        return ret;
    }
    
}
