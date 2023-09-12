package BonApp.BonApp.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
public class Review {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(nullable = false, length = 1000)
    private String comment;
    
    @Column(nullable = false)
    @Min(1)  
    @Max(5)  
    private Integer rating;
 

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String username;
    private LocalDate reviewDate;

	public Review(String comment, Integer rating, User user) {
		
		if (comment == null) {
            throw new IllegalArgumentException("Il commento non può essere nullo");
        }
        
        if (user == null) {
            throw new IllegalArgumentException("L'utente non può essere nullo");
        }
		
		if(rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
		}
		this.comment = comment;
		this.rating = rating;
	
		this.reviewDate = LocalDate.now();
		this.username = user.getUsername();
		}
	}
	

