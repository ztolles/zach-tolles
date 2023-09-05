package com.techelevator.dao;

import com.techelevator.model.CatCard;

import java.util.List;

public interface CatCardDao {

	List<CatCard> getCatCards();

	CatCard getCatCardById(int id);

	CatCard createCatCard(CatCard catCard);

	CatCard updateCatCard(CatCard card);

	int deleteCatCardById(int id);

}
