package model;

import java.util.List;

public class CustomerProduct {
    private int productId;
    private String productName;
    private String category;   // added category
    private double price;
    private int quantity;      // available stock

    // Default constructor
    public CustomerProduct() {}

    // Full constructor
    public CustomerProduct(int productId, String productName, String category, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters & Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

	public static List<CustomerProduct> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}
}
