package paystation.domain;
import java.util.*;


/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    public Map<Integer, Integer> coinsInserted = new HashMap<Integer, Integer>();

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: break;
            case 10: break;
            case 25: break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
        if (coinsInserted.containsKey(coinValue)){
            coinsInserted.put(coinValue, coinsInserted.get(coinValue) + 1);
        }
        else{
            coinsInserted.put(coinValue, 1);
        }
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        Map<Integer, Integer> mapBeforeCancel = new HashMap<Integer, Integer>();
        mapBeforeCancel.putAll(coinsInserted);
        reset();
        return mapBeforeCancel;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        coinsInserted.clear();
    }
    
    public int empty(){
        int totalAmountSinceLastCall = insertedSoFar;
        insertedSoFar = 0;
        return totalAmountSinceLastCall;
    }
    
    public Map<Integer, Integer> returnPayStationMap() {
        return coinsInserted;
    }
    
}
