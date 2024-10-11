package ar.lamansys.messages.infrastructure.input.rest.messages;

import ar.lamansys.messages.application.messages.ListContacts;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ListContacts listContacts;

    @GetMapping("/list")
    public List<String> listContacts() throws UserSessionNotExists {
        return listContacts.run();
    }
}
