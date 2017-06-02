package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.repository.CreditorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by Dule on 31-May-17.
 */
@RunWith(SpringRunner.class)
public class CreditorServiceTest {

    @TestConfiguration
    static class CreditorServiceTestContextConfiguration {
        @Bean
        public CreditorService creditorService() {
            return new CreditorServiceImpl();
        }
    }

    @Autowired
    private CreditorService creditorService;

    @MockBean
    private CreditorRepository creditorRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void findCreditorByIdShouldReturnCreditor() {

        long id = 1L;
        Creditor shouldReturn = mock(Creditor.class);
        Mockito.when(creditorRepository.findCreditorById(id)).thenReturn(shouldReturn);

        Creditor found = creditorService.findCreditorById(id);

        assertThat(found).isEqualTo(shouldReturn);

    }

    @Test
    public void findCreditorByIdWhenNotFoundShouldReturnNull() {

        long id = 1L;
        Mockito.when(creditorRepository.findCreditorById(id)).thenReturn(null);

        Creditor found = creditorService.findCreditorById(id);

        assertThat(found).isEqualTo(null);

    }
}
