package net.cloudy.sytes.hello_liberty.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("mq")
@RestController
public class RestEventController {

  MessageSender sender;

  RestEventController(@Autowired MessageSender sender) {
    this.sender = sender;
  }

  @GetMapping("send")
  String send() {

    sender.sendMessage("Hello World !");
    return "OK";
  }

}
