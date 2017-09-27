package br.com.ecarrara.yabaking.core.presentation;

/**
 * A Presenter that specifies asynchronous data loading behavior.
 */
public abstract class LoadDataPresenter<T extends LoadDataView, P> implements Presenter<T, P> {

    protected T view;

    /**
     * Does the heavy lifting for data loading.
     */
    protected abstract void loadData(P inputData);

    @Override
    public void attachTo(T view, P inputData) {
        this.view = view;
        displayLoading();
        loadData(inputData);
    }

    protected void displayLoading() {
        this.view.hideRetry();
        this.view.hideError();
        this.view.showLoading();
    }

    protected void hideLoading() {
        this.view.hideLoading();
    }

    protected void displayError(String message) {
        this.view.hideLoading();
        this.view.showError(message);
        this.view.showRetry();
    }

    protected void hideError() {
        this.view.hideError();
        this.view.hideRetry();
    }

}
