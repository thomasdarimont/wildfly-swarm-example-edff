package org.edff.messaging;

/**
 * Created by tom on 04.07.16.
 */
// Sadly this is not recognized by wildfly swarm...
//@JMSDestinationDefinition(
//        name = MessagingResources.MESSAGING_EDFF_EVENTS_QUEUE,
//        interfaceName = "javax.jms.Queue",
//        properties = {
//                "PhysicalName=edff/EventsQueue"
//        })
public class MessagingResources {

    public static final String EDFF_EVENTS_QUEUE_NAME = "edff/EventsQueue";
    public static final String MESSAGING_EDFF_EVENTS_QUEUE = "jms/queue/" + EDFF_EVENTS_QUEUE_NAME;
}
