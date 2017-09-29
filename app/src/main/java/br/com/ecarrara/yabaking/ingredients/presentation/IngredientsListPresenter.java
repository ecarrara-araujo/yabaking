package br.com.ecarrara.yabaking.ingredients.presentation;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.core.presentation.LoadDataPresenter;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;

class IngredientsListPresenter extends LoadDataPresenter<IngredientsListFragment, List<Ingredient>> {

    private IngredientFormatter ingredientFormatter;

    @Inject
    IngredientsListPresenter(IngredientFormatter ingredientFormatter) {
        this.ingredientFormatter = ingredientFormatter;
    }

    @Override
    protected void loadData(List<Ingredient> inputData) {
        List<String> formattedIngredients = ingredientFormatter.formatIngredients(inputData);
        hideLoading();
        hideError();
        this.view.showContent(formattedIngredients);
    }

    @Override
    public void destroy() { /* Do Nothing */ }

}
