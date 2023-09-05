package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }


//Provides a list of all Cat Cards in the user's collection.

    @RequestMapping ( path = "/api/cards", method = RequestMethod.GET)
    public List<CatCard> list(){
        return catCardDao.getCatCards();
    }

  //  GET /api/cards/{id}: Provides a Cat Card with the given ID.
    @RequestMapping( path = "/api/cards/{id}", method = RequestMethod.GET)
    public CatCard getCatCard(@PathVariable int id){
        CatCard catCard = catCardDao.getCatCardById(id);
        if(catCard == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shit anit here.");
        }else {
            return catCard;
        }
    }

// Provides a new, randomly created Cat Card containing
// information from the cat fact and picture service


    @RequestMapping(path = "/api/cards/random", method = RequestMethod.GET)
    public CatCard addCatCard(){
        CatFact catFact = catFactService.getFact();
       // System.out.println(catFact.getText());
        CatPic catPic = catPicService.getPic();
       // System.out.println(catPic.getFile());
        // 1. we need a CatCard object
        // 2. A CatCard has a catFact and a url.
        CatCard randomCatCard = new CatCard();
        randomCatCard.setCatFact(catFact.getText());
        randomCatCard.setImgUrl(catPic.getFile());

        return randomCatCard;
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/api/cards/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        catCardDao.deleteCatCardById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/cards", method = RequestMethod.POST)
    public CatCard addCatcard(@Valid @RequestBody CatCard catCard){
        return catCardDao.createCatCard(catCard);

    }

    @RequestMapping(path = "/api/cards/{id}", method = RequestMethod.PUT)
    public CatCard updateCatCard(@RequestBody CatCard catCard, @PathVariable int id){
        catCard.setCatCardId(id);
        try {
            CatCard updatedCatCard = catCardDao.updateCatCard(catCard);
            return updatedCatCard;

        }catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat Card not found.");
        }
    }


}
