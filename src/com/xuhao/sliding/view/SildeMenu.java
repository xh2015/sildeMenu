package com.xuhao.sliding.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SildeMenu extends FrameLayout {
	private View menuView, mainView;
	private int menuWidth = 0;
	private Scroller scroller;

	public SildeMenu(Context context) {
		super(context);
		init();
	}

	public SildeMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		scroller = new Scroller(getContext());
	}

	/**
	 * 当1级的子view全部加载完调用，可以用初始化子view的引用 注意，这里无法获取子view的宽高
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		menuView = getChildAt(0);
		mainView = getChildAt(1);
		menuWidth = menuView.getLayoutParams().width;
		// Log.e("Main", menuWidth + "");

	}

	/**
	 * widthMeasureSpec和heightMeasureSpec是系统测量SlideMenu时传入的参数
	 * 这2个参数测kiang出来的宽高能让slidemenu充满船体，其实是正好等于屏幕的宽高
	 */

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// int measureSpec = MeasureSpec.makeMeasureSpec(menuWidth,
	// MeasureSpec.EXACTLY);
	// // Log.e("Main", measureSpec + "measureSpec");
	// // 测量所有子view的宽高
	// // 通过getLayoutParams方法可以获取到布局文件中指定宽高
	// menuView.measure(measureSpec, heightMeasureSpec);
	// // 直接使用slidemenu的测量参数，以为他的狂傲就是充满父窗体
	// mainView.measure(widthMeasureSpec, heightMeasureSpec);
	// }

	// dispatchTouchEvent(MotionEvent ev)

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) (ev.getX() - downX);
			
			if(Math.abs(deltaX)>8){
				//如果左右滑动大于8就让slidemenu处理
				return true;
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * l:当前子view的左边在父view的坐标系中的x坐标 t:当前子view的顶边在父view坐标系中的y坐标
	 */

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		menuView.layout(-menuWidth, 0, 0, menuView.getMeasuredHeight());
		mainView.layout(0, 0, r, b);
	}

	private int downX;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();
			int detalX = moveX - downX;
			int newScrollX = getScrollX() - detalX;
			if (newScrollX < -menuWidth)
				newScrollX = -menuWidth;
			if (newScrollX > 0)
				newScrollX = 0;
			scrollTo(newScrollX, 0);
			downX = moveX;
			break;
		case MotionEvent.ACTION_UP:
			// ScrollAnimation animation;
			// if (getScrollX() > -menuWidth / 2) {
			// // 关闭菜单
			// animation = new ScrollAnimation(this, 0);
			// // scrollTo(0, 0);
			// } else {
			// // 打开菜单
			// animation = new ScrollAnimation(this, -menuWidth);
			// // scrollTo(-menuWidth, 0);
			// }
			// startAnimation(animation);
			if (getScrollX() > -menuWidth / 2) {
				// 关闭菜单
				closeMenu();
			} else {
				// 打开菜单
				openMenu();
			}
			break;
		}
		return true;
	}

	/**
	 * 打开惨淡
	 */
	private void openMenu() {
		scroller.startScroll(getScrollX(), 0, -menuWidth - getScrollX(), 0, 400);
		invalidate();
	}

	/**
	 * 关闭菜单
	 */
	private void closeMenu() {
		scroller.startScroll(getScrollX(), 0, 0 - getScrollX(), 0, 400);
		invalidate();
	}

	/**
	 * Scroller不主动去掉用此方法 invalidate（）可以调用这个方法 invalidate->draw->computeScroll
	 */
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (scroller.computeScrollOffset()) {// 返回true表示动画没有结束
			scrollTo(scroller.getCurrX(), 0);
			invalidate();
		}
	}
	
	public void switchMenu(){
		if(getScrollX()==0){
			//需要打开
			openMenu();
		}else{
			//需要关闭
			closeMenu();
		}
	}
}
