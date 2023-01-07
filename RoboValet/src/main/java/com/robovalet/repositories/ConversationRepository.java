package com.robovalet.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.robovalet.models.Conversation;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

	Optional<Conversation> findBySMS(String sms);
}
