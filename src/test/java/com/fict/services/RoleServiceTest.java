package com.fict.services;

import com.fict.entities.Role;
import com.fict.repository.RoleRepository;
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

/**
 * Created by Dule on 01-Jun-17.
 */
@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @TestConfiguration
    static class RoleServiceTesConfigurationContext {
        @Bean
        public RoleService roleService() {
            return new RoleServiceImpl();
        }
    }

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void findRoleByIdShouldReturnRole() {

        long id = 1L;
        Role shouldReturn = getDummyRole();
        Mockito.when(roleRepository.findRoleById(id)).thenReturn(shouldReturn);

        Role found = roleService.findRoleById(id);

        assertThat(found).isEqualTo(shouldReturn);
    }

    @Test
    public void findRoleByIdWhenNotFoundShouldReturnNull() {

        long id = 1L;
        Mockito.when(roleRepository.findRoleById(id)).thenReturn(null);

        Role found = roleService.findRoleById(id);

        assertThat(found).isEqualTo(null);
    }

    public Role getDummyRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        return role;
    }
}
