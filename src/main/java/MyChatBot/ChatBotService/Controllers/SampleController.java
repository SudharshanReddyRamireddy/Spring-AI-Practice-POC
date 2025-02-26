package MyChatBot.ChatBotService.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class SampleController {
	
	@RequestMapping("/")
	public ResponseEntity<String> sample(){
	
		return ResponseEntity.status(HttpStatus.OK).body("Hi...!");
	}

}
