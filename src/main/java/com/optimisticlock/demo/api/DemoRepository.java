package com.optimisticlock.demo.api;

import org.springframework.data.repository.CrudRepository;

public interface DemoRepository extends CrudRepository<DemoEntity, String> {
}
