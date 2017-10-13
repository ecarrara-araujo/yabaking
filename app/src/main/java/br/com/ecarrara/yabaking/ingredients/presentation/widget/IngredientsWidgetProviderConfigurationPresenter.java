package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.core.presentation.LoadDataPresenter;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class IngredientsWidgetProviderConfigurationPresenter
        extends LoadDataPresenter<IngredientsWidgetProviderConfigurationActivity, Void>
        implements RecipeSelectedForWidgetListener {

    private RecipesRepository recipesRepository;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    IngredientsWidgetProviderConfigurationPresenter(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @Override
    protected void loadData(Void inputData) {
        disposables.add(recipesRepository
                .list()
                .flatMap(recipes -> Single.just(SimpleRecipeListItemViewModel.Mapper.fromRecipeList(recipes)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayData,
                        exception -> displayError(exception.getMessage())
                )
        );

    }

    private void displayData(List<SimpleRecipeListItemViewModel> recipeNames) {
        this.hideLoading();
        this.view.showContent(recipeNames);
    }

    @Override
    public void destroy() {
        disposables.dispose();
    }

    @Override
    public void onRecipeSelectedForWidget(int recipeId) {
        this.view.hideContent();
        this.displayLoading();
        storeSelectedRecipe(recipeId);
    }

    private void storeSelectedRecipe(int recipeId) {
        disposables.add(recipesRepository
                .get(recipeId)
                .flatMapCompletable(recipe -> recipesRepository.setRecipeForWidgetDisplay(recipe))
                .andThen(recipesRepository.get(recipeId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::notifyWidget,
                        exception -> displayError(exception.getMessage())
                )
        );

    }

    private void notifyWidget(Recipe recipe) {
        this.view.notifyWidget(recipe);
    }

}
