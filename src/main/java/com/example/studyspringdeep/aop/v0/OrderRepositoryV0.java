package com.example.studyspringdeep.aop.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId)  {
        // 저장 로직
        if ( itemId.equals("ex") ) {
            try {
                throw new IllegalAccessException("예외 발생");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
