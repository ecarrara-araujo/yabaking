package br.com.ecarrara.yabaking.functional;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import br.com.ecarrara.yabaking.recipes.presentation.listing.RecipesListActivity;
import br.com.ecarrara.yabaking.robot.RecipesListingRobot;
import br.com.ecarrara.yabaking.utils.rule.Rx2IdlingSchedulerRule;

@RunWith(AndroidJUnit4.class)
public class ViewRecipe {

    @Rule
    public Rx2IdlingSchedulerRule rx2IdlingSchedulerRule =
            new Rx2IdlingSchedulerRule();

    @Rule
    public ActivityTestRule<RecipesListActivity> activityActivityTestRule =
            new ActivityTestRule<RecipesListActivity>(RecipesListActivity.class);

    @Test
    public void viewRecipe() {
        final String recipeName = getExpectedRecipeTitle();
        List<String> ingredientsList = getExpectedIngredientsList();
        List<String> stepsShortDescriptionList = getExpectedStepsShortDescriptionList();
        List<String> stepsLongDescriptionList = getExpectedStepsLongDescriptionList();

        RecipesListingRobot.getRobot()
                .selectRecipe(recipeName)
                .checkRecipeName(recipeName)
                .navigateToIngredientsList()
                .checkIngredients(ingredientsList)
                .navigateToStepsList()
                .checkSteps(stepsShortDescriptionList)
                .selectFirstStep()
                .checkShortAndLongDescriptionsThroughAllSteps(
                        stepsShortDescriptionList, stepsLongDescriptionList);
    }

    private String getExpectedRecipeTitle() {
        return "Cheesecake";
    }

    private List<String> getExpectedIngredientsList() {
        return Arrays.asList(
                "2 cup(s) of Graham Cracker crumbs",
                "6 tablespoon(s) of unsalted butter, melted",
                "250 gram(s) of granulated sugar",
                "1 teaspoon(s) of salt",
                "4 teaspoon(s) of vanilla,divided",
                "680 gram(s) of cream cheese, softened",
                "3 unit(s) of large whole eggs",
                "2 unit(s) of large egg yolks",
                "250 gram(s) of heavy cream"

        );
    }

    private List<String> getExpectedStepsShortDescriptionList() {
        return Arrays.asList(
                "Recipe Introduction",
                "Starting prep.",
                "Prep the cookie crust.",
                "Start water bath.",
                "Prebake cookie crust. ",
                "Mix cream cheese and dry ingredients.",
                "Add eggs.",
                "Add heavy cream and vanilla.",
                "Pour batter in pan.",
                "Bake the cheesecake.",
                "Turn off oven and leave cake in.",
                "Remove from oven and cool at room temperature.",
                "Final cooling and set."
        );
    }


    public List<String> getExpectedStepsLongDescriptionList() {
        return Arrays.asList(
                "Recipe Introduction",
                "1. Preheat the oven to 350\u00b0F. Grease the bottom of a 9-inch round springform pan with butter. ",
                "2. To assemble the crust, whisk together the cookie crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt for the crust in a medium bowl. Stir in the melted butter and 1 teaspoon of vanilla extract until uniform. ",
                "3. Fill a large roasting pan with a few inches of hot water and place it on the bottom rack of the oven.",
                "4. Press the cookie mixture into the bottom and slightly up the sides of the prepared pan. Bake for 11 minutes and then let cool.",
                "5. Beat the cream cheese, remaining 200 grams (1 cup) of sugar, and remaining 1/2 teaspoon salt on medium speed in a stand mixer with the paddle attachment for 3 minutes (or high speed if using a hand mixer). ",
                "6. Scrape down the sides of the pan. Add in the eggs one at a time, beating each one on medium-low speed just until incorporated. Scrape down the sides and bottom of the bowl. Add in both egg yolks and beat until just incorporated. ",
                "7. Add the cream and remaining tablespoon of vanilla to the batter and beat on medium-low speed until just incorporated. ",
                "8. Pour the batter into the cooled cookie crust. Bang the pan on a counter or sturdy table a few times to release air bubbles from the batter.",
                "9. Bake the cheesecake on a middle rack of the oven above the roasting pan full of water for 50 minutes. ",
                "10. Turn off the oven but keep the cheesecake in the oven with the door closed for 50 more minutes.",
                "11. Take the cheesecake out of the oven. It should look pale yellow or golden on top and be set but still slightly jiggly. Let it cool to room temperature. ",
                "12. Cover the cheesecake with plastic wrap, not allowing the plastic to touch the top of the cake, and refrigerate it for at least 8 hours. Then it's ready to serve!"
        );
    }
}
