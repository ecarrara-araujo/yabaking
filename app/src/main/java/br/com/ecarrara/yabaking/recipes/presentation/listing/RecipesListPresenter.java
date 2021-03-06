package br.com.ecarrara.yabaking.recipes.presentation.listing;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.core.presentation.LoadDataPresenter;
import br.com.ecarrara.yabaking.core.presentation.LoadDataView;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecipesListPresenter
        extends LoadDataPresenter<LoadDataView<List<RecipeListItemViewModel>>, Void>
        implements RecipeSelectedListener {

    private RecipesRepository recipesRepository;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public RecipesListPresenter(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @Override
    protected void loadData(Void inputData) {
        disposables.add(recipesRepository
                .list()
                .flatMap(recipes -> Single.just(RecipeListItemViewModel.Mapper.fromRecipeList(recipes)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayData,
                        exception -> displayError(exception.getMessage())
                )
        );
    }

    private void displayData(List<RecipeListItemViewModel> recipeListItemViewModels) {
        this.hideLoading();
        this.view.showContent(recipeListItemViewModels);
    }

    @Override
    public void destroy() {
        disposables.dispose();
    }

    @Override
    public void onRecipeSelected(Integer recipeId) {
        this.view.hideContent();
        this.displayLoading();
        disposables.add(recipesRepository
                .get(recipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::navigateToRecipeDetail,
                        exception -> displayError(exception.getMessage())
                )
        );
    }

    private void navigateToRecipeDetail(Recipe recipe) {
        if(this.view instanceof RecipesListView) {
            ((RecipesListView) this.view).navigateToRecipeDetail(recipe);
        }
        Timber.e("Incorrect usage of RecipesListView");
    }

}
