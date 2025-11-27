public class Utility {

    public static String colourMap(String colour){
        switch (colour.toLowerCase()){
            case "red" -> {
                return "\u001B[31m";
            }
            case "blue" -> {
                return "\u001B[34m";
            }
            case "green" -> {
                return "\u001B[32m";
            }
            case "yellow" -> {
                return "\u001B[33m";
            }
            case "orange" -> {
                return "\u001B[91m";
            }
            case "pink" -> {
                return "\u001B[95m";
            }
            case "gray" -> {
                return "\u001B[90m";
            }
            case "purple" ->{
                return "\u001B[35m";
            }
            default -> {
                return "\u001B[0m";
            }
        }
    }
}
