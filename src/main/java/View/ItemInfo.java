package View;

public class ItemInfo {
    private String type;
    private String name;
    private int stock;
    private float price;

    public ItemInfo(String type, String name, int stock, float price) {
        this.type = type;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
