package prpo.mealtracking;

import org.springframework.web.bind.annotation.*;


@RestController
public class MealtrackingApi {
    private final MealRepository me;
    private final MealFoodRepository mf;

    public MealtrackingApi(MealRepository me, MealFoodRepository mf){
        this.me = me;
        this.mf = mf;
    }

    @GetMapping("/api/getMeal")
    public String getMeal(int id){
        return "hello from mealTracking";
    }

    @PostMapping("/api/addMeal")
    public Meal addMeal(@RequestBody Meal meal){
        return me.save(meal);
    }

}
