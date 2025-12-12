package prpo.mealtracking;
import jakarta.persistence.*;

@Entity
@Table(name = "MealFood")
public class MealFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer mealId;
    private Integer foodId;
    private Integer amount;

    public MealFood(){

    }

    public MealFood(Integer mealId, Integer foodId, Integer amount){
        this.mealId = mealId;
        this.foodId = foodId;
        this.amount = amount;
    }

    public Integer getId(){
        return id;
    }

    public Integer getmealId(){
        return mealId;
    }

    public Integer getfoodId(){
        return foodId;
    }

    public Integer getamount(){
        return amount;
    }

    public void setmealId(Integer i){
        this.mealId = i;
    }

    public void setfoodId(Integer i){
        this.foodId = i;
    }

    public void setamout(Integer i){
        this.amount = i;
    }
}
