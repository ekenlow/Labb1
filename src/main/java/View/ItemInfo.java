package View;

public class ItemInfo {
    private final String type;
    private final String name;
    private final int stock;
    private final float price;

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

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public float getPrice() {
        return price;
    }
}
