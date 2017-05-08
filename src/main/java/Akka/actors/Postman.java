package Akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.BoundedMessageQueueSemantics;
import akka.dispatch.RequiresMessageQueue;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * This actor should think longer to forward somebody his message
 */
public class Postman extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Letter.class, this::processMessage)
                .build();
    }

    private void processMessage(Letter s) {
        log.info("Received message: {} with priority {}", s.getBody(), s.getPriority());
    }

}
