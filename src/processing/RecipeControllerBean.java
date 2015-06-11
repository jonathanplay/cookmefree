package processing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.RecipeListModelBean;
import model.RecipeModel;
import dao.fabric.DaoFabric;
import dao.instance.RecipesDao;

@ManagedBean(name="recipeControl")
@SessionScoped
public class RecipeControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private RecipeModel specificRecipe;
	private RecipesDao recipeDao;

	public RecipeModel getSpecificRecipe() {
		return specificRecipe;
	}

	/**
	 * recupere une recette a partir de son id
	 * @return RecipeModel
	 */
	public RecipeModel getRecipeById(int id) {
		return recipeDao.getRecipesByID(id);
	}
	public RecipeControllerBean() {
		this.recipeDao=DaoFabric.getInstance().createRecipesDao();
	}
	
	public void loadAllRecipe(){
		ArrayList<RecipeModel> list = this.recipeDao.getAllRecipes();
		RecipeListModelBean recipeList = new RecipeListModelBean();
		for(RecipeModel recipe:list){
            recipeList.addRecipeList(recipe);
		}
		//reÌ�cupeÌ€re l'espace de meÌ�moire de JSF
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		//place la liste de recette dans l'espace de meÌ�moire de JSF
		sessionMap.put("recipeList", recipeList);
	}
	
	public String goToResult(){

		//specificRecipe = this.recipeDao.getRecipesByID(1);
		//return("showRecipe.jsf?faces-redirect=true");
		return("resultRecipe.jsf?faces-redirect=true");

	}
	
	public String goToShowRecipe(int id){
		specificRecipe = this.recipeDao.getRecipesByID(id);
		return("showRecipe.jsf?faces-redirect=true");
	}
}
