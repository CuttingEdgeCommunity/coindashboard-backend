package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import com.mongodb.event.ConnectionCheckOutFailedEvent;
import com.mongodb.event.ConnectionCheckedOutEvent;
import com.mongodb.event.ConnectionPoolListener;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConnectionPoolLogger implements ConnectionPoolListener {
  @Override
  public void connectionCheckedOut(final ConnectionCheckedOutEvent event) {
    log.info(
        String.format("Using connection with id: %s...", event.getConnectionId().getLocalValue()));
  }

  @Override
  public void connectionCheckOutFailed(final ConnectionCheckOutFailedEvent event) {
    log.error(
        String.format(
            "Connection failed: server id: %s, reason: %s",
            event.getServerId(), event.getReason()));
  }
}
