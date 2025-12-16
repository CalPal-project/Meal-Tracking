package prpo.mealtracking;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private char mealType;
    private LocalDateTime dateTime;

    private Double calories;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    private List<MealFood> mealFoods = new ArrayList<>();

    public Meal(){

    }

    public Meal(char mealType){
        this.mealType = mealType;
    }

    public Integer getId(){
        return id;
    }

    public char getmealType(){
        return mealType;
    }

     public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setmealType(char c){
        this.mealType = c;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCalories(Double c){
        this.calories = c;
    }

    public Double getCalories(){
        return calories;
    }

    public List<MealFood> getFoods(){
        return mealFoods;
    }

}