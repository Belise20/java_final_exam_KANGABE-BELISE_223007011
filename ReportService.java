package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import dao.DBUtil;

public class ReportService {

    // Total revenue
    public double getTotalRevenue(){
        String sql = "SELECT SUM(total_amount) as total FROM orders WHERE status='delivered'";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            if(rs.next()){
                return rs.getDouble("total");
            }
        } catch(Exception e){ e.printStackTrace(); }
        return 0;
    }

    // Total orders
    public int getTotalOrders(){
        String sql = "SELECT COUNT(*) as total FROM orders";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            if(rs.next()){
                return rs.getInt("total");
            }
        } catch(Exception e){ e.printStackTrace(); }
        return 0;
    }

    // Top products by quantity sold
    public Map<String, Integer> getTopProducts(int limit){
        Map<String,Integer> topProducts = new LinkedHashMap<>();
        String sql = "SELECT p.product_name, SUM(oi.quantity) as total_qty " +
                     "FROM order_items oi " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "GROUP BY p.product_id " +
                     "ORDER BY total_qty DESC " +
                     "LIMIT ?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                topProducts.put(rs.getString("product_name"), rs.getInt("total_qty"));
            }
        } catch(Exception e){ e.printStackTrace(); }
        return topProducts;
    }
}
