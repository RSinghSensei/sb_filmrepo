package com.movCast.demo;

import com.movCast.demo.service.FilmListingService;
import com.movCast.demo.service.FilmService;
import com.movCast.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/demo")
public class HwController {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmListingRepository filmListingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmListingService filmListingService;
    @Autowired
    private UserService userService;

//    @Autowired
//    private FilmService filmService;

//    @PostMapping(path = "/add")
//    public @ResponseBody Filmography addNewFilm(@RequestParam String filmName, @RequestParam String filmDirector)
//    {
//        Filmography film = new Filmography();
//        film.setFlickName(filmName);
//        film.setFlickDirector(filmDirector);
//        filmRepository.save(film);
//        return film;
//    }
    @PostMapping(path = "/add")
    public String addNewFilm(@ModelAttribute("newFilm") Filmography newFilm)
    {
        filmService.addNewFilm(newFilm);
        System.out.println(newFilm.getId() + " " + newFilm.getFlickName() + " " + newFilm.getFlickDirector() + " " + newFilm.getFlickRating() + " " + newFilm.getTotalFlickRating());
        return "redirect:/demo/displaytable";
    }

    @DeleteMapping(path = "/remove/{filmId}")
    public @ResponseBody String removeFilm(@PathVariable Integer filmId)
    {
        if (!filmRepository.existsById(filmId)){return "Invalid ID";}
        filmRepository.deleteById(filmId);
        return "Deleted";
    }
    @GetMapping(path = "/display")
    public @ResponseBody Iterable<Filmography> getAllFilms ()
    {
        return filmService.getAllFilms();
    }

    @GetMapping(path = "/userDisplay")
    public void getAllUsers()
    {
        for (Films films: filmListingRepository.findAll())
        {
            for (User user : filmListingRepository.findById(films.getId()).get().getUserList()) {
                System.out.println("Film " + filmListingRepository.findById(films.getId()).get().getFilmName() + " " +
                        "User Favourite Size: " + filmListingRepository.findById(films.getId()).get().getUserList().size() +  " " + user.getId());
            }
        }
//        return filmListingRepository.findById(12).get().getUserList();
    }

    @PostMapping(path = "/userAddition")
    public void addUser(@RequestBody User newUser)
    {
//        User anotherUser = new User();
//        anotherUser.setUsername(newUser.getUsername());
//        if (newUser.getFavouriteFlick() != null){System.out.println("Current Film: " + newUser.getFavouriteFlick());}
//        anotherUser.setFavouriteFlick(filmListingRepository.findById(filmListingRepository.findByName(newUser.getFavouriteFlick().getFilmName())));

        userService.addUser(newUser);
//        if (filmListingRepository.findByFilmName(newUser.getFavouriteFlick().getFilmName()).isPresent())
//        {
//            System.out.println("New Username created: " + newUser.getUsername() + " fav flick: " +
//                    newUser.getFavouriteFlick().getFilmName() + " film ID: "  + filmListingRepository.findByFilmName(newUser.getFavouriteFlick().getFilmName()).toString());
//        }

//        else
//        {
//            System.out.println("Incorrect Favourite Film, check list");
//        }
    }

    @PutMapping(path = "/userUpdate")
    public void updateUser(@RequestBody User currentUser,
                           @RequestParam(value = "isNameChanging", defaultValue = "false") boolean nameChange,
                           @RequestParam(required = false, value = "newUserName") String newUserName,
                           @RequestParam(required = false, value = "newFavFlick") String newFavouriteFlick)
    {
        userService.updateUser(currentUser, nameChange, newUserName, newFavouriteFlick);
//        System.out.println("current favflick: " + currentUser.getFavouriteFlick().getFilmName());
//        if (nameChange)
//        {
//            currentUser.setUsername(newUserName);
//            System.out.println("New username : " + currentUser.getUsername());
//        }
//        else
//        {
//            if (filmListingRepository.findByFilmName(newFavouriteFlick).isPresent())
//            {
//                System.out.println("FavFlick being upd from: " + currentUser.getFavouriteFlick().getFilmName());
//                currentUser.setFavouriteFlick(filmListingRepository.findByFilmName(newFavouriteFlick).get());
//                System.out.println("FavouriteFlick upd. to : " + currentUser.getFavouriteFlick().getFilmName());
//            }
//            else
//            {
//                System.out.println("FavouriteFlick doesn't exist");
//            }
//        }
    }

    @GetMapping(path = "/displaytable")
    public String getFilmTable(@RequestParam(required = false) Integer editFilmId, Model model)
    {
        if (editFilmId != null)
        {
//            Filmography filmToEdit = new Filmography();
            Filmography filmToEdit = filmService.getFilmById(editFilmId);
//            System.out.println(filmToEdit.getId());
            model.addAttribute("editedFilm", filmToEdit);
//            return "filmDisplay";
        }
        Iterable<Filmography> currentList = filmService.getAllFilms();
        List<Filmography> filmList = new ArrayList<Filmography>();
        currentList.forEach(filmList::add);
        model.addAttribute("filmDetailList", filmList);
        model.addAttribute("newFilm", new Filmography());
        return "filmDisplay";
    }

    @PutMapping(path = "/update/{filmId}")
    public @ResponseBody String updateFilmDetes(@PathVariable Integer filmId,
                                                @RequestParam String newFilm,
                                                @RequestParam String newDirector,
                                                @RequestParam(required = false, defaultValue = "10") Integer newRating)
    {
        filmService.updateFilmById(filmId, newFilm, newDirector, newRating);
        return "updated";
    }

    @PostMapping(path = "/updateFilm")
    public String addToExistingFilm(@RequestParam("selectedFilmId") Integer filmId,
                                    @ModelAttribute("editedFilm") Filmography film)
    {
        filmService.updateFilmById(filmId, film.getFlickName(), film.getFlickDirector(), film.getFlickRating());
//        System.out.println(currentFilm.getId() + " " + currentFilm.getFlickName() + " " + currentFilm.getFlickDirector() + " " + currentFilm.getFlickRating() + " " + currentFilm.getTotalFlickRating());
//        if (film == null){return "Unable to find film, incorrect ID";}
        return "redirect:/demo/displaytable";
    }

}
