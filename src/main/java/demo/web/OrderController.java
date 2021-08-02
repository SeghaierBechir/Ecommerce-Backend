package demo.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.dao.ClientRepository;
import demo.dao.OrderItemRepository;
import demo.dao.OrderRepository;
import demo.dao.ProductRepository;
import demo.entities.Client;
import demo.entities.Order;
import demo.entities.OrderItem;
import demo.entities.Product;

@CrossOrigin("*")
@RestController
public class OrderController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@PostMapping("/orders")
	public Order saveOrder(@RequestBody OrderForm orderForm) {
		Client client = new Client();
		client.setName(orderForm.getClient().getName());
		client.setEmail(orderForm.getClient().getEmail());
		client.setAddresse(orderForm.getClient().getAddresse());
		client.setPhoneNumber(orderForm.getClient().getPhoneNumber());
		client.setUsername(orderForm.getClient().getUsername());
		client = clientRepository.save(client);
		System.out.println(client.getId());
		Order order = new Order();
		order.setClient(client);
		order.setDate(new Date());
		order=orderRepository.save(order);
		double total =0;
		for(OrderProduct p:orderForm.getProducts()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			Product product=productRepository.findById(p.getId()).get();
			orderItem.setProduct(product);
			orderItem.setPrice(product.getCurrentPrice());
			orderItem.setQuantity(p.getQuantity());
			orderItemRepository.save(orderItem);
			total +=p.getQuantity()*product.getCurrentPrice();
		}
		
		order.setTotalAmount(total);
		return orderRepository.save(order);
		
		
	}

}