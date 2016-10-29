/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

/**
 *
 * @author movillaf
 */
public class Card {
    
    public static int COMMUNITY_CHEST_ID = 1;
    public static int CHANCE_ID = 2;
    public static int PROPERTY_ID = 3;
    
    public static int PAY_CATEGORY = 1;
    public static int COLLECT_CATEGORY = 2;
    public static int PER_ITEM_CATEGORY = 3;
    public static int ADVANCE_CATEGORY = 4;
    public static int RETRACT_CATEGORY = 5;
    public static int FREE_JAIL_CATEGORY = 6;
    public static int PROPERTY_CATEGORY = 7;
    
    private final String description;
    private final int identifier;
    private final int category;
    
    /**
     * Represents the value to pay or collect in PAY_CATEGORY or COLLECT_CATEGORY cards.\n
     * In PER_ITEM_CATEGORY cards, this value is 0.\n
     * In ADVANCE_CATEGORY cards, this value represents the space to advance to.\n
     * In ADVANCE_CATEGORY cards, this value can also represent the category of space to advance to (expressed as String).\n
     * In RETRACT_CATEGORY cards, this value represents the number of spaces to retract.\n
     * In FREE_JAIL_CATEGORY cards, this value is 0.
     */
    private final Object value;
    
    private int perHouse;
    private int perHotel;
    
    /*
Community Chest list: 

    Go to jail – go directly to jail – Do not pass Go, do not collect $200 
    * 
    Get out of jail free – this card may be kept until needed, or sold 
    * 
    You are assessed for street repairs – $40 per house, $115 per hotel 
    * 
    Advance to Go (Collect $200) 
    Bank error in your favor – collect $75 
    Doctor's fees – Pay $50 
    It is your birthday Collect $10 from each player 
    Grand Opera Night – collect $50 from every player for opening night seats 
    Income Tax refund – collect $20 
    Life Insurance Matures – collect $100 
    Pay Hospital Fees of $100 
    Pay School Fees of $50 
    Receive $25 Consultancy Fee 
    You have won second prize in a beauty contest– collect $10 
    You inherit $100 
    From sale of stock you get $50 
    Holiday Fund matures - Receive $100 

Chance list: 

    Advance to Go (Collect $200) 
    Advance to Illinois Ave. 
    Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown. 
    Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank. (There are two of these.) 
    Advance to St. Charles Place – if you pass Go, collect $200 
    Take a trip to Reading Railroad – if you pass Go collect $200 
    Take a walk on the Boardwalk – advance token to Boardwalk 
    Go directly to Jail – do not pass Go, do not collect $200
    * 
    Go back 3 spaces 
    * 
    Get out of Jail free – this card may be kept until needed, or traded/sold 
    *
    Make general repairs on all your property – for each house pay $25 – for each hotel $100 
    * 
    Bank pays you dividend of $50 
    Pay poor tax of $15 
    You have been elected chairman of the board – pay each player $50 
    Your building loan matures – collect $150 
    You have won a crossword competition - collect $100
     */

    public Card(String description, int identifier, int category, Object value) {
        this.description = description;
        this.identifier = identifier;
        this.category = category;
        this.value = value;
    }

    public Card(String description, int identifier, int category, Object value, int perHouse, int perHotel) {
        this.description = description;
        this.identifier = identifier;
        this.category = category;
        this.value = value;
        this.perHouse = perHouse;
        this.perHotel = perHotel;
    }
    
}
