package br.com.ecarrara.yabaking.recipes.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Measure;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;
import io.reactivex.Single;

public class FakeRecipeRepository implements RecipesRepository {

    List<Recipe> recipes;

    public FakeRecipeRepository() {
        recipes = new ArrayList<>();
        recipes.add(getFakeCookieRecipe());
        recipes.add(getFakeRiceRecipe());
        recipes.add(getFakePastaRecipe());
        recipes.add(getFakeSaladRecipe());
        recipes.add(getFakeDessertRecipe());
    }

    @Override
    public Single<List<Recipe>> list() {
        return Single.defer(() -> Single.just(recipes));
    }

    @Override
    public Single<Recipe> get(Integer recipeId) {
        return Single.defer(() -> Single.just(recipes.get(recipeId)));
    }

    private Recipe getFakeCookieRecipe() {
        return Recipe.builder()
                .setId(0)
                .setName("Awesome Cookies for Fun")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .setSteps(getFakeStepList())
                .build();
    }

    private Recipe getFakeRiceRecipe() {
        return Recipe.builder()
                .setId(1)
                .setName("Rice to be Really happy")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .setSteps(getFakeStepList())
                .build();
    }

    private Recipe getFakePastaRecipe() {
        return Recipe.builder()
                .setId(2)
                .setName("Pasta for the fatty ones")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .setSteps(getFakeStepList())
                .build();
    }

    private Recipe getFakeSaladRecipe() {
        return Recipe.builder()
                .setId(3)
                .setName("Salad for the Fits")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .setSteps(getFakeStepList())
                .build();
    }

    private Recipe getFakeDessertRecipe() {
        return Recipe.builder()
                .setId(4)
                .setName("Desserts for everyone!")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .setSteps(getFakeStepList())
                .build();
    }

    private List<Ingredient> getFakeIngredientsList() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(getCrackerCrumbs());
        ingredients.add(getUnsaltedButter());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        return ingredients;
    }

    private Ingredient getCrackerCrumbs() {
        return Ingredient.builder()
                .setMeasure(Measure.CUP)
                .setQuantity(2f)
                .setName("Graham Cracker crumbs")
                .build();
    }

    private Ingredient getUnsaltedButter() {
        return Ingredient.builder()
                .setMeasure(Measure.TABLE_SPOON)
                .setQuantity(6f)
                .setName("unsalted butter, melted")
                .build();
    }

    private Ingredient getGranulatedSugar() {
        return Ingredient.builder()
                .setMeasure(Measure.CUP)
                .setQuantity(0.5f)
                .setName("granulated sugar")
                .build();
    }

    private List<Step> getFakeStepList() {
        List<Step> steps = new ArrayList<>();
        steps.add(getStep1());
        steps.add(getStep2());
        steps.add(getRealStep());
        steps.add(getAnotherRealStep());
        return steps;
    }

    private Step getStep1() {
        return Step.builder()
                .setId(1)
                .setDescription("Step 1 - you should lalalalalalalalalala")
                .setShortDescription("Step 1 - lala")
                .setThumbnailPath("")
                .setVideoPath("")
                .build();
    }

    private Step getStep2() {
        return Step.builder()
                .setId(2)
                .setDescription("Step 2 - you must lololololo")
                .setShortDescription("Step 2 - lolo")
                .setThumbnailPath("https://www.hestancue.com/wp-content/themes/hestan/images/get-start-pic-2.jpg")
                .setVideoPath("")
                .build();
    }

    private Step getRealStep() {
        return Step.builder()
                .setId(3)
                .setDescription("Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")
                .setShortDescription("Prep the cookie crust.")
                .setThumbnailPath("")
                .setVideoPath("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4")
                .build();
    }

    private Step getAnotherRealStep() {
        return Step.builder()
                .setId(4)
                .setDescription("Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.")
                .setShortDescription("Start filling prep")
                .setThumbnailPath("")
                .setVideoPath("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4")
                .build();
    }

}
