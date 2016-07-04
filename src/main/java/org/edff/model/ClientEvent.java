package org.edff.model;

/**
 * Created by tom on 04.07.16.
 */
public interface ClientEvent {

    class ClientCreated implements ClientEvent {

        private final Client client;

        public ClientCreated(Client client) {
            this.client = client;
        }

        @Override
        public String toString() {
            return "ClientCreated{" +
                    "client=" + client +
                    '}';
        }
    }

    class ClientUpdated implements ClientEvent {

        private final Client client;

        public ClientUpdated(Client client) {
            this.client = client;
        }

        @Override
        public String toString() {
            return "ClientUpdated{" +
                    "client=" + client +
                    '}';
        }
    }
}
