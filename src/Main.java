public class Main {
    public static void main(String[] args) {

        System.out.println("This is the initial version of this project - nothing is here.");

        Item item = new Item();
        try {
             item = new Item(20, 0, "Banana", 0);
        } catch (WrongItemException e) {
            System.out.println(e.getMessage());
        }

       try {
           item.addEvidence(20);
           System.out.println(item.toString());
           int earnings = 0;
           earnings = earnings + item.itemSold(-10);
           System.out.println(earnings);
           System.out.println(item.toString());

           item.setPrice(25);
           earnings = earnings + item.itemSold(-10);
           System.out.println(earnings);
           System.out.println(item.toString());

           item.setPrice(5);
           item.addEvidence(50);
           System.out.println(item.toString());
           item.setPrice(50);

           earnings = earnings + item.itemSold(-50);
           System.out.println(earnings);
           System.out.println(item.toString());

       }catch (WrongEvidenceException | WrongItemException e){
           System.out.println(e.getMessage());
       }


    }

    public static void testItem1() {
        Item item = new Item();
        try {
            item.setName("Banana");
            item.setPrice(20);
            item.setWholeBoughtPrice(20);
            item.setAmount(1);
        } catch (IllegalArgumentException | WrongItemException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(item.toString());

        try {
            item.moveWithAmount(-1);
        } catch (WrongItemException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(item.toString());
    }
}