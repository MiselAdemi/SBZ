package sbz.projekat.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sbz.projekat.entity.KategorijaArtikla;
import sbz.projekat.service.ArticleCategoryService;

@RequestMapping("/api/articleCategory")
@RestController
public class ArticleCategoryController {

	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> get()
	{ 
		List<KategorijaArtikla> articleCategories = articleCategoryService.getAll();
		
		if (articleCategories == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(articleCategories, HttpStatus.OK);
	}
	
	@RequestMapping(value="/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> addCategory(@RequestBody KategorijaArtikla category) { 
		articleCategoryService.saveArticleCategory(category);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> deleteCategory(@RequestBody KategorijaArtikla category) { 
		articleCategoryService.removeArticleCategory(category);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateCategory(@RequestBody KategorijaArtikla category) { 
		articleCategoryService.saveArticleCategory(category);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
