package com.optimisticlock.demo.api;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity(name = "DEMO")
final class DemoEntity {

    @Id
    private String id;
    private Integer counter;

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(final Integer counter) {
        this.counter = counter;
    }
}
