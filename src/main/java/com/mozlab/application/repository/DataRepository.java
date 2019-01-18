package com.mozlab.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mozlab.application.model.CaptainTeam;

@Repository
public interface DataRepository extends MongoRepository<CaptainTeam, Integer> {

}