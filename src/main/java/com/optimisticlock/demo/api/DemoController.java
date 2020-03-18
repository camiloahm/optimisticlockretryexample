package com.optimisticlock.demo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
class DemoController {

    private final LockDatabase lockRepository;

    @GetMapping(
            value = "/count/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DemoEntity> count(@PathVariable("id") String demoId) throws Exception {
        return ResponseEntity.ok(countRetry(demoId, 5));
    }

    private DemoEntity countRetry(final String id, int counter) throws Exception {
        try {
            return lockRepository.addOneToCounter(id);
        } catch (Exception e) {
            log.error("error count", e);
            if (counter > 0) {
                counter--;
                return countRetry(id, counter);
            } else {
                throw e;
            }
        }
    }

    @GetMapping(
            value = "/countAgain/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DemoEntity> countAgain(@PathVariable("id") String demoId) throws Exception {
        return ResponseEntity.ok(countRetryNewTransaction(demoId, 5));
    }

    @GetMapping(
            value = "/rollback/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DemoEntity> rollback(@PathVariable("id") String demoId) throws Exception {
        return ResponseEntity.ok(lockRepository.rollback("1"));
    }

    @GetMapping(
            value = "/reset",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity countAgain() {
        lockRepository.resetCounter();
        return ResponseEntity.ok("");
    }

    private DemoEntity countRetryNewTransaction(final String id, int counter) throws Exception {
        try {
            return lockRepository.addOneToCounterRequireNew(id);
        } catch (Exception e) {
            log.error("error count again", e);
            if (counter > 0) {
                counter--;
                return countRetryNewTransaction(id, counter);
            } else {
                throw e;
            }
        }
    }

}

