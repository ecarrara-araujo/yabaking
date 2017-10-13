package br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import br.com.ecarrara.yabaking.core.data.DBFlowRecipeDatabase;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

@Table(name = DBFlowRecipe.TABLE_NAME, database = DBFlowRecipeDatabase.class)
public class DBFlowRecipe {

    public static final String TABLE_NAME = "recipe";

    @PrimaryKey
    Integer id;

    @Column
    String name;

    @Column
    String imageUrl;

    List<DBFlowIngredient> ingredients;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "ingredients")
    public List<DBFlowIngredient> getIngredients() {
        if (ingredients == null || ingredients.isEmpty()) {
            ingredients = SQLite.select()
                    .from(DBFlowIngredient.class)
                    .where(DBFlowIngredient_Table.recipe_id.eq(id))
                    .queryList();
        }
        return ingredients;
    }

    List<DBFlowStep> steps;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "steps")
    public List<DBFlowStep> getSteps() {
        if (steps == null || steps.isEmpty()) {
            steps = SQLite.select()
                    .from(DBFlowStep.class)
                    .where(DBFlowStep_Table.recipe_id.eq(id))
                    .orderBy(DBFlowStep_Table.order.asc())
                    .queryList();
        }
        return steps;
    }

    public static final class Mapper {

        private Mapper() { /* no construction allowed */ }

        public static Recipe toDomain(DBFlowRecipe dbFlowRecipe) {
            return Recipe.builder()
                    .setId(dbFlowRecipe.id)
                    .setImageUrl(dbFlowRecipe.imageUrl)
                    .setName(dbFlowRecipe.name)
                    .setIngredients(DBFlowIngredient.Mapper.toDomain(dbFlowRecipe.ingredients))
                    .setSteps(DBFlowStep.Mapper.toDomain(dbFlowRecipe.steps))
                    .build();
        }

        public static List<Recipe> toDomain(List<DBFlowRecipe> dbFlowRecipes) {
            return Stream.of(dbFlowRecipes).map(DBFlowRecipe.Mapper::toDomain).collect(Collectors.toList());
        }

        public static DBFlowRecipe fromDomain(Recipe recipe) {
            DBFlowRecipe dbFlowRecipe = new DBFlowRecipe();
            dbFlowRecipe.id = recipe.id();
            dbFlowRecipe.name = recipe.name();
            dbFlowRecipe.imageUrl = recipe.imageUrl();
            dbFlowRecipe.ingredients = DBFlowIngredient.Mapper.fromDomain(recipe.ingredients(), recipe.id());
            dbFlowRecipe.steps = DBFlowStep.Mapper.fromDomain(recipe.steps(), recipe.id());
            return dbFlowRecipe;
        }

        public static List<DBFlowRecipe> fromDomain(List<Recipe> recipes) {
            return Stream.of(recipes).map(DBFlowRecipe.Mapper::fromDomain).collect(Collectors.toList());
        }

    }

}
