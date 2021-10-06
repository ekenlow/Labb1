package View;

public class ItemInfo {
    private final int id;
    private final String type;
    private final String name;
    private final int stock;
    private final float price;

    public ItemInfo(int id, String type, String name, int stock, float price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
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
