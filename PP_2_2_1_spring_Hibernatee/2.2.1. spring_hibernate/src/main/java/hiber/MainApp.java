package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
      UserService userService = context.getBean(UserService.class);

      List<User> users = userService.listUsers();

      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         Car car = user.getCar();
         if (car!= null) {
            System.out.println("Car = " + car.getModel() + "-" + car.getSeries());
         } else {
            System.out.println("User has no car.");
         }
         System.out.println();
      }
      //метод getUserByCar возвращает не null перед вызовом getFirstName()
      User userByCar = userService.getUserByCar("ВАЗ", 2111);
      if (userByCar!= null) {
         System.out.println(userByCar.getFirstName());
      } else {
         System.out.println("User not found.");
      }

      context.close();
   }
}