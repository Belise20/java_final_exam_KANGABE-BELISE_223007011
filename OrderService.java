package Service;

import dao.OrderDAO;
import model.CustomerProduct;
import model.Order;
import model.OrderItem;
import model.User;

import java.util.List;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAO();

    public List<Order> getAllOrders(){ return orderDAO.getAllOrders(); }

    public List<OrderItem> getOrderItems(int orderId){ return orderDAO.getOrderItems(orderId); }

    public boolean updateOrderStatus(int orderId, String status){ return orderDAO.updateOrderStatus(orderId, status); }

	public List<CustomerProduct> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	public void placeOrder(User loggedInUser, int productId, int orderQty) {
		// TODO Auto-generated method stub
		
	}
}
