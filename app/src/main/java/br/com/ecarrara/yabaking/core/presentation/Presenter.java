package br.com.ecarrara.yabaking.core.presentation;

/**
 * A basic Presenter specification.
 *
 * @param <T> View that this Presenter will be attached to.
 * @param <P> Data that this Presenter can use to initialize the View.
 */
public interface Presenter<T, P> {

    /**
     * Attaches this Presenter to the specified view making usage of the provided data.
     *
     * @param view to attach the presenter
     * @param inputData to be used to initialize the view
     */
    void attachTo(T view, P inputData);

    /**
     * Method that respond to the View destroy lifecycle state.
     */
    void destroy();

}
