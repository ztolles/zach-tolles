package com.techelevator.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatPicService implements CatPicService {

	private static final String API_BASE_URL = "https://cat-data.netlify.app/api/pictures/random";
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatPic getPic() {
		// TODO Auto-generated method stub
		CatPic myCatPic = null;
		try{
			myCatPic = restTemplate.getForObject(API_BASE_URL, CatPic.class);
		} catch (RestClientResponseException e) {
			System.out.println("Not correct, Rest Client");
		} catch (ResourceAccessException e){
			System.out.println("Incorrect, Resource access");
		}
		return myCatPic;
	}

//	@Override
//	public CatPic getPic(CatPic newCatPic){
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<CatPic> entity = new HttpEntity<>(newCatPic, headers);
//
//		CatPic resturnedCatPic = null;
//		 try{
//			 resturnedCatPic = restTemplate.postForObject(API_BASE_URL, entity, CatPic.class);
//
//		 }catch (RestClientResponseException e){
//			 System.out.println("something will go here in a minute");
//		 }catch (ResourceAccessException e){
//			 System.out.println("something else will go here later");
//		 }
//
//		return resturnedCatPic;
//
//	}


}	
