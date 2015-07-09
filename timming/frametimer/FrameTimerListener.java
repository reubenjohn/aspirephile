package com.aspirephile.shared.timming.frametimer;

public interface FrameTimerListener {
	void onNewFrame();
	void onEndFrame();
	void onReset();
}
