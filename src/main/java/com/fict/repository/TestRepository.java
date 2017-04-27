package com.fict.repository;

import com.fict.entities.TestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by stevo on 4/27/17.
 */
@Repository
public interface TestRepository extends CrudRepository<TestEntity, Long> {

    TestEntity findTestEntityById(Long id);

}
