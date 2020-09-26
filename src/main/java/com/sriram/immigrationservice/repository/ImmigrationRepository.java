package com.sriram.immigrationservice.repository;

import com.sriram.immigrationservice.model.Immigration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmigrationRepository extends ReactiveMongoRepository<Immigration, String> {
}
