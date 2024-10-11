package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.messages.MessageStoredBo;
import ar.lamansys.messages.infrastructure.output.repository.messages.MessageRepository;
import ar.lamansys.messages.infrastructure.output.MessageStorage;
import ar.lamansys.messages.infrastructure.output.entity.messages.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Primary
@RequiredArgsConstructor
@Service
public class MessageStorageImpl implements MessageStorage {

    private final MessageRepository messageRepository;

    @Override
    public void save(MessageStoredBo message) {
        messageRepository.save(new Message(message));
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<MessageStoredBo> findBetween(String oneContact, String otherContact) {
        return messageRepository.findAllBetweenTwoContacts(oneContact, otherContact);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<MessageStoredBo> findByContact(String userId) {
        return messageRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteAll() {
        messageRepository.deleteAll();
    }

}
