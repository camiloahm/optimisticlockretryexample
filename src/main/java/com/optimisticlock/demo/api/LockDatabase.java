package com.optimisticlock.demo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
class LockDatabase {

    AtomicInteger counter = new AtomicInteger(0);
    private final DemoRepository demoRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    DemoEntity addOneToCounter(String id) throws Exception {
        int count = counter.incrementAndGet();
        log.info("count {}", count);
        DemoEntity demoEntity = demoRepository.findById(id).get();
        demoEntity.setCounter(demoEntity.getCounter() + 1);
        demoRepository.save(demoEntity);
        if (count % 10 == 0) {
            throw new Exception("error in count " + count);
        }
        return demoEntity;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    DemoEntity addOneToCounterRequireNew(String id) throws Exception {
        int count = counter.incrementAndGet();
        log.info("countAgain {}", count);
        DemoEntity demoEntity = demoRepository.findById(id).get();
        demoEntity.setCounter(demoEntity.getCounter() + 1);
        demoRepository.save(demoEntity);
        if (count % 10 == 0) {
            throw new RuntimeException("error in countAgain " + count);
        }
        return demoEntity;
    }

}
