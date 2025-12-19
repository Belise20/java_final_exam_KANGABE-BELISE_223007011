package Service;

import dao.PaymentDAO;
import model.Payment;

import java.util.List;

public class PaymentService {

    private PaymentDAO paymentDAO = new PaymentDAO();

    public List<Payment> getAllPayments(){
        return paymentDAO.getAllPayments();
    }
}
