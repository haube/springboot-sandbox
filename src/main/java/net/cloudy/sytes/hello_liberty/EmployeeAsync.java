package net.cloudy.sytes.hello_liberty;

import java.util.concurrent.ThreadLocalRandom;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.ibm.msg.client.jakarta.jms.JmsMessage;

import jakarta.jms.JMSException;

@Component
public class EmployeeAsync {
  @Autowired
  private MessageSender sender;

  @JmsListener(destination = "DEV.QUEUE.1", id = "sampleConsumer", concurrency = "1")
  public void onMessage(JmsMessage message) {
    try {
      boolean fail = ThreadLocalRandom.current().nextBoolean();
      if (fail) {
        throw new RuntimeException("Error happend");
      }
      System.out.printf("Received message: %s,  %n", message);
    } catch (RuntimeException e2) {
      try {
        sender.sendMessage(message);
      } catch (JMSException e1) {
        System.out.println("Error reseinding sending failed  message");
      }
    }
  }

}