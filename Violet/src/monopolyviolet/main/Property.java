/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

/**
 *
 * @author movillaf
 */
public class Property extends Card{

    private int numHotels;
    private int numHotel;

    public Property(int numHotels, int numHotel, String description, int identifier, int category, Object value, int perHouse, int perHotel) {
        super(description, identifier, category, value, perHouse, perHotel);
        this.numHotels = numHotels;
        this.numHotel = numHotel;
    }
    
    public Property(String description, int identifier, int category, Object value, int perHouse, int perHotel) {
        super(description, identifier, category, value, perHouse, perHotel);
    }

    public Property(String description, int identifier, int category, Object value) {
        super(description, identifier, category, value);
    }
    
    
    
}
