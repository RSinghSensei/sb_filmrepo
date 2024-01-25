package com.movCast.demo;

import com.movCast.demo.dto.FilmListingDTO;
import com.movCast.demo.dto.UserDTO;
import com.movCast.demo.service.*;
import org.hibernate.annotations.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
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

    void loadUsers()
    {

    }

    // In an effort to not make the tc's trivial
    // Expect service layer to work
    // base tc on that

    @Test
    void getAllUsers() throws Exception {
//        Mockito.when(filmListingService.getAllFilms()).thenReturn(List.of(new FilmListingDTO("sampleuser1")));

//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.
//                get("/userDisplay").
//                accept(MediaType.APPLICATION_JSON));
//        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    void addUser() throws Exception
    {
        final String userName = "someDudeThatsSittingInATestLmao";

        FilmListingDTO testCase_1 = new FilmListingDTO("sampleFilm_1");
        FilmListingDTO testCase_2 = new FilmListingDTO("sampleFilm_2");
        FilmListingDTO testCase_3 = new FilmListingDTO("sampleFilm_3");
        Films testCaseFilm_1 = modelMapper.map(testCase_1, Films.class);

        UserDTO userTestDTO_1 = new UserDTO("im_a_test");
        User userTest_1 = modelMapper.map(userTestDTO_1, User.class);
        User userTest_2 = new User();
        userTestDTO_1.setFavouriteFlick(testCaseFilm_1);

        Mockito.when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userTest_1));
        Mockito.when(filmListingRepository.findByFilmName(testCase_1.getFilmName())).thenReturn(Optional.of(testCaseFilm_1));

        System.out.println(filmListingService.getFilm(testCaseFilm_1.getFilmName()));
        userService.addUser(userTestDTO_1);

        mockMvc.perform(MockMvcRequestBuilders.
                post("/demo/userAddition").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"userName\" : \"kfan\", \"favouriteFlick\" : {\"filmName\" : \"sampleFilm_1\"}}")).
                andExpect(status().isOk()).
                andReturn();

//        ArgumentCaptor<UserDTO> argumentCaptor = ArgumentCaptor.forClass(UserDTO.class);
//        Mockito.verify(userRepository).findByUserName(argumentCaptor.capture().getUserName());

//        System.out.println(userService.findUser("kfan").getUserName());
//        Mockito.verify(userRepository, Mockito.times(2)).save(Mockito.any(User.class));

//        assertEquals("kfan", userService.findUser("kfan").getUserName());
//        Mockito.verify(filmListingRepository, Mockito.times(1)).findByFilmName(Mockito.any(String.class));
//        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));



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

        Iterable<FilmListingDTO> filmListingDTOList = List.of(testCase_1, testCase_2, testCase_3);
        List<Films> filmListingList = new ArrayList<>();
        for (FilmListingDTO films: filmListingDTOList){filmListingList.add(modelMapper.map(films, Films.class));}

//        Mockito.when(filmListingService.getAllFilms()).thenReturn(filmListingList);
        Mockito.when(filmListingRepository.findAll()).thenReturn(filmListingList);

        System.out.println(filmListingList);

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/demo/viewFilmsFromAPI").
                        contentType(MediaType.APPLICATION_JSON)).
                        andExpect(status().isOk());
    }

    @Test
    void addFilm() {
    }
}