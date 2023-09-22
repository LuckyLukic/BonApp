package BonApp.BonApp;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import BonApp.BonApp.service.OrdineSingoloService;




	
	@Component
	public class ChroneJob {

	    @Autowired
	    private OrdineSingoloService ordineService;

	    @Scheduled(cron = "*/20 * * * * *")
	    public void checkStatusSendEmail() throws IOException {
	        System.out.println("Executing checkAndUpdateFatturaStates at: " + LocalDate.now());
	        ordineService.checkStatusSendEmail();
	    }

}
