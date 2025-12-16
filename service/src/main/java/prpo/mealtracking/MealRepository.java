package prpo.mealtracking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    
    // 1. Pridobi vse obroke za določen datum (uporabljajoč LocalDateTime)
    @Query("SELECT m FROM Meal m WHERE m.dateTime >= :startOfDay AND m.dateTime < :startOfNextDay")
    List<Meal> findByDateBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("startOfNextDay") LocalDateTime startOfNextDay);
    
    // 2. Pridobi današnje obroke
    @Query("SELECT m FROM Meal m WHERE m.dateTime >= :startOfToday AND m.dateTime < :startOfTomorrow")
    List<Meal> findTodayMeals(@Param("startOfToday") LocalDateTime startOfToday, @Param("startOfTomorrow") LocalDateTime startOfTomorrow);

    // izbirse vse trenutne mealfoods ki pripadajo mealu (uporabljeno v update meal funkciji)
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM meal_food WHERE meal_id = :id", nativeQuery = true)
    void deleteMealFoodsByMealId(@Param("id") Long id);
}