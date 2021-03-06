package demo.web;

import java.util.ArrayList;
import java.util.List;

import demo.entities.Client;
import lombok.Data;

@Data
public class OrderForm {
	private Client client = new Client();
	private List<OrderProduct> products = new ArrayList<>();

}
@Data
class OrderProduct{
	private Long id;
	private int quantity;
}
