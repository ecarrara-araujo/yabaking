package br.com.ecarrara.yabaking.core.presentation;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.ecarrara.yabaking.R;
import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public abstract class LoadDataActivity<T> extends AppCompatActivity
        implements LoadDataView<T> {

    @BindView(R.id.progress_indicator)
    ProgressBar progressIndicator;
    @BindView(R.id.error_message_text_view)
    TextView errorDisplay;
    @BindView(R.id.retry_button)
    Button retryButton;

    @Override
    public void showLoading() {
        hideError();
        hideRetry();
        hideContent();
    }

    @Override
    public void hideLoading() {
        progressIndicator.setVisibility(GONE);
    }

    @Override
    public void showRetry() {
        retryButton.setVisibility(VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryButton.setVisibility(GONE);
    }

    @Override
    public void showError(String message) {
        hideLoading();
        hideContent();
        errorDisplay.setVisibility(VISIBLE);
        errorDisplay.setText(message);
    }

    @Override
    public void hideError() {
        errorDisplay.setVisibility(GONE);
    }

}
