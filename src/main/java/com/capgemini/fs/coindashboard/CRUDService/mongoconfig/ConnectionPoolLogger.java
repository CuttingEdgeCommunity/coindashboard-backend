package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import com.mongodb.event.ConnectionCheckOutFailedEvent;
import com.mongodb.event.ConnectionCheckOutStartedEvent;
import com.mongodb.event.ConnectionCheckedOutEvent;
import com.mongodb.event.ConnectionPoolListener;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConnectionPoolLogger implements ConnectionPoolListener {

  @Override
  public void connectionCheckOutStarted(ConnectionCheckOutStartedEvent event) {
    log.debug(
        String.format(
            "Checking out connection to server: %s...", event.getServerId().getAddress()));
  }

  @Override
  public void connectionCheckedOut(final ConnectionCheckedOutEvent event) {
    log.debug(
        String.format(
            "Using connection with id: %s... on server: %s",
            event.getConnectionId().getLocalValue(),
            event.getConnectionId().getServerId().getAddress()));
  }

  @Override
  public void connectionCheckOutFailed(final ConnectionCheckOutFailedEvent event) {
    log.error(
        String.format(
            "Connection failed: server id: %s, reason: %s",
            event.getServerId().getAddress(), event.getReason()));
  }
}
