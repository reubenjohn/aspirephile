package com.aspirephile.shared.timming.frametimer;

public class FrameIntervalListenerContainer {
	FrameIntervalListener listener;
	int interval;
	public FrameIntervalListenerContainer(FrameIntervalListener frameIntervalListener,int interval){
		listener=frameIntervalListener;
		this.interval=interval;
	}
}
