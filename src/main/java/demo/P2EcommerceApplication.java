package demo;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import demo.dao.CategoryRepository;
import demo.dao.ProductRepository;
import demo.entities.Category;
import demo.entities.Product;
import net.bytebuddy.utility.RandomString;

@SpringBootApplication
public class P2EcommerceApplication implements CommandLineRunner {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private RepositoryRestConfiguration configuration;

	public static void main(String[] args) {
		SpringApplication.run(P2EcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		configuration.exposeIdsFor(Product.class,Category.class);
		categoryRepository.save(new Category(null, "Computers",null, null, null));
		categoryRepository.save(new Category(null, "Printers",null, null, null));
		categoryRepository.save(new Category(null, "Smart Phones",null, null, null));
		
		Random rnd=new Random();
		categoryRepository.findAll().forEach(c->{
			for (int i = 0; i < 10; i++) {
				Product p=new Product();
				p.setName(RandomString.make(10));
				p.setCurrentPrice(100+rnd.nextInt(10000));
				p.setPromotion(rnd.nextBoolean());
				p.setSelected(rnd.nextBoolean());
				p.setAvailable(rnd.nextBoolean());
				p.setPhotoName("unknown.png");
				p.setCategory(c);
				productRepository.save(p);
			}
			
		});
		
	}

}
