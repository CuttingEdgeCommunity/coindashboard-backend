package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.ServerAddress;
import com.mongodb.connection.ClusterId;
import com.mongodb.connection.ConnectionId;
import com.mongodb.connection.ServerId;
import com.mongodb.event.ConnectionCheckOutFailedEvent;
import com.mongodb.event.ConnectionCheckOutStartedEvent;
import com.mongodb.event.ConnectionCheckedOutEvent;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConnectionPoolLogger.class)
class ConnectionPoolLoggerTest {
  @InjectMocks ConnectionPoolLogger connectionPoolLogger;
  @Mock Logger logger;

  @Test
  void connectionCheckOutStarted() {
    connectionPoolLogger.connectionCheckOutStarted(
        new ConnectionCheckOutStartedEvent(
            new ServerId(new ClusterId("desc"), new ServerAddress("testhost", 1))));
    //    BDDMockito.verify(logger).debug(Mockito.contains("testhost"));
  }

  @Test
  void connectionCheckedOut() {
    connectionPoolLogger.connectionCheckedOut(
        new ConnectionCheckedOutEvent(
            new ConnectionId(new ServerId(new ClusterId("desc"), new ServerAddress()))));
    //    BDDMockito.verify(logger).debug(Mockito.contains("desc"));
  }

  @Test
  void connectionCheckOutFailed() {
    connectionPoolLogger.connectionCheckOutFailed(
        new ConnectionCheckOutFailedEvent(
            new ServerId(new ClusterId("desc"), new ServerAddress("testhost", 1)),
            ConnectionCheckOutFailedEvent.Reason.CONNECTION_ERROR));
    //    BDDMockito.verify(logger).error(Mockito.contains("testhost"));
    //    BDDMockito.verify(logger).error(Mockito.contains("ConnectionError"));
  }
}
