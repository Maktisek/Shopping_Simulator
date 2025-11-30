import java.io.*;

public class Main {
    public static void main(String[] args) {



    playerLoadAndSaveTest();

    }

    public static void playerLoadAndSaveTest(){
        Player player = new Player();
        System.out.println(player.getItems());
        try {
            player.getItems().get(4).setPrice(Utility.priceUpdater(player.getItems().get(4).getInitialPrice()));
        } catch (WrongItemException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(player.getItems());
        player.save();
    }
//    public static void priceUpdaterTest(){
//        int initialBananaPrice = 5000;
//        System.out.println(initialBananaPrice);
//        initialBananaPrice = Utility.priceUpdater(initialBananaPrice);
//        System.out.println(initialBananaPrice);
//    }
//    public static void toStringTest(){
//        Item item = new Item();
//        try {
//            item = new Item(20, 0, "Banana", 0, 10);
//        } catch (WrongItemException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            item.itemBought(20);
//            item.setPrice(55);
//            System.out.println(item.toString());
//        }catch (WrongItemException | WrongEvidenceException e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static void itemLoaderTest(){
//        Item item = new Item();
//
//        try {
//            item = new Item(20, 0, "Banana", 0,10);
//        } catch (WrongItemException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            item.itemBought(52);
//            item.setPrice(12);
//            item.itemBought(20);
//        }catch (WrongEvidenceException | WrongItemException e){
//            System.out.println(e.getMessage());
//        }
//
//
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("res\\test.txt"))){
//            bw.write("price>wholeBoughtPrice>name>amount>initialPrice>now all evidences in (amount>price) \n");
//            bw.write(item.intoCSV());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//
//        /**
//         * Tady je algoritmus na loadeni itemu.
//         */
//        Item item2 = new Item();
//        try (BufferedReader br = new BufferedReader(new FileReader("res\\test.txt"))){
//            br.readLine();
//            String line;
//            while ((line = br.readLine()) != null){
//                String[] data = line.split(">");
//                item2 = new Item(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
//                item2.loadEvidences(data);
//            }
//        }catch (IOException | WrongItemException | WrongEvidenceException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println(item2.getWholeBoughtPrice());
//        System.out.println(item2.intoCSV());
//    }
//
//    public static void testItemSellingAndBuying(){
//        Item item = new Item();
//        try {
//            item = new Item(20, 0, "Banana", 0, 10);
//        } catch (WrongItemException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            item.itemBought(20);
//            System.out.println(item.toString());
//            int earnings = 0;
//            earnings = earnings + item.itemSold(-10);
//            System.out.println(earnings);
//            System.out.println(item.toString());
//
//            item.setPrice(25);
//            earnings = earnings + item.itemSold(-10);
//            System.out.println(earnings);
//            System.out.println(item.toString());
//
//            item.setPrice(5);
//            item.itemBought(50);
//            System.out.println(item.toString());
//            item.setPrice(50);
//
//            earnings = earnings + item.itemSold(-50);
//            System.out.println(earnings);
//            System.out.println(item.toString());
//
//        }catch (WrongEvidenceException | WrongItemException e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static void testItem1() {
//        Item item = new Item();
//        try {
//            item.setName("Banana");
//            item.setPrice(20);
//            item.setWholeBoughtPrice(20);
//            item.setAmount(1);
//        } catch (IllegalArgumentException | WrongItemException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(item.toString());
//
//        try {
//            item.moveWithAmount(-1);
//        } catch (WrongItemException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(item.toString());
//    }
}