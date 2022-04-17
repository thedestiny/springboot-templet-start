package com.destiny.origin;

import com.destiny.origin.mapper.UserMapper;
import com.destiny.origin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Random;

@Slf4j
public class MockTest {

    // spy 方法会真实调用  @InjectMocks
    @Mock
    private Random random;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    public void test001() {

        Mockito.when(random.nextInt()).thenReturn(0);
        int i = random.nextInt();
        log.info(" result {}",i);

    }


    @BeforeEach
    public void setUp() {

        log.info("before invoke ");
    }

    @AfterEach
    public void after() {

        log.info("after invoke ");
    }


}
