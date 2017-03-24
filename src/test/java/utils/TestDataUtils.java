package utils;

import com.tw.security.demo.dao.OrderDao;
import com.tw.security.demo.dao.UserDao;
import com.tw.security.demo.domain.DealerUser;
import com.tw.security.demo.domain.Order;
import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.UserRole;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class TestDataUtils {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private OrderDao orderDao;

    @Required
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Required
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Required
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public User createUser(String username, String password, UserRole role) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userDao.insert(user);

        Optional<User> userFromDB = userDao.findByUsername(username);
        if (!userFromDB.isPresent()) {
            throw new Exception("test data error!");
        }

        return userFromDB.get();
    }

    public Order createOrder(Integer userId, Integer storeId) throws Exception {
        Order order = new Order();
        order.setContent("this is a default order");
        order.setOwnerId(userId);
        order.setStoreId(storeId);
        order.setStatus("initial");

        orderDao.create(order);

        Optional<Order> latestOrder = orderDao.findLatest(userId, storeId);
        if (!latestOrder.isPresent()) {
            throw new Exception("test data error, order not found");
        }

        return latestOrder.get();
    }

    public void createDealerUser(DealerUser dealerUser) {
        jdbcTemplate.update("insert into dealer_users(user_id, store_id, name) values(?, ?, ?)",
                dealerUser.getUserId(), dealerUser.getStoreId(), dealerUser.getName());
    }

    public void deleteAllUsers() {
        jdbcTemplate.update("truncate table users");
    }

    public void deleteAllOrders() {
        jdbcTemplate.update("truncate table orders");
    }

    public void deleteAllDealerUsers() {
        jdbcTemplate.update("truncate table dealer_users");
    }
}
