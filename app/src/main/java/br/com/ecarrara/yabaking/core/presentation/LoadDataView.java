package br.com.ecarrara.yabaking.core.presentation;

/**
 * Represents a View that loads Data Asynchronously
 *
 * @param <T> Type of the data that the view will handle.
 */
public interface LoadDataView<T> {

    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError(String message);

    void hideError();

    void showContent(T viewData);

    void hideContent();

}
