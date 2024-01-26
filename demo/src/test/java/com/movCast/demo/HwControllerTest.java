package com.movCast.demo;

import com.movCast.demo.dto.FilmListingDTO;
import com.movCast.demo.dto.UserDTO;
import com.movCast.demo.service.*;
import org.hibernate.annotations.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = HwController.class)
class HwControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FilmListingRepository filmListingRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private FilmRepository filmRepository;
    @Autowired
    private FilmListingService filmListingService;
    @Autowired
    private UserService userService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private ModelMapper modelMapper;

    @TestConfiguration
    static class controllerBeanTestConfig {
        @Bean
        FilmListingService filmListingServiceTest()
        {
            return new FilmListingServiceImpl();
        }

        @Bean
        FilmService filmServiceTest()
        {
            return new FilmServiceImpl();
        }

        @Bean
        UserService userServiceTest()
        {
            return new UserServiceImpl();
        }

        @Bean
        ModelMapper modelMapperTest()
        {
            return new ModelMapper();
        }

        @Bean
        WebClient webClientTest()
        {
            return WebClient.builder().build();
        }
    }

    @Test
    void loadUsers() throws Exception
    {
        final String testCase_userName = "sampleUser_1";
        UserDTO testCase_1 = new UserDTO(testCase_userName);
        User testCaseUser_1 = modelMapper.map(testCase_1, User.class);

        Mockito.when(userRepository.findByUserName(testCase_userName)).thenReturn(Optional.of(testCaseUser_1));

        assertEquals(testCase_1.getUserName(), userService.findUser(testCase_userName).getUserName());

    }


    @Test
    void getAllUsers() throws Exception
    {
        UserDTO testCase_1 = new UserDTO("sampleUser_1");
        UserDTO testCase_2 = new UserDTO("sampleUser_2");

        List<UserDTO> userDTOList = List.of(testCase_1, testCase_2);

        User testUserCase_1 = Mockito.mock(User.class);
        User testUserCase_2 = Mockito.mock(User.class);
        testUserCase_1 = modelMapper.map(testCase_1, User.class);
        testUserCase_2 = modelMapper.map(testCase_2, User.class);

        List<User> testUserList = Arrays.asList(testUserCase_1, testUserCase_2);

        Mockito.when(userRepository.findAll()).thenReturn(testUserList);

        List<UserDTO> userTestDTOList = new ArrayList<>();
        userService.getAllUsers().forEach(userTestDTOList::add);

        assertEquals(userDTOList.get(0).getUserName(), userTestDTOList.get(0).getUserName());
        assertEquals(userDTOList.get(1).getUserName(), userTestDTOList.get(1).getUserName());

    }

    @Test
    void addUser() throws Exception
    {
        final String userName = "someDudeThatsSittingInATestLmao";
        final String postRequestUserName = "kfan";

        FilmListingDTO testCase_1 = new FilmListingDTO("sampleFilm_1");
        FilmListingDTO testCase_2 = new FilmListingDTO("sampleFilm_2");
        FilmListingDTO testCase_3 = new FilmListingDTO("sampleFilm_3");
        Films testCaseFilm_1 = modelMapper.map(testCase_1, Films.class);

        UserDTO userTestDTO_1 = new UserDTO("im_a_test");
        User userTest_1 = modelMapper.map(userTestDTO_1, User.class);
        userTestDTO_1.setFavouriteFlick(testCaseFilm_1);

        User returnedUser = Mockito.mock(User.class);

        Mockito.when(userRepository.findByUserName(Mockito.any(String.class))).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(returnedUser);
        Mockito.when(filmListingRepository.findByFilmName(testCase_1.getFilmName())).thenReturn(Optional.of(testCaseFilm_1));

//        System.out.println(filmListingService.getFilm(testCaseFilm_1.getFilmName()));
        userService.addUser(userTestDTO_1);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        Mockito.verify(userRepository, Mockito.times(1)).findByUserName(argumentCaptor.capture());
        Mockito.verify(userRepository, Mockito.times(1)).save(userArgumentCaptor.capture());

        assertEquals(userTestDTO_1.getUserName(), userArgumentCaptor.getValue().getUsername());

        mockMvc.perform(MockMvcRequestBuilders.
                post("/demo/userAddition").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"userName\" : \"kfan\", \"favouriteFlick\" : {\"filmName\" : \"sampleFilm_1\"}}")).
                andExpect(status().isOk()).
                andReturn();

        Mockito.verify(userRepository, Mockito.times(2)).findByUserName(argumentCaptor.capture());
        Mockito.verify(userRepository, Mockito.times(2)).save(userArgumentCaptor.capture());

        assertEquals(postRequestUserName, userArgumentCaptor.getValue().getUsername());

