package BonApp.BonApp;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.github.javafaker.Faker;

import BonApp.BonApp.Enum.Categoria;
import BonApp.BonApp.Enum.Role;
import BonApp.BonApp.entities.Indirizzo;
import BonApp.BonApp.entities.Ingredienti;
import BonApp.BonApp.entities.Ordine;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;

import BonApp.BonApp.repositories.IndirizzoRepository;
import BonApp.BonApp.repositories.IngredienteRepository;
import BonApp.BonApp.repositories.OrdineRepository;
import BonApp.BonApp.repositories.ProdottoRepository;
import BonApp.BonApp.repositories.UserRepository;
import BonApp.BonApp.security.AuthController;


import jakarta.transaction.Transactional;


@Component
public class Runner implements CommandLineRunner {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IndirizzoRepository indirizzoRepository;
	
	@Autowired
	OrdineRepository ordineRepository;
	
	@Autowired
	ProdottoRepository prodottoRepository;
	
	@Autowired
	IngredienteRepository ingredienteRepository;
	
	@Autowired
	AuthController authController;

	

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		

		List<User> utentiDb = userRepository.findAll();
		if (utentiDb.isEmpty()) {

			for (int i = 0; i < 10; i++) {
                String username = faker.name().username();
                String name = faker.name().firstName();
                String surname = faker.name().lastName();
                String email = faker.internet().emailAddress();
                String password = "Infedele1980!";
                String via = faker.address().streetAddress();
                int civico = faker.number().numberBetween(1, 100); // Adjust the range as needed
                String localita = faker.address().city();
                String cap = faker.number().digits(5); // 5-digit postal code
                String comune = faker.address().state();

                // Create a User object and an associated Address object
                User user = new User(username, name, surname, email, null, password);
                Indirizzo address = new Indirizzo(cap, civico, localita, via, user, comune);

                user.setIndirizzo(address);
                user.setRole(Role.USER); // Set the user's role

                // Save the user and address objects to your database
                userRepository.save(user);

			}
			
		}
			
			List<Ingredienti> ingredientiDb = ingredienteRepository.findAll();
			if (ingredientiDb.isEmpty()) {
		
		    
			
			for (int i = 0; i < 30; i++) {
		        String nomeIngrediente = faker.food().ingredient();
		        
		        // Create a new Ingredienti instance
		        Ingredienti ingrediente = new Ingredienti(nomeIngrediente);	
		        
		        ingredienteRepository.save(ingrediente);
		
	}
}
			
			List<Prodotto> prodottiDb = prodottoRepository.findAll();
			if (prodottiDb.isEmpty()) {
				
				// Fetch all available ingredients from the database
			    List<Ingredienti> allIngredients = ingredienteRepository.findAll();
				
				for (int i = 0; i < 20; i++) {
			        String productName = faker.food().dish();
			        String productDescription = faker.lorem().sentence();
			        double productPrice = faker.number().randomDouble(2, 1, 100); // Generate a random price
			        Categoria productCategory = Categoria.values()[faker.number().numberBetween(0, Categoria.values().length)]; // Generate a random category

			        // Generate a random number of ingredients between 3 and 6
			        int numIngredients = faker.number().numberBetween(3, 7);
			        
			        // Shuffle the list of all ingredients randomly
			        Collections.shuffle(allIngredients);

			        // Select the first numIngredients from the shuffled list to get unique ingredient IDs
			        List<Ingredienti> productIngredients = allIngredients.subList(0, numIngredients);

			        // Create a new Prodotto instance
			        Prodotto prodotto = new Prodotto(productName, productDescription, productPrice, productCategory, productIngredients, "");
			        
			        // Save the product to the database
			        prodottoRepository.save(prodotto);			 
			        }
			}
			
			
			List<Ordine> ordineDb = ordineRepository.findAll();
			if (ordineDb.isEmpty()) {
				
				
				// Fetch all users from the database
			    List<User> allUsers = userRepository.findAll();

			    // Create orders for each user
			    for (User user : allUsers) {
			        int numOrders = faker.number().numberBetween(2, 6); // Generate a random number of orders between 2 and 5
			        
			        for (int i = 0; i < numOrders; i++) {
			        	
			        	//LocalDate orderDate = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Generate a random order date within the past year
			            //LocalTime orderTime = LocalTime.of(faker.number().numberBetween(0, 23), faker.number().numberBetween(0, 59)); // Generate a random order time

			            // Fetch all available products from the database
			            List<Prodotto> allProducts = prodottoRepository.findAll();

			            // Generate a random number of products between 1 and 5 for the order
			            int numProducts = faker.number().numberBetween(1, 6);

			            // Shuffle the list of all products randomly
			            Collections.shuffle(allProducts);

			            // Select the first numProducts from the shuffled list to get unique product IDs
			            List<Prodotto> orderProducts = allProducts.subList(0, numProducts);

			            // Create a new Ordine instance
			            Ordine order = new Ordine(user, orderProducts);

			            // Save the order to the database
			            ordineRepository.save(order);
			        }
			    }
			
			
	}
}
}
			
	
		
		
		
