package prpo.mealtracking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.*;

import prpo.mealtracking.dtos.CreateMealRequest;
import prpo.mealtracking.dtos.FoodAmount;
import prpo.mealtracking.dtos.UpdateMealRequest;

@CrossOrigin(origins = "*")  // Dovoli vse origins
@RestController
public class MealtrackingApi {
    private final MealRepository me;
    private final MealFoodRepository mf;
    private final FoodRepository fr;

    public MealtrackingApi(MealRepository me, MealFoodRepository mf, FoodRepository fr){
        this.me = me;
        this.mf = mf;
        this.fr = fr;
    }

    //ni dokumentacije
    @GetMapping("/api/getMeal")
    public Optional<Meal> getMeal(Long id){
        return me.findById(id);
    }

    @GetMapping("/api/getFood")
    public Optional<Food> getFood(Integer id){
        return fr.findById((long) id);
    }

    @PostMapping("/api/addMeal")
    @Transactional
    public Meal createMeal(@RequestBody CreateMealRequest request){
        Meal meal = new Meal();
        //double totalCal = 0;
        meal.setmealType(request.mealType);
        meal.setDateTime(request.dateTime);
        meal.setCalories(request.calories);
        meal.setUserId(request.userId);

        meal = me.save(meal);
        
        for(FoodAmount fa : request.foods){
            Food food = fr.findById(fa.foodId).orElseThrow();
            
            MealFood mfood = new MealFood();
            mfood.setmeal(meal);
            mfood.setfood(food);
            mfood.setAmount(fa.amount);
            double calories =  Math.round((food.getCalories() * mfood.getamount()) / 100);
            //totalCal += calories;
            mfood.setCalories(calories);
            mf.save(mfood);
        }
        return meal;
    }   

    @GetMapping("/api/findFood")
    public List<Food> findFood(@RequestParam String ime){
        List<Food> rez = fr.findByfoodNameContainingIgnoreCase(ime);
        return rez;
    }

    @GetMapping("/api/date")
    public List<Map<String, Object>> getMealsByDate(@RequestParam String date, Long userId) {
        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime startOfNextDay = localDate.plusDays(1).atStartOfDay();
        
        // Pridobi obroke za ta dan
        List<Meal> meals = me.findByDateBetween(startOfDay, startOfNextDay, userId);
        
        return meals.stream()
            .map(meal -> {
                Map<String, Object> mealMap = new HashMap<>();
                mealMap.put("id", meal.getId());
                mealMap.put("mealType", meal.getmealType());
                mealMap.put("dateTime", meal.getDateTime());
                mealMap.put("calories", meal.getCalories());
                
                // Foods kot seznam hashmapov
                List<Map<String, Object>> foods = meal.getFoods().stream()
                    .map(mf -> {
                        Map<String, Object> foodMap = new HashMap<>();
                        foodMap.put("id", mf.getId());
                        foodMap.put("foodName", mf.getfood() != null ? mf.getfood().getFoodName() : "");
                        foodMap.put("amount", mf.getamount());
                        foodMap.put("calories", mf.getCalories());
                        
                        return foodMap;
                    })
                    .collect(Collectors.toList());
                
                mealMap.put("foods", foods);
                return mealMap;
            })
            .collect(Collectors.toList());
    }

    @GetMapping("/api/mealsToday")
    public List<Map<String, Object>> getTodayMealsSimple(@RequestParam Long userId) {

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();

        List<Meal> meals = me.findTodayMealsByUserId(userId, startOfDay, endOfDay);

        return meals.stream()
            .map(meal -> {
                Map<String, Object> mealMap = new HashMap<>();
                mealMap.put("id", meal.getId());
                mealMap.put("mealType", meal.getmealType());
                mealMap.put("dateTime", meal.getDateTime());
                mealMap.put("calories", meal.getCalories());

                List<Map<String, Object>> foods = meal.getFoods().stream()
                    .map(mf -> {
                        Map<String, Object> foodMap = new HashMap<>();
                        foodMap.put("foodName", mf.getfood() != null ? mf.getfood().getFoodName() : "");
                        foodMap.put("amount", mf.getamount());
                        foodMap.put("calories", mf.getCalories());
                        return foodMap;
                    })
                    .collect(Collectors.toList());

                mealMap.put("foods", foods);
                return mealMap;
            })
            .collect(Collectors.toList());
    }

    @DeleteMapping("/api/deleteMeal")
    public void deleteMeal(@RequestParam Long id){
       me.deleteById(id);
    }

    @PutMapping("/api/updateMeal")
    public Meal updateMeal(@RequestParam Long id, @RequestBody UpdateMealRequest updateRequest){
        Optional<Meal> omeal = me.findById(id);
        Meal meal = omeal.get();
        meal.setmealType(updateRequest.mealType);
        meal.setDateTime(updateRequest.dateTime);
        meal.setCalories(updateRequest.calories);
        meal.setUserId(updateRequest.userId);

        me.deleteMealFoodsByMealId(id);
        meal = me.save(meal);

        for(FoodAmount fa : updateRequest.foods){
            Optional<Food> ofood = fr.findById(fa.foodId);
            Food food = ofood.get();
            MealFood mfood = new MealFood();
            mfood.setmeal(meal);
            mfood.setfood(food);
            mfood.setAmount(fa.amount);
            double calories =  Math.round((food.getCalories() * mfood.getamount()) / 100);
            mfood.setCalories(calories);
            mf.save(mfood);

        }
        return meal;
    }
}
