package br.com.ecarrara.yabaking.robot;

import java.util.List;

import br.com.ecarrara.yabaking.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class StepsNavigationRobot {

    public static StepsNavigationRobot getRobot() {
        return new StepsNavigationRobot();
    }

    private StepsNavigationRobot() {
    }

    public StepsNavigationRobot previousStep() {
        onView(withId(R.id.step_details_previous_button)).perform(click());
        return this;
    }

    public StepsNavigationRobot nextStep() {
        onView(withId(R.id.step_details_next_button)).perform(click());
        return this;
    }

    public StepsNavigationRobot checkShortAndLongDescriptionsThroughAllSteps(
            List<String> stepsShortDescriptionList, List<String> stepsLongDescriptionList) {

        for (int stepIndex = 0; stepIndex < stepsShortDescriptionList.size(); stepIndex++) {
            String shortDescription = stepsShortDescriptionList.get(stepIndex);
            String longDescription = stepsLongDescriptionList.get(stepIndex);

            onView(
                    allOf(
                            withId(R.id.step_detail_short_description_text_view),
                            withText(shortDescription)
                    ))
                    .check(matches(isDisplayed()));

            onView(
                    allOf(
                            withId(R.id.step_detail_description_text_view),
                            withText(longDescription)
                    ))
                    .check(matches(isDisplayed()));

            if (stepIndex != stepsShortDescriptionList.size() - 1) {
                nextStep();
            }
        }

        return this;
    }
}
