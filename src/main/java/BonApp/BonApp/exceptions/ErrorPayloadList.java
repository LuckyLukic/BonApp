package BonApp.BonApp.exceptions;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorPayloadList {

	private String message;
	private Date timestamp;
	private List<String> errorsList;
}
