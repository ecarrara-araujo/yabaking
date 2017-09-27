package br.com.ecarrara.yabaking.recipes.presentation.listing;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.auto.value.AutoValue;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

@AutoValue
abstract class RecipeListItemViewModel {

    public abstract Integer recipeId();

    public abstract String name();

    public abstract String imageUrl();

    public static Builder builder() {
        return new AutoValue_RecipeListItemViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setRecipeId(Integer recipeId);

        public abstract Builder setName(String name);

        public abstract Builder setImageUrl(String imageUrl);

        public abstract RecipeListItemViewModel build();

    }

    static class Mapper {

        public static RecipeListItemViewModel fromRecipe(Recipe recipe) {
            return RecipeListItemViewModel.builder()
                    .setRecipeId(recipe.id())
                    .setName(recipe.name())
                    .setImageUrl(recipe.imageUrl())
                    .build();
        }

        public static List<RecipeListItemViewModel> fromRecipeList(List<Recipe> recipes) {
            return Stream.of(recipes).map(Mapper::fromRecipe).collect(Collectors.toList());
        }

    }

}
