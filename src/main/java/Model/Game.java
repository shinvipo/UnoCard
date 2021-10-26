/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author tranh
 */
public class Game {
    private int currentPlayer;
    private String[] playerIds;
    
    private CardDeck deck;
    private ArrayList <ArrayList<Card>> playerHand;
    private ArrayList<Card> stockpile; //stock dung khi dat la bai xuong
    
    private Card.Color validColor;
    private Card.Value validValue;
    
    boolean gameDirection; // chieu danh bai => vidu: theo chieu kim dong ho
    
    public Game(String[] pids){
        deck = new CardDeck();
        deck.shuffle();
        stockpile = new ArrayList<Card>();
        
        playerIds = pids;
        currentPlayer   = 0;
        gameDirection = false;
        
        playerHand = new ArrayList<ArrayList<Card>>();
        //System.out.println("gamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegamegame");
        /*chia bai`*/
        for(int i=0; i< pids.length; ++i){
            ArrayList<Card> hand = new ArrayList<Card>(Arrays.asList(deck.drawCard(7)));
            playerHand.add(hand);
        }
  
    }
    
    public void start(Game game){
        Card card = deck.drawCard();
        validColor = card.getColor();
        validValue = card.getValue();
        
        if(card.getValue() == Card.Value.Wild){
            start(game);
        }
        
        if(card.getValue() == Card.Value.Wild_Four || card.getValue() == Card.Value.DrawTwo){
            start(game);
        }
        
        if(card.getValue() == Card.Value.Skip){
            JLabel msg = new JLabel(playerIds[currentPlayer] + "was Skipped!");
            msg.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msg);
            
            if(gameDirection    == false){
                currentPlayer = (currentPlayer + 1) % playerIds.length;
            }
            
            else if( gameDirection == true){
                currentPlayer = (currentPlayer - 1) % playerIds.length;
                if(currentPlayer == -1){
                    currentPlayer = playerIds.length-1;
                }
            }
        }
        
        if(card.getValue() == Card.Value.Reverse){
            JLabel msg = new JLabel(playerIds[currentPlayer] + "The Game diretion changed!");
            msg.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msg);
            
