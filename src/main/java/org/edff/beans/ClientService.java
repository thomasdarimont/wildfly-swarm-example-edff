package org.edff.beans;

import org.edff.model.Client;
import org.edff.model.ClientEvent;
import org.edff.model.ClientEvent.ClientCreated;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional
public class ClientService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private Event<ClientEvent> clientEvent;

    public List<Client> getClients() {
        return entityManager.createQuery("from Client").getResultList();
    }

    public Client createClient(Client client) {

        entityManager.persist(client);

        clientEvent.fire(new ClientCreated(client));

        return client;
    }

    public Client findOneById(Long id) {
        return entityManager.find(Client.class, id);
    }
}