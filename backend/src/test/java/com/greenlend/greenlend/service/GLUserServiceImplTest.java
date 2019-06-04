package com.greenlend.greenlend.service;

import com.greenlend.greenlend.GreenlendApplicationTests;
import com.greenlend.greenlend.exception.NotFoundException;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.repository.GLUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;


public class GLUserServiceImplTest extends GreenlendApplicationTests {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private GLUserRepository userRepository;

    @InjectMocks
    private GLUserServiceImpl userService;


    private long loggedInUserId = 4L;
    private String loggedInUserUsername = "Karel";
    private String hashEnd = "nejlepsihash123";
    private String basicUsername = "Honzik";
    private long basicId = 0L;
    private long invalidId = -1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(userRepository.save(any(GLUser.class))).then(returnsFirstArg());
        when(bCryptPasswordEncoder.encode(any(String.class))).thenAnswer(invocation -> invocation.getArgument(0) + hashEnd);

        GLUser basicUser = new GLUser();
        basicUser.setUserId(basicId);
        basicUser.setUsername(basicUsername);
        basicUser.setFirstname("Jean");
        basicUser.setLastname("Krauss");
        basicUser.setPassword("honzik14" + hashEnd);

        when(userRepository.findById(basicId)).thenReturn(java.util.Optional.of(basicUser));
        when(userRepository.findByUsername(basicUsername)).thenReturn(basicUser);

        GLUser loggedInUser = new GLUser();
        loggedInUser.setUserId(loggedInUserId);
        loggedInUser.setUsername(loggedInUserUsername);
        when(userRepository.findById(loggedInUserId)).thenReturn(java.util.Optional.of(loggedInUser));
        when(userRepository.findByUsername(loggedInUserUsername)).thenReturn(loggedInUser);

        // security
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(loggedInUser);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(loggedInUserUsername);

    }

    @Test
    public void findUserById_validId_userFound() {
        GLUser expectedUser = userRepository.findById(basicId).get(); // first call for verify

        GLUser actualUser = userService.findUserById(basicId); // second call for verify

        verify(userRepository, times(1 + 1)).findById(basicId);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findUserById_invalidId_notFoundExceptionThrown() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.findUserById(invalidId);
        });
    }

    @Test
    public void findAllUsers() {
        userService.findAllUsers();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void createUser_validUser_userCreated() {
        String fName = "Karel";
        String lName = "Barel";
        String username = "kbarel";
        String password = "heslo123";

        GLUser user = new GLUser();
        user.setFirstname(fName);
        user.setLastname(lName);
        user.setUsername(username);
        user.setPassword(password);
        GLUser savedUser = userService.createUser(user);

        verify(userRepository, times(1)).save(savedUser);
        assertEquals(username, savedUser.getUsername());
        assertEquals(password + hashEnd, savedUser.getPassword());
    }

    @Test
    public void replaceUser_oldUserFound_oldUserSavedWithNewData() {
        GLUser oldUser = userRepository.findById(basicId).get();
        assertEquals("Honzik", oldUser.getUsername());

        long userId = 2L;
        String name = "Frantik";
        String password = "mujPejsek99";

        GLUser newUser = new GLUser();
        newUser.setUserId(userId);
        newUser.setUsername(name);
        newUser.setPassword(password);
        GLUser replacedUser = userService.replaceUser(newUser, basicId);

        verify(userRepository, times(1)).save(oldUser);
        verify(userRepository, times(0)).save(newUser);
        assertEquals(name, replacedUser.getUsername());
        assertEquals(password, replacedUser.getPassword());
    }

    @Test
    public void replaceUser_oldUserNotFound_newUserSaved() {
        long userId = 2L;
        String name = "Frantik";
        String password = "mujPejsek99";

        GLUser newUser = new GLUser();
        newUser.setUserId(userId);
        newUser.setUsername(name);
        newUser.setPassword(password);
        newUser.setPhone("123456789");
        GLUser replacedUser = userService.replaceUser(newUser, invalidId);

        verify(userRepository, times(1)).save(newUser);
        assertEquals(name, replacedUser.getUsername());
        assertEquals(password, replacedUser.getPassword());
    }

    @Test
    public void deleteUserById_deleteCalled() {
        userService.deleteUser(basicId);

        verify(userRepository, times(1)).deleteById(basicId);
    }

    @Test
    public void findByUsername_existingUsername_userReturned() {
        GLUser expectedUser = userRepository.findById(basicId).get();

        GLUser actualUser = userRepository.findByUsername(basicUsername);

        verify(userRepository, times(1)).findByUsername(basicUsername);
        assertEquals(expectedUser, actualUser);
    }
}
