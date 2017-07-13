package sbz.projekat.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sbz.projekat.entity.AkcijskiDogadjaj;
import sbz.projekat.service.ActionEventService;

@RequestMapping("/api/event")
@RestController
public class ActionEventController {

	@Autowired
	private ActionEventService actionEventService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> get()
	{ 
		List<AkcijskiDogadjaj> events = actionEventService.getAll();
		
		if (events == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(events, HttpStatus.OK);
	}
	
	@RequestMapping(value="/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> addEvent(@RequestBody AkcijskiDogadjaj event) { 
		actionEventService.saveActionEvent(event);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> deleteEvent(@RequestBody AkcijskiDogadjaj event) { 
		actionEventService.removeActionEvent(event);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateEvent(@RequestBody AkcijskiDogadjaj event) { 
		actionEventService.saveActionEvent(event);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
