package com.aspirephile.shared.timming.frametimer;

public interface FrameTimerListener {
	public void onNewFrame();
	public void onEndFrame();
	public void onReset();
}
