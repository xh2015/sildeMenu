package com.xuhao.sliding.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ScrollAnimation extends Animation {
	private View view;
	private int startScrollX;
	private int totalValue;
	private int targetScrollX;

	public ScrollAnimation(View view, int targetScrollX) {
		super();
		this.view = view;
		this.targetScrollX = targetScrollX;

		startScrollX = view.getScrollX();

		totalValue = targetScrollX - startScrollX;
		int time = Math.abs(totalValue);
		setDuration(time);
	}

	/**
	 * 在指定的时间内一直执行该方法，一直到动画结束 interpolatedTime：0-1 标识动画执行的进度或者百分比 time：0 - 0.5 -
	 * 0.7 - 1 value：10 - 60 - 80 - 110 当前的值=起始值+总的差值*interpolatedTime
	 */

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		int currentScrollX = (int) (startScrollX + totalValue
				* interpolatedTime);
		view.scrollTo(currentScrollX, 0);
	}
}
