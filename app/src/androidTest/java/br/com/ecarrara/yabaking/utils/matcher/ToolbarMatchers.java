package br.com.ecarrara.yabaking.utils.matcher;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.is;

public class ToolbarMatchers {

    public static ViewInteraction matchCollapsingToolbarTitle(CharSequence title) {
        return onView(ViewMatchers.isAssignableFrom(CollapsingToolbarLayout.class))
                .check(matches(withCollapsingToolbarTitle(is(title))));
    }

    private static Matcher<Object> withCollapsingToolbarTitle(
            final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, CollapsingToolbarLayout>(CollapsingToolbarLayout.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("with collapsing toolbar title: ");
                textMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(CollapsingToolbarLayout collapsingToolbarLayout) {
                return textMatcher.matches(collapsingToolbarLayout.getTitle());
            }

        };
    }

}
