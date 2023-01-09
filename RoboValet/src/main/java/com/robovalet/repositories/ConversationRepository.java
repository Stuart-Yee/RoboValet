package com.robovalet.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.robovalet.models.Conversation;
import com.robovalet.models.Conversation.Stage;
import com.robovalet.models.Stay;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

	Optional<Conversation> findBySMSAndClosedAtIsNull(String sms);
	ArrayList<Conversation> findByStayAndStageNot(Stay stay, Stage notStage);
}
