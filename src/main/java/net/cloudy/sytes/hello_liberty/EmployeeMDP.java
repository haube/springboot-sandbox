package net.cloudy.sytes.hello_liberty;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableJms
@RestController
public class EmployeeMDP {

  MessageSender sender;

  EmployeeMDP(MessageSender sender) {
    this.sender = sender;
  }

  @GetMapping("send")
  String send() {

    sender.sendMessage("Hello World !");
    return "OK";
  }

}
