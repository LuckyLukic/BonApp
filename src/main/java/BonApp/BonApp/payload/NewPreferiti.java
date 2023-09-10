package BonApp.BonApp.payload;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NewPreferiti {
	
	private UUID userId;
    private UUID productId;
    private String message;

}
