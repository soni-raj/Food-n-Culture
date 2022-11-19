package com.dalhousie.server.controller;

import com.dalhousie.server.AbstractTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class FeedbackControllerTest extends AbstractTest {
    
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }
    
}
