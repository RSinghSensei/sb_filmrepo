package com.movCast.demo;

import com.movCast.demo.dto.FilmListingDTO;
import com.movCast.demo.dto.UserDTO;
import com.movCast.demo.service.FilmListingService;
import com.movCast.demo.service.FilmService;
import com.movCast.demo.service.UserService;
import org.modelmapper.ModelMapper;
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
    private FilmService filmService;
    @Autowired
    private FilmListingService filmListingService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

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
       filmService.deleteFilmById(filmId);
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
        Iterable<FilmListingDTO>films = filmListingService.getAllFilms();
        for (FilmListingDTO currentFilm: films)
        {
            System.out.println(currentFilm.getFilmName() + " favourited by " + currentFilm.getUserList().size() + " users");
            for (User currentUser: currentFilm.getUserList())
            {
                System.out.print(currentUser.getUsername() + " ");
            }
        }
    }

    @PostMapping(path = "/userAddition")
    public void addUser(@RequestBody UserDTO userDTO)
    {
        System.out.println("dto attributes " + userDTO.getUserName() + " " + userDTO.getFavouriteFlick());
        userService.addUser(userDTO);
    }

    @PutMapping(path = "/userUpdate")
    public void updateUser(@RequestBody UserDTO userDTO,
                           @RequestParam(value = "isNameChanging", defaultValue = "false") boolean nameChange,
                           @RequestParam(required = false, value = "newUserName") String newUserName,
                           @RequestParam(required = false, value = "newFavFlick") String newFavouriteFlick)
    {
        userService.updateUser(userDTO, nameChange, newUserName, newFavouriteFlick);
    }

    @GetMapping(path = "/displaytable")
    public String getFilmTable(@RequestParam(required = false) Integer editFilmId, Model model)
    {
        if (editFilmId != null)
        {
            Filmography filmToEdit = filmService.getFilmById(editFilmId);
            model.addAttribute("editedFilm", filmToEdit);
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
        return "redirect:/demo/displaytable";
    }

}
