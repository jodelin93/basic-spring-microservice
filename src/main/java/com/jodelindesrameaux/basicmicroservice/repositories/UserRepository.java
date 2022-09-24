package com.jodelindesrameaux.basicmicroservice.repositories;

import com.jodelindesrameaux.basicmicroservice.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
   public UserEntity findByEmail(String email);
}
