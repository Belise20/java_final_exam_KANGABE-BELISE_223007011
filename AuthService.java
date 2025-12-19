package Service;

import dao.UserDAO;
import model.User;

import java.util.List;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers(){ return userDAO.getAllUsers(); }
    public boolean deleteUser(int id){ return userDAO.deleteUser(id); }
    public boolean updateUser(User u){ return userDAO.updateUser(u); }
    public boolean addUser(User u){ return userDAO.addUser(u); }

    // ✅ Add register method to fix UserFormDialog
    public boolean register(User u){
        return addUser(u);
    }

    // Login method for email/username
    public User login(String usernameOrEmail, String password){
        List<User> users = userDAO.getAllUsers();
        for(User u : users){
            if((u.getEmail().equalsIgnoreCase(usernameOrEmail) || 
                u.getUsername().equalsIgnoreCase(usernameOrEmail)) &&
                u.getPassword().equals(password)){
                return u;
            }
        }
        return null; // login failed
    }
}