            gameDirection ^= true;
            currentPlayer = playerIds.length - 1;
            
        }
        
        stockpile.add(card);
    }
    
    public Card getTopCard(){
        return new Card(validColor, validValue);
    }
    
    public  ImageIcon getTopCardImage(){
        return new ImageIcon(validColor + "_" + validValue + ".png");
    }
    
    public boolean isGameOver(){
        for(String player : this.playerIds){
            if(hasEmptyHand(player)){
                return true;
            }
        }
        return false;
    }
    
    public String getCurrentPlayer(){
        return this.playerIds[this.currentPlayer];
    }
    
    public String getPrivPlayer(int i){
        int index = this.currentPlayer - i ;
        if(index == -1){
            index = playerIds.length - 1;
        }
        return this.playerIds[index];
    }
    
    public String[] getPlayers(){
        return playerIds;
    }
    
    public ArrayList<Card> getPlayerHand(String pids){
        int index = Arrays.asList(playerIds).indexOf(pids);
        return playerHand.get(index);
    }
    
    public int getPlayerHandSize(String pids){
        return getPlayerHand(pids).size();
    }
    
    public Card getPlayerCard(String pid, int choice){
        ArrayList<Card> hand = getPlayerHand(pid);
        return hand.get(choice);
    }

    private boolean hasEmptyHand(String player) {
        return getPlayerHand(player).isEmpty();
    }
    
    private boolean  validCardPlay(Card card){
        return card.getColor() == validColor || card.getValue() == validValue;
    }
    
    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException{
        if(this.playerIds[this.currentPlayer] != pid){
            throw new InvalidPlayerTurnException("Its not "+ pid + "'s turn", pid);
        }
    }
    
    public void submitDraws (String pid) throws InvalidPlayerTurnException{
        checkPlayerTurn(pid);
        
        if(deck.isEmpty()){
            deck.replaceDeckWith(stockpile);
            deck.shuffle();
        }
        
        getPlayerHand(pid).add(deck.drawCard());
        if(gameDirection == false){
            currentPlayer = (currentPlayer + 1 ) % playerIds.length;
        }
        else if(gameDirection == true){
            currentPlayer = (currentPlayer -  1)% playerIds.length;
            
            if(currentPlayer == -1){
                currentPlayer = playerIds.length -1;
            }
        }
    }
    
    public void setCardColor(Card.Color color){
        validColor = color;
    }
    
    public void submitPlayerCard(String pid, Card card, Card.Color declareColor) throws  InvalidColorSubmittion, InvalidValueSubmittion, InvalidPlayerTurnException{
        checkPlayerTurn(pid);
        
        ArrayList<Card> pHand = getPlayerHand(pid);
        
        if(!validCardPlay(card)){
            if(card.getColor() == Card.Color.Wild){
                validColor = card.getColor();
                validValue = card.getValue();
            }
            
            if(card.getColor() != validColor){
                String txt = "Invalid player move, expected color: " + validColor + " but got color " + card.getColor();
                JLabel message = new JLabel("Invalid player move, expected color: " + validColor + " but got color " + card.getColor());
                message.setFont(new Font("Arial", Font.BOLD, 48));
                JOptionPane.showMessageDialog(null, message);
                throw new InvalidColorSubmittion(txt, validColor, card.getColor());
            }
            else if(card.getValue() != validValue){
                String txt = "Invalid player move, expected value: " + validValue + " but got value " + card.getValue();
                JLabel message = new JLabel("Invalid player move, expected value: " + validValue + " but got value " + card.getValue());
                message.setFont(new Font("Arial", Font.BOLD, 48));
                JOptionPane.showMessageDialog(null, message);
                throw new InvalidValueSubmittion(validValue, card.getValue(), txt);
            }
        }
        
        pHand.remove(card);
        
        if(hasEmptyHand(this.playerIds[currentPlayer])){
            JLabel msg2 = new JLabel(this.playerIds[currentPlayer] + "won the game!");
            msg2.setFont(new Font("Arial",Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msg2);
            System.exit(0);
        }
        
        validColor = card.getColor();
        validValue = card.getValue();
        stockpile.add(card);
        
        if(gameDirection == false){
            currentPlayer = (currentPlayer + 1) % playerIds.length;
        }
        else if(gameDirection == true){
            currentPlayer = (currentPlayer - 1) % playerIds.length;
            if(currentPlayer == -1){
                currentPlayer = playerIds.length -1;
            }
        }
        
        if(card.getColor() == Card.Color.Wild){
            validColor = declareColor;
        }
        
        if(card.getValue() == Card.Value.DrawTwo){
            pid = playerIds[currentPlayer];
            getPlayerHand(pid).add(deck.drawCard());
            getPlayerHand(pid).add(deck.drawCard());
            JLabel msg = new JLabel(pid + "drew 2 cards!");
        }
        
        if(card.getValue() == Card.Value.Wild_Four){
            pid = playerIds[currentPlayer];
            getPlayerHand(pid).add(deck.drawCard());
            getPlayerHand(pid).add(deck.drawCard());
            getPlayerHand(pid).add(deck.drawCard());
            getPlayerHand(pid).add(deck.drawCard());
            JLabel msg = new JLabel(pid + "drew 4 cards!");
        }
        
        if( card.getValue() == Card.Value.Skip){
            JLabel msgk = new JLabel(playerIds[currentPlayer] + "was skipped!");
            msgk.setFont(new Font("Arial",Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msgk);
            
            if(gameDirection == false){
            currentPlayer = (currentPlayer + 1) % playerIds.length;
            }
            else if(gameDirection == true){
                currentPlayer = (currentPlayer - 1) % playerIds.length;
                if(currentPlayer == -1){
                    currentPlayer = playerIds.length -1;
                }
            }
        }
        
        if(card.getValue() == Card.Value.Reverse){
            JLabel msgk = new JLabel(playerIds[currentPlayer] + "changed game direction!");
            msgk.setFont(new Font("Arial",Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, msgk);
            
            gameDirection ^= true;
            if(gameDirection == true){
                currentPlayer = (currentPlayer -2) % playerIds.length;
                if(currentPlayer == -1){
                    currentPlayer = playerIds.length -1;
                }
                if(currentPlayer == -2){
                    currentPlayer = playerIds.length -2;
                }
            }
            else if(gameDirection == false){
                currentPlayer = (currentPlayer + 2) % playerIds.length;
                
            }
        }
    }
}

//class InvalidPlayerTurnException extends Exception{
//    String playerId;
//    
//    public InvalidPlayerTurnException(String message, String pid) {
//        super(message);
//        playerId = pid;
//    }
//
//    public String getPlayerId() {
//        return playerId;
//    }
//    
//}

//class InvalidColorSubmittion extends Exception{
//    private Card.Color expected;
//    private Card.Color actual;
//
//    public InvalidColorSubmittion(String message, Card.Color expected, Card.Color actual) {
//        this.expected = expected;
//        this.actual = actual;
//    }
//    
//}
//
//class InvalidValueSubmittion extends Exception{
//    private Card.Value expected;
//    private Card.Value actual;
//
//    public InvalidValueSubmittion(Card.Value expected, Card.Value actual, String message) {
//        super(message);
//        this.expected = expected;
//        this.actual = actual;
//    }
//}
