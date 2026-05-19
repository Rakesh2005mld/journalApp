package com.myCompany.journalApp.service;

import com.myCompany.journalApp.entity.User;
import com.myCompany.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest//else autowired won't work
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    public void testFindByUserName(){
        User user=userRepository.findByUserName("Rakesh");
        assertNotNull(user);
        assertFalse(user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "Rakesh",
            "Arshiya"
    })
    public void testName(String name){
        assertNotNull(userRepository.findByUserName(name),"failed for "+name);
    }

//    @BeforeEach → runs before every test method.
//    @BeforeAll → runs once before all test methods in the class.
//    @AfterEach → runs after every test method.
//    @AfterAll → runs once after all test methods in the class.

    //in built arguments testing
//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentsProvider.class)
//    public void testSaveNewUser(User user){
//        assertTrue(userService.saveNewUser(user));
//    }


    @ParameterizedTest
    @CsvSource({
            //a,b,expected
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
