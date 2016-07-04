package org.edff.messaging;

import org.edff.model.ClientEvent;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * @author Thomas Darimont
 */

@Stateless
public class EventForwarder {

    @Inject
    JMSContext context;

    @Resource(lookup = MessagingResources.MESSAGING_EDFF_EVENTS_QUEUE)
    Queue eventsQueue;

    void sendMessage(String message) {
        context.createProducer().send(eventsQueue, message);
    }


    public void onClientCreated(@Observes ClientEvent clientEvent) {
        sendMessage(clientEvent.toString());
    }
}
