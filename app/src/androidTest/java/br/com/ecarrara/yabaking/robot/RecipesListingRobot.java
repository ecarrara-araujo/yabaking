package br.com.ecarrara.yabaking.robot;

import br.com.ecarrara.yabaking.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class RecipesListingRobot {

    public static RecipesListingRobot getRobot() {
        return new RecipesListingRobot();
    }

    private RecipesListingRobot() {
    }

    public RecipeDetailsRobot selectRecipe(String recipeName) {
        onView(withId(R.id.recipes_list_recycler_view))
                .perform(scrollTo(hasDescendant(withText(recipeName))));
        onView(withId(R.id.recipes_list_recycler_view))
                .perform(actionOnItem(hasDescendant(withText(recipeName)), click()));
        return RecipeDetailsRobot.getRobot();
    }

}
