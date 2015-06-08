package dao.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.RecipeModel;
import model.SearchModelBean;

public class RecipesDao {
	private static String dB_HOST;   
	private static String dB_PORT;
	private static String dB_NAME;
	private static String dB_USER;
	private static String dB_PWD;
	private Connection connection;
	
	public RecipesDao(String DB_HOST,String DB_PORT, String DB_NAME,String DB_USER,String DB_PWD) {
	       dB_HOST = DB_HOST;
	       dB_PORT = DB_PORT;
	       dB_NAME = DB_NAME;
	       dB_USER = DB_USER;
	       dB_PWD = DB_PWD;
	}
	
	/**
	 * Ajout d'un élément recipe en base de données
	 * @param recipe
	 */
	public void addRecipe(RecipeModel recipe) {
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+dB_HOST+":"+dB_PORT+"/"+dB_NAME, dB_USER, dB_PWD);
			
			String sql = "INSERT INTO recipe (title, description, expertise, duration, nbpeople, type) VALUES(?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, recipe.getTitle());
			statement.setString(2, recipe.getDescription());
			statement.setInt(3, recipe.getExpertise());
			statement.setInt(4, recipe.getDuration());
			statement.setInt(5, recipe.getNbPeople());
			statement.setString(6, recipe.getType());
			
			statement.executeUpdate();
			
			connection.close(); 
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Récupération de la liste des recettes
	 * @return ArrayList contenant des beans RecipeModelBean
	 */
	public ArrayList<RecipeModel> getAllRecipes(){ 
		ArrayList<RecipeModel> recipeList=new ArrayList<RecipeModel>();
		
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+dB_HOST+":"+dB_PORT+"/"+dB_NAME, dB_USER, dB_PWD);
			
			java.sql.Statement query = connection.createStatement(); 
			ResultSet rs = query.executeQuery("select * from recipe"); 
			
			while (rs.next()){ 
				recipeList.add(new RecipeModel(rs.getString("title"), rs.getString("description"), rs.getInt("Expertise"),
						rs.getInt("duration"), rs.getInt("nbPeople"), rs.getString("type"))
				); 
			}
			
			connection.close();
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}
		return recipeList;
	}
	
	/**
	 * Récupération de la liste des recettes
	 * @return ArrayList contenant des beans RecipeModelBean
	 */
	public RecipeModel getRecipesByID(int id){ 
		RecipeModel recipe = null;
		
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+dB_HOST+":"+dB_PORT+"/"+dB_NAME, dB_USER, dB_PWD);
			
			java.sql.PreparedStatement query = connection.prepareStatement("select * from recipe where id=?");
			query.setInt(1, id);
			ResultSet rs = query.executeQuery(); 
			
			rs.next();
			recipe = new RecipeModel(rs.getString("title"), rs.getString("description"), rs.getInt("Expertise"),
					rs.getInt("duration"), rs.getInt("nbPeople"), rs.getString("type"));
			
			connection.close();
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}
		return recipe;
	}
	
	public ArrayList<RecipeModel> getRecipesByCriteria(SearchModelBean search){
		ArrayList<RecipeModel> returnV=new ArrayList<>();
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+dB_HOST+":"+dB_PORT+"/"+dB_NAME, dB_USER, dB_PWD);
			String queryString="select * from recipe where ";
			boolean preceded=false;
			if(!search.isIndiff1()){
				if(preceded){
					queryString+="and ";
				}
				else{
					preceded=true;
				}
				queryString+="duration=? ";
			}
			if(!search.isIndiff2()){
				if(preceded){
					queryString+="and ";
				}
				else{
					preceded=true;
				}
				queryString+="expertise=? ";
			}
			if(!search.isIndiff3()){
				if(preceded){
					queryString+="and ";
				}
				else{
					preceded=true;
				}
				queryString+="people=? ";
			}
			if(!search.isIndiff4()){
				if(preceded){
					queryString+="and ";
				}
				else{
					preceded=true;
				}
				queryString+="type=? ";
			}
			java.sql.PreparedStatement query = connection.prepareStatement(queryString);
			if(!search.isIndiff1()){
				query.setInt(1, search.getDuration());
			}
			if(!search.isIndiff2()){
				query.setInt(2, search.getExpertise());
			}
			if(!search.isIndiff3()){
				query.setInt(3, search.getPeople());
			}
			if(!search.isIndiff4()){
				query.setString(4, search.getType());
			}
			
			ResultSet rs = query.executeQuery();
			while (rs.next()){ 
				returnV.add(new RecipeModel(rs.getString("title"), rs.getString("description"), rs.getInt("Expertise"),
						rs.getInt("duration"), rs.getInt("nbPeople"), rs.getString("type"))
				); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnV;
	}
}
