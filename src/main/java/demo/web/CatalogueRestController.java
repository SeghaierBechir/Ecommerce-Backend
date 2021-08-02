package demo.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import demo.dao.ProductRepository;
import demo.entities.Product;

@CrossOrigin("*")
@RestController
public class CatalogueRestController {
	private ProductRepository productRepository;

	public CatalogueRestController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@GetMapping(path = "/photoProduct/{id}" ,produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getPhoto(@PathVariable("id")Long id) throws Exception {
		Product p=productRepository.findById(id).get();
		return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/econ/products/"+p.getPhotoName()));
	}
	
	@PostMapping(path = "/uploadPhoto/{id}")
	public void uploadPhoto(MultipartFile file,@PathVariable("id")Long id) throws Exception {
		Product p=productRepository.findById(id).get();
		p.setPhotoName(id+".jpg");
		Files.write(Paths.get(System.getProperty("user.home")+"/econ/products/"+p.getPhotoName()),file.getBytes());
		productRepository.save(p);
	}
	
	

}
