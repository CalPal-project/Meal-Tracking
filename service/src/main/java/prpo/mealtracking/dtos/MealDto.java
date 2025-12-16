package prpo.mealtracking.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import prpo.mealtracking.Meal;
import prpo.mealtracking.MealFood;

public class MealDto {
    private Integer id;
    private char mealType;
    private LocalDateTime dateTime;
    private Double calories;
    private List<FoodItemDto> foods;

    public MealDto(Meal meal) {
        this.id = meal.getId();
        this.mealType = meal.getmealType();
        this.dateTime = meal.getDateTime();
        this.calories = meal.getCalories();
        this.foods = meal.getFoods().stream()
            .map(FoodItemDto::new)
            .collect(Collectors.toList());
    }

    // Getterji in setterji
    public Integer getId() { return id; }
    public char getMealType() { return mealType; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Double getCalories() { return calories; }
    public List<FoodItemDto> getFoods() { return foods; }
}
