package prpo.mealtracking;

import jakarta.persistence.*;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String foodName;
    private double calories;

    public Food(){

    }

    public Food(String foodname, Integer calories){
        this.foodName = foodname;
        this.calories = calories;
    }

    public Integer getId(){
        return id;
    }

    public double getCalories(){
        return calories;
    }

    public String getFoodName(){
        return foodName;
    }

    public void setCalories(Integer i){
        this.calories = i;
    }

    public void setfoodName(String s){
        this.foodName = s;
    }

}