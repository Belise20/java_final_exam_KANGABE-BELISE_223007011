package dao;

import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory(rs.getString("category"));
                products.add(p);
            }
        } catch(Exception e){ e.printStackTrace(); }
        return products;
    }

    public boolean addProduct(Product p){
        String sql = "INSERT INTO products(product_name, price, quantity, category) VALUES(?,?,?,?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,p.getProductName());
            stmt.setDouble(2,p.getPrice());
            stmt.setInt(3,p.getQuantity());
            stmt.setString(4,p.getCategory());
            return stmt.executeUpdate() > 0;
        } catch(Exception e){ e.printStackTrace(); return false; }
    }

    public boolean updateProduct(Product p){
        String sql = "UPDATE products SET product_name=?, price=?, quantity=?, category=? WHERE product_id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,p.getProductName());
            stmt.setDouble(2,p.getPrice());
            stmt.setInt(3,p.getQuantity());
            stmt.setString(4,p.getCategory());
            stmt.setInt(5,p.getProductId());
            return stmt.executeUpdate() > 0;
        } catch(Exception e){ e.printStackTrace(); return false; }
    }

    public boolean deleteProduct(int productId){
        String sql = "DELETE FROM products WHERE product_id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch(Exception e){ e.printStackTrace(); return false; }
    }
}
