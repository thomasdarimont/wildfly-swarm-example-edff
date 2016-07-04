package org.edff;

import org.edff.messaging.MessagingResources;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.cdi.CDIFraction;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSFraction;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.jsf.JSFFraction;
import org.wildfly.swarm.messaging.MessagingFraction;
import org.wildfly.swarm.naming.NamingFraction;
import org.wildfly.swarm.resource.adapters.RARArchive;
import org.wildfly.swarm.spi.api.Fraction;

import java.io.File;

/**
 * Created by tom on 04.07.16.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // Instantiate the container
        Swarm container = new Swarm(args);

        ClassLoader classLoader = Main.class.getClassLoader();
        File ironJacamarFile = new File(classLoader.getResource("META-INF/ironjacamar.xml").getFile());
        RARArchive rarArchive = ShrinkWrap.create(RARArchive.class, "activemq-ra.rar");
        rarArchive.addAsLibrary(Swarm.artifact("org.apache.activemq:activemq-rar:rar:5.13.3"));
        rarArchive.resourceAdapter(ironJacamarFile);

        container
                .fraction(Main::database)
                .fraction(Main::naming)
                .fraction(Main::jpa)
                .fraction(Main::cdi)
                .fraction(Main::jaxRs)
                .fraction(Main::jsf)
                .fraction(Main::jms)
                .start(rarArchive)
                .deploy();

    }

    private static Fraction naming() {
        return new NamingFraction();
    }

    private static Fraction jms() {

//        @JMSDestinationDefinition(
//                name = MessagingResources.MESSAGING_EDFF_EVENTS_QUEUE,
//                interfaceName = "javax.jms.Queue",
//                properties = {
//                        "PhysicalName=edff/EventsQueue"
//                })

        //return new MessagingFraction().server("", server -> server.jmsQueue("edff/EventsQueue", queue -> System.out.println("Created queue: " + queue)));

        return MessagingFraction.createDefaultFraction()//
                .defaultServer(server -> server //
                        .jmsQueue(MessagingResources.EDFF_EVENTS_QUEUE_NAME) //
                );
    }

    private static Fraction jaxRs() {
        return new JAXRSFraction();
    }

    private static Fraction jpa() {
        return new JPAFraction();
    }

    private static Fraction cdi() {
        return new CDIFraction();
    }

    private static Fraction jsf() {
        return new JSFFraction();
    }

    private static DatasourcesFraction database() {

        return new DatasourcesFraction()
                .jdbcDriver("h2", (d) -> {
                    d.driverClassName("org.h2.Driver");
                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                    d.driverModuleName("com.h2database.h2");
                })
                .dataSource("ExampleDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.userName("foo");
                    ds.password("bar");
                });
    }
}
