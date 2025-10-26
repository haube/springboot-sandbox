package net.cloudy.sytes.hello_liberty;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.jms.JmsMessage;
import com.ibm.msg.client.jakarta.wmq.common.CommonConstants;
import com.ibm.msg.client.wmq.WMQConstants;

import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueSender;
import jakarta.jms.QueueSession;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Component
public class MessageSender {
  private QueueConnection connection;
  private QueueSession session;
  private QueueSender sender;

  public MessageSender() throws JMSException {
    /*
     * MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
     * factory.setHostName("localhost");
     * factory.setPort(1414);
     * factory.setQueueManager("QM1");
     * factory.setChannel("DEV.ADMIN.SVRCONN");
     * factory.setTransportType(0);
     * factory.setIntProperty(CommonConstants.WMQ_CONNECTION_MODE,
     * CommonConstants.WMQ_CM_CLIENT);
     * connection = factory.createQueueConnection();
     * session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
     * Queue queue = session.createQueue("DEV.QUEUE.1");
     * sender = session.createSender(queue);
     * connection.start();
     */
  }

  public void sendMessage(String messageText) {
    try {
      TextMessage message = session.createTextMessage();
      message.setText(messageText);
      message.setJMSCorrelationID(UUID.randomUUID().toString());
      sender.send(message);
    } catch (JMSException e) {
      // handle exception
    } finally {
      // close resources
    }
  }

  public void sendMessage(JmsMessage message) throws JMSException {
    sender.send(message);
  }
}