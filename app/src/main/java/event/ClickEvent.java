package event;

import android.view.View;

import com.yecy.bus.Event;

public class ClickEvent extends Event<ClickEvent> {
    public View view;

    public ClickEvent(View view) {
        this.view = view;
    }

    @Override
    public ClickEvent initEvent() {
        return this;
    }
}
