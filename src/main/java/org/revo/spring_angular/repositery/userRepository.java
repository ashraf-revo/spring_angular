package org.revo.spring_angular.repositery;

import org.revo.spring_angular.domain.user;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by ashraf on 6/7/15.
 */
public interface userRepository extends CrudRepository<user, Long> {
    public Optional<user> findByEmail(String email);

}
