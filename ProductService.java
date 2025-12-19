package Service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductService {

    private ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllProducts(){ return productDAO.getAllProducts(); }
    public boolean addProduct(Product p){ return productDAO.addProduct(p); }
    public boolean updateProduct(Product p){ return productDAO.updateProduct(p); }
    public boolean deleteProduct(int id){ return productDAO.deleteProduct(id); }
}
