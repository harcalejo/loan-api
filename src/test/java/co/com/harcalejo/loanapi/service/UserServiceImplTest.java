package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.config.LoanProperties;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Target;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.entity.UserTarget;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    LoanProperties loanProperties;

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userRepository, loanProperties);
    }

    @Test
    void shouldReturnExistingUser() {
        //given
        Target target = new Target();
        target.setId(1L);
        target.setName("NEW");

        User user = new User();
        user.setId(1L);
        user.setTarget(target);

        //when
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        //then
        assertThat(userService.getUser(user.getId()))
                .isEqualTo(user);
    }

    @Test
    void shouldFailWhenUserNotExist() {
        //given
        Long userId = 20L;
        Optional<User> optionalUser = Optional.empty();

        //when
        when(userRepository.findById(userId))
                .thenReturn(optionalUser);

        //then
        assertThatExceptionOfType(UserException.class)
                .isThrownBy(() -> userService.getUser(userId))
                .withMessage("El usuario con Id " + userId + ", no existe");
    }

    @Test
    void shouldLoadUserNewTargetProperties() {
        //given
        Target newTarget = new Target();
        newTarget.setId(1L);
        newTarget.setName("NEW");

        UserTargetDTO userTargetDTO = new UserTargetDTO(UserTarget.NEW,
                0.15, 500000.0);

        //when
        when(loanProperties.getNewRate())
                .thenReturn(0.15);
        when(loanProperties.getNewMaxAmount())
                .thenReturn(500000.0);

        //then
        assertThat(userService.loadUserTargetProperties(newTarget.getId()))
                .isEqualTo(userTargetDTO);
    }
}