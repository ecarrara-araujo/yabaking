package br.com.ecarrara.yabaking.steps.presentation.listing;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.core.presentation.LoadDataPresenter;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;
import br.com.ecarrara.yabaking.steps.presentation.listing.StepsListFragment;

class StepsListPresenter extends LoadDataPresenter<StepsListFragment, List<Step>> {

    @Inject
    StepsListPresenter() { }

    @Override
    protected void loadData(List<Step> inputData) {
        List<String> formattedSteps = Stream.of(inputData)
                .map(Step::shortDescription)
                .collect(Collectors.toList());
        hideLoading();
        hideError();
        this.view.showContent(formattedSteps);
    }

    @Override
    public void destroy() { /* Do Nothing */ }

}
