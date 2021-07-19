package ru.example.sweater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.sweater.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}
