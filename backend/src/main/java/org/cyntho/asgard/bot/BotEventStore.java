package org.cyntho.asgard.bot;

import lombok.extern.slf4j.Slf4j;
import org.cyntho.asgard.bot.dto.BotEvent;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
public class BotEventStore {

	private final Queue<BotEvent> events = new ConcurrentLinkedQueue<>();

	public void addEvent(BotEvent event) {
		events.add(event);
		log.debug("Added event: {}", event);
	}

	public Queue<BotEvent> drainEvents() {
		Queue<BotEvent> drained = new ConcurrentLinkedQueue<>();
		BotEvent e;
		while ((e = events.poll()) != null) {
			drained.add(e);
		}
		return drained;
	}
}