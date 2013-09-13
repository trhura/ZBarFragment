package com.trhura.android.zbar.examples;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * This view is overlaid on top of the camera preview.
 */

public final class OverlayRectangleView extends View {
    private final Paint paint;

    // This constructor is used when the class is built from an XML resource.
    public OverlayRectangleView (Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setARGB(88, 255, 255, 255);
        paint.setStyle(Paint.Style.FILL);
        bringToFront();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int left = this.getLeft();
        int right = this.getRight();
        int top = this.getTop();
        int bottom = this.getBottom();
        int THICKNESS = 100;

        canvas.drawRect(left, top, left+THICKNESS, bottom, paint); // left
        canvas.drawRect(right-THICKNESS, top, right, bottom, paint); // right
        canvas.drawRect(left+THICKNESS, top, right-THICKNESS, top+THICKNESS, paint); //top
        canvas.drawRect(left+THICKNESS, bottom-THICKNESS, right-THICKNESS, bottom, paint); //bottom
        //canvas.drawRect(THICKNESS, 0, width-THICKNESS, THICKNESS, paint); // top
        //canvas.drawRect(THICKNESS, height-THICKNESS, width-THICKNESS, height, paint); //down
        bringToFront();
    }
}
