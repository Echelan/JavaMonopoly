/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

/**
 *
 * @author movillaf
 */
public class Card extends Node{
    
    public static int COMMUNITY_CHEST_ID = 1;
    public static int CHANCE_ID = 2;
    
    public static int FREE_JAIL_ID = 0;
    public static int PAY_ID = 1;
    public static int COLLECT_ID = 2;
    public static int OTHER_ID = 3;
	
	public static int NA = 0;
    public static int PER_ITEM_ID = 1;
    public static int PER_PLAYER_ID = 2;
    public static int ADVANCE_ID = 3;
    public static int ADVANCE_TO_ID = 4;
    
    private String title;
    private String description;
    private int cardType;
    private int category;
	
    private String value;
    
    public Card(int id) {
        readInfo(id);
    }
    
	private void readInfo(int id) {
		for (int j = 0; j < monopolyviolet.data.NIC.INFO_CARDS.size(); j++) {
			String[] cardInfo = monopolyviolet.data.NIC.INFO_CARDS.get(j).split(";");
			for (int i = 0; i < cardInfo.length; i++) {
				String[] partes = cardInfo[i].split("=");
				if (partes[0].compareTo("title")==0) {
					this.title = partes[1];
				}else if (partes[0].compareTo("description")==0) {
					this.description = partes[1];
				}else if (partes[0].compareTo("id")==0) {
					if (partes[1].compareTo("COMMUNITY")==0) {
						this.cardType = COMMUNITY_CHEST_ID;
					} else if (partes[1].compareTo("CHANCE")==0) {
						this.cardType = CHANCE_ID;
					}
				}else if (partes[0].compareTo("flags")==0) {
					String flags = partes[1];
					System.out.println(flags);
					if (flags.contains("at")) {
						System.out.println("ADVANCE TO CARD");
					} else if (flags.contains("ai")) {
						System.out.println("GO TO JAIL CARD");
					} else if (flags.contains("a")) {
						System.out.println("ADVANCE CARD");
					} else if (flags.contains("cp")) {
						System.out.println("COLLECT PER PLAYER CARD");
					} else if (flags.contains("c")) {
						System.out.println("COLLECT CARD");
					} else if (flags.contains("pi")) {
						System.out.println("PAY PER ITEM CARD");
					} else if (flags.contains("pp")) {
						System.out.println("PAY PER PLAYER CARD");
					} else if (flags.contains("p")) {
						System.out.println("PAY CARD");
					} else if (flags.contains("fj")) {
						System.out.println("FREE JAIL CARD");
					}
				}else if (partes[0].compareTo("value")==0) {
					this.value = partes[1];
				}
			}
		}
	}
}
