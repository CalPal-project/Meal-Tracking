package prpo.mealtracking.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateMealRequest {
    public char mealType;
    public List<FoodAmount> foods;
    public LocalDateTime dateTime;
    public double calories;
}