//        System.out.println(userArgumentCaptor.getValue().getUsername());

    }

    @Test
    void getFilmListing()
    {
        final String filmName = "sampleFilm_1";
        FilmListingDTO testCase_1 = new FilmListingDTO(filmName);
        Mockito.when(filmListingService.getFilm(testCase_1.getFilmName())).thenReturn(Optional.of(testCase_1));

        FilmListingDTO checkFilmListing = filmListingService.getFilm(filmName).get();

        assertEquals(testCase_1, checkFilmListing);
    }

    @Test
    void getAllFilmListings() throws Exception
    {
        FilmListingDTO testCase_1 = new FilmListingDTO("sampleFilm_1");
        FilmListingDTO testCase_2 = new FilmListingDTO("sampleFilm_2");
        FilmListingDTO testCase_3 = new FilmListingDTO("sampleFilm_3");

        Films testFilm_1 = Mockito.mock(Films.class);
        Films testFilm_2 = Mockito.mock(Films.class);
        Films testFilm_3 = Mockito.mock(Films.class);
        testFilm_1 = modelMapper.map(testCase_1, Films.class);
        testFilm_2 = modelMapper.map(testCase_2, Films.class);
        testFilm_3 = modelMapper.map(testCase_3, Films.class);

        Iterable<FilmListingDTO> filmListingDTOList = List.of(testCase_1, testCase_2, testCase_3);
        List<Films> filmListingList = Arrays.asList(testFilm_1, testFilm_2, testFilm_3);
//        for (FilmListingDTO films: filmListingDTOList){filmListingList.add(modelMapper.map(films, Films.class));}

//        Mockito.when(filmListingService.getAllFilms()).thenReturn(filmListingList);
        Mockito.when(filmListingRepository.findAll()).thenReturn(filmListingList);

        List<FilmListingDTO> filmListingDTOListTest = new ArrayList<>();
        filmListingService.getAllFilms().forEach(filmListingDTOListTest::add);

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/demo/viewFilmsFromAPI").
                        contentType(MediaType.APPLICATION_JSON)).
                        andExpect(status().isOk());


        assertEquals(filmListingList.get(0).getFilmName(), filmListingDTOListTest.get(0).getFilmName());
        assertEquals(filmListingList.get(1).getFilmName(), filmListingDTOListTest.get(1).getFilmName());
        assertEquals(filmListingList.get(2).getFilmName(), filmListingDTOListTest.get(2).getFilmName());

    }

    @Test
    void addFilm()
    {
        final String testFilmTitle_1 = "arandomfilm";
        final String testFilmTitle_2 = "doesthisfilmexist";
        FilmListingDTO testDTO_1 = new FilmListingDTO(testFilmTitle_1);
        FilmListingDTO testDTO_2 = new FilmListingDTO(testFilmTitle_2);

        Films testFilm_1 = Mockito.mock(Films.class);

        Mockito.when(filmListingRepository.findByFilmName(Mockito.any(String.class))).thenReturn(Optional.empty());
        Mockito.when(filmListingRepository.save(Mockito.any(Films.class))).thenReturn(testFilm_1);

        filmListingService.saveNewFilm(testDTO_1);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Films> filmsArgumentCaptor = ArgumentCaptor.forClass(Films.class);

        Mockito.verify(filmListingRepository, Mockito.times(1)).save(filmsArgumentCaptor.capture());
        Mockito.verify(filmListingRepository, Mockito.times(1)).findByFilmName(argumentCaptor.capture());

        assertEquals(testFilmTitle_1, filmsArgumentCaptor.getValue().getFilmName());

    }
}