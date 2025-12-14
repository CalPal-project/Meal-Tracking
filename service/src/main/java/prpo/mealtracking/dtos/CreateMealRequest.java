package prpo.mealtracking.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class CreateMealRequest {
    public char mealType;
    public List<FoodAmount> foods;
    public LocalDateTime dateTime;
}
