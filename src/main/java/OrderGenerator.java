import java.util.List;

public class OrderGenerator {
    public static Order getNewOrder(List<String> color){
        return new Order(
                "Sergey",
                "Steshov",
                "Moscow",
                4,
                "+7 999 999 99 99",
                5,
                "2020-11-11",
                "Samokat",
                color);
    }
}
