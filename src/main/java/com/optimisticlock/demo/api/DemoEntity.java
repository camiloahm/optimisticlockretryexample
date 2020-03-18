package com.optimisticlock.demo.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@NoArgsConstructor
@Entity(name = "DEMO")
@Getter
@Setter
final class DemoEntity {

    @Id
    private String id;

    private Integer counter;

    private String sampleText;

    @Version
    private Integer version;

}
