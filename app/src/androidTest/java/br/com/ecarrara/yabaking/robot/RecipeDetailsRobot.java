package br.com.ecarrara.yabaking.robot;

import java.util.List;

import br.com.ecarrara.yabaking.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.ecarrara.yabaking.utils.matcher.ToolbarMatchers.matchCollapsingToolbarTitle;

public class RecipeDetailsRobot {

    public static RecipeDetailsRobot getRobot() {
        return new RecipeDetailsRobot();
    }

    private RecipeDetailsRobot() {
    }

    public RecipeDetailsRobot checkRecipeName(String recipeName) {
        matchCollapsingToolbarTitle(recipeName).check(matches(isDisplayed()));
        return this;
    }

    public RecipeDetailsRobot navigateToIngredientsList() {
        onView(withText(R.string.recipe_details_ingredient))
                .perform(click());
        return this;
    }

    public RecipeDetailsRobot checkIngredients(List<String> ingredients) {
        for (String ingredient : ingredients) {
            onView(withId(R.id.ingredients_list_recycler_view))
                    .perform(scrollTo(hasDescendant(withText(ingredient))));
            onView(withId(R.id.ingredients_list_recycler_view))
                    .check(matches(hasDescendant(withText(ingredient))));
        }

        return this;
    }

    public RecipeDetailsRobot navigateToStepsList() {
        onView(withText(R.string.recipe_details_steps))
                .perform(click());
        return this;
    }

    public RecipeDetailsRobot checkSteps(List<String> steps) {
        for (String step : steps) {
            onView(withId(R.id.steps_list_recycler_view))
                    .perform(scrollTo(hasDescendant(withText(step))));
            onView(withId(R.id.steps_list_recycler_view))
                    .check(matches(hasDescendant(withText(step))));
        }

        return this;
    }

    public StepsNavigationRobot selectFirstStep() {
        navigateToStepsList();

        onView(withId(R.id.steps_list_recycler_view))
                .perform(scrollToPosition(0));
        onView(withId(R.id.steps_list_recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        return StepsNavigationRobot.getRobot();
    }
}
