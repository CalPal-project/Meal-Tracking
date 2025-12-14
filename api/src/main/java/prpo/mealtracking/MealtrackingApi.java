package prpo.mealtracking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.*;

import prpo.mealtracking.dtos.CreateMealRequest;
import prpo.mealtracking.dtos.FoodAmount;

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

    @GetMapping("/api/getMeal")
    public String getMeal(int id){
        return "hello from mealTracking";
    }

    // @PostMapping("/api/addMeal")
    // public Meal addMeal(@RequestBody Meal meal){
    //     return me.save(meal);
    // }

    @GetMapping("/api/getFood")
    public Optional<Food> getFood(Integer id){
        return fr.findById((long) id);
    }

    @PostMapping("/api/addMeal")
    public Meal createMeal(@RequestBody CreateMealRequest request){
        Meal meal = new Meal();

        meal.setmealType(request.mealType);
        meal.setDateTime(request.dateTime);
        meal = me.save(meal);
        for(FoodAmount fa : request.foods){
            Food food = fr.findById(fa.foodId).orElseThrow();

            MealFood mfood = new MealFood();
            mfood.setmeal(meal);
            mfood.setfood(food);
            mfood.setAmount(fa.amount);

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
    public List<Meal> getMealsByDate(@PathVariable String date) {
        // Pretvori string v LocalDate
        LocalDate localDate = LocalDate.parse(date);
        
        // Izračunaj začetek in konec dneva
        LocalDateTime startOfDay = localDate.atStartOfDay();          // 2024-12-14T00:00:00
        LocalDateTime startOfNextDay = localDate.plusDays(1).atStartOfDay(); // 2024-12-15T00:00:00
        
        // Pridobi obroke za ta dan
        return me.findByDateBetween(startOfDay, startOfNextDay);
    }

    @GetMapping("/api/mealsToday")
    public List<Meal> getTodayMeals() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime startOfTomorrow = today.plusDays(1).atStartOfDay();
        
        return me.findByDateBetween(startOfToday, startOfTomorrow);
    }

}
