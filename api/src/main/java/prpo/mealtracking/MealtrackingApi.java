package prpo.mealtracking;

import org.springframework.web.bind.annotation.*;

@RestController
public class MealtrackingApi {

    @GetMapping("/api/getMeal")
    public String getMeal(int id){
        return "hello from mealTracking";
    }

}
