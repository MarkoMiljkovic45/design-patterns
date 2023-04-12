public class Client {

    public static void main(String[] args) {
        Sheet sheet = new Sheet(5,5);
        System.out.println(sheet);
        System.out.println();

        sheet.set("A1", "2");
        sheet.set("A2", "5");
        sheet.set("A3", "A1+A2");
        System.out.println(sheet);
        System.out.println();

        sheet.set("A1", "4");
        sheet.set("A4", "A1+A3");
        System.out.println(sheet);
        System.out.println();
        
        try {
            sheet.set("A1", "A3");
        }
        catch (RuntimeException e) {
            System.out.println("Caught exception: " + e.getMessage());
            System.out.println(sheet);
        }
    }
}
