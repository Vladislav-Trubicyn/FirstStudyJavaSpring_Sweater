package ru.example.sweater.repository;

import org.springframework.data.repository.CrudRepository;
import ru.example.sweater.model.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer>
{
    List<Message> findByTag(String tag);
}
