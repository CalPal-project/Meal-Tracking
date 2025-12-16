package prpo.mealtracking.dtos;

import prpo.mealtracking.MealFood;

class FoodItemDto {
    private String foodName;
    private Double amount;
    private Double calories;

    public FoodItemDto(MealFood mealFood) {
        this.foodName = mealFood.getfood() != null ? mealFood.getfood().getFoodName() : "Neznano";
        this.amount = mealFood.getamount();
        this.calories = mealFood.getCalories();
    }

    // Getterji in setterji
    public String getFoodName() { return foodName; }
    public Double getAmount() { return amount; }
    public Double getCalories() { return calories; }
}
