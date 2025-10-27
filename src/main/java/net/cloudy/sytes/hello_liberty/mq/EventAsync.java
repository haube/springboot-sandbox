package net.cloudy.sytes.hello_liberty.mq;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.ibm.msg.client.jakarta.jms.JmsMessage;

import jakarta.jms.JMSException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("mq")
@Component
@EnableJms
public class EventAsync {
  @Autowired
  private MessageSender sender;

  /**
   * Holt asynchron über Nachrtichten informiert.
   * Mit einem Münzwurf wird entschieden ob die Nachricht mit Achnowledge
   * abgegolten wird, oder im Falle einer Exception wieder hinten an der Queue
   * angestellt wird.
   * 
   * @param message
   */
  @JmsListener(destination = "DEV.QUEUE.1", id = "sampleConsumer", concurrency = "1")
  public void onMessage(JmsMessage message) {
    try {
      boolean fail = ThreadLocalRandom.current().nextBoolean();
      if (fail) {
        throw new RuntimeException("Error happend");
      }
      log.info("Received message erfolgreich verarbeitet: {}, \n", message);
    } catch (RuntimeException e2) {
      try {
        log.info("Fehler bei der Verarbeitung. Messeage wird erneut in die Queue gepackt.");
        sender.sendMessage(message);
      } catch (JMSException e1) {
        log.error("Error Message could not be put back in the queue.");
      }
    }
  }

}