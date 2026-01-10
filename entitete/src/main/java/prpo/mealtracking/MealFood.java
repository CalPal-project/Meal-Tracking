package prpo.mealtracking;

import jakarta.persistence.*;

@Entity
@Table(name = "MealFood")
public class MealFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Meal meal;
    @ManyToOne
    private Food food;

    private double amount;

    private Double calories; //izracunane kalorije 

    public MealFood(){

    }

    public MealFood(Meal meal, Food food, Double amount){
        this.meal = meal;
        this.food = food;
        this.amount = amount;
    }

    public Integer getId(){
        return id;
    }

    public Meal getmeal(){
        return meal;
    }

    public Food getfood(){
        return food;
    }

    public void setAmount(double a){
        this.amount = a;
    }

    public double getamount(){
        return amount;
    }

    public void setmeal(Meal m){
        this.meal = m;
    }

    public void setfood(Food f){
        this.food = f;
    }

    public void setamout(double i){
        this.amount = i;
    }

    public void setCalories(Double c){
        this.calories = c;
    }
    
    public Double getCalories(){
        return calories;
    }
}
