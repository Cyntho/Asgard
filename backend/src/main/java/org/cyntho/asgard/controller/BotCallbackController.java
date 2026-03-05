package org.cyntho.asgard.controller;

import lombok.RequiredArgsConstructor;
import org.cyntho.asgard.bot.BotEventStore;
import org.cyntho.asgard.bot.dto.BotEvent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bot")
@RequiredArgsConstructor
public class BotCallbackController {

	private final BotEventStore eventStore;

	// Wird vom Bot-Service aufgerufen
	@PostMapping("/events")
	public void receiveEvent(@RequestBody BotEvent event) {
		eventStore.addEvent(event);
	}
}
