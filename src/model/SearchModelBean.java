package model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/*
 * 
 */
@ManagedBean
@RequestScoped
public class SearchModelBean implements Serializable {
	
	//////////ATTRIBUTES
	private static final long serialVersionUID = 1L;
	private int duration=0, expertise, people=1;
	private String type;
	private boolean indiff1=false, indiff2=false, indiff3=false, indiff4=false;
	private boolean blockedindiff2=false, blockedindiff3=false;
	
	////////////MUTATORS
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getExpertise() {
		return expertise;
	}
	public void setExpertise(int expertise) {
		if(expertise <=5 && expertise >= 1){
			this.expertise = expertise;
		}
		else{
			this.indiff2 = true;
			this.blockedindiff2=true;
		}
	}
	
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		if(people <=10 && people >= 1){
			this.people = people;
		}
		else{
			this.indiff3 = true;
			this.blockedindiff3=true;
		}
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isIndiff1() {
		return indiff1;
	}
	public void setIndiff1(boolean indiff1) {
		this.indiff1 = indiff1;
	}
	
	public boolean isIndiff2() {
		return indiff2;
	}
	public void setIndiff2(boolean indiff2) {
		if(!this.blockedindiff2){
			this.indiff2 = indiff2;
		}
	}
	
	public boolean isIndiff3() {
		return indiff3;
	}
	public void setIndiff3(boolean indiff3) {
		if(!this.blockedindiff3){
			this.indiff3 = indiff3;
		}
	}
	
	public boolean isIndiff4() {
		return indiff4;
	}
	public void setIndiff4(boolean indiff4) {
		this.indiff4 = indiff4;
	}
	
	/////////CONSTRUCTORS
	public SearchModelBean(int duration, int expertise, int people,
			String type, boolean indiff1, boolean indiff2, boolean indiff3,
			boolean indiff4) {
		super();
		this.duration = duration;
		this.expertise = expertise;
		this.people = people;
		this.type = type;
		this.indiff1 = indiff1;
		this.indiff2 = indiff2;
		this.indiff3 = indiff3;
		this.indiff4 = indiff4;
	}
	public SearchModelBean() {
		super();
	}
	
}
