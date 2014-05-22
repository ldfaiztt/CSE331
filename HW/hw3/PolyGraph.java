package hw3;

import java.awt.*;

/**
 * <b>PolyGraph</b> is part of the graphical calculator that utilizes all of
 * the other classes in package hw3. PolyGraph implements the graphing
 * component.
 *
 * @author Felix Klock, Andreas Hofmann
 * @version 1.0
 */

public final class PolyGraph extends Canvas {
    Color col[] = new Color[4];

    Color zeroline = new Color(0xe0, 0xe0, 0xff);

    CalculatorFrame calcFrame;

    // Serializable classes are supposed to have this
    private static final long serialVersionUID = 24L;

    public PolyGraph(CalculatorFrame cf) {
        super();

        calcFrame = cf;

        col[0] = new Color(0xa0, 0, 0);
        col[1] = new Color(0, 0, 0xFF);
        col[2] = new Color(0, 0x80, 0);
        col[3] = new Color(0, 0, 0);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        int w = getSize().width;
        int h = getSize().height;

        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);

        /*
         * g.setColor(Color.red); String msg= "Click to graph selected
         * variable"; int wid= getFontMetrics(getFont()).stringWidth(msg);
         * g.drawString(msg, (w-wid)/2, h/2);
         */

        // Draw axes and data
        int numIncrements = 100;

        float xMin = Float.parseFloat(calcFrame.jTextField1.getText());
        float xMax = Float.parseFloat(calcFrame.jTextField2.getText());
        float yMin = 0;
        float yMax = 0;
        float[] xValBuffer1 = null;
        float[] yValBuffer1 = null;
        float[] xValBuffer2 = null;
        float[] yValBuffer2 = null;
        float[] xValBuffer3 = null;
        float[] yValBuffer3 = null;
        float[] xValBuffer4 = null;
        float[] yValBuffer4 = null;
        float[] yExtrema;
        String msg;

        if (xMin >= xMax) {
            g.setColor(Color.red);
            msg = "Xmin must be greater than Xmax";
            int wid = getFontMetrics(getFont()).stringWidth(msg);
            g.drawString(msg, (w - wid) / 2, h / 2);
            return;
        }

        // Get RatPoly
        RatPoly currentRatPoly;

        // Now fill in new information base on what's in stack.
        // Note that size of stack must be checked.
        if ((calcFrame.stack != null) && (calcFrame.stack.size() > 0)) {
            currentRatPoly = calcFrame.stack.getNthFromTop(0);
            xValBuffer1 = new float[numIncrements];
            yValBuffer1 = new float[numIncrements];
            yExtrema = new float[2];
        } else {
            g.setColor(Color.red);
            msg = "Stack is empty";
            int wid = getFontMetrics(getFont()).stringWidth(msg);
            g.drawString(msg, (w - wid) / 2, h / 2);
            return;
        }

        updatePlotBuffer(xMin, xMax, numIncrements, xValBuffer1, yValBuffer1,
                yExtrema, currentRatPoly);

        yMin = yExtrema[0];
        yMax = yExtrema[1];

        if (calcFrame.stack.size() > 1) {
            currentRatPoly = calcFrame.stack.getNthFromTop(1);
            xValBuffer2 = new float[numIncrements];
            yValBuffer2 = new float[numIncrements];

            updatePlotBuffer(xMin, xMax, numIncrements, xValBuffer2,
                    yValBuffer2, yExtrema, currentRatPoly);

            if (yExtrema[0] < yMin) {
                yMin = yExtrema[0];
            }

            if (yExtrema[1] > yMax) {
                yMax = yExtrema[1];
            }
        }

        if (calcFrame.stack.size() > 2) {
            currentRatPoly = calcFrame.stack.getNthFromTop(2);
            xValBuffer3 = new float[numIncrements];
            yValBuffer3 = new float[numIncrements];

            updatePlotBuffer(xMin, xMax, numIncrements, xValBuffer3,
                    yValBuffer3, yExtrema, currentRatPoly);

            if (yExtrema[0] < yMin) {
                yMin = yExtrema[0];
            }

            if (yExtrema[1] > yMax) {
                yMax = yExtrema[1];
            }
        }

        if (calcFrame.stack.size() > 3) {
            currentRatPoly = calcFrame.stack.getNthFromTop(3);
            xValBuffer4 = new float[numIncrements];
            yValBuffer4 = new float[numIncrements];

            updatePlotBuffer(xMin, xMax, numIncrements, xValBuffer4,
                    yValBuffer4, yExtrema, currentRatPoly);

            if (yExtrema[0] < yMin) {
                yMin = yExtrema[0];
            }

            if (yExtrema[1] > yMax) {
                yMax = yExtrema[1];
            }
        }

        // At this point, min and max have been computed, and buffers
        // are full. Draw axes, then draw graph line.
        int bord = 32;
        g.setColor(Color.black);
        g.drawLine(bord, h - bord, w - bord, h - bord); // horizontal axis
        g.drawLine(bord, bord, bord, h - bord); // vertical axis

        float gw = w - 2 * bord; // width of graph area inside axes
        float gh = h - 2 * bord; // height of graph area inside axes

        // Draw axis labels.
        msg = Float.toString(xMin);
        g.drawString(msg, bord, h - 8);

        msg = Float.toString(xMax);
        g.drawString(msg, w - bord, h - 8);

        msg = Float.toString(yMin);
        g.drawString(msg, 8, h - bord);

        msg = Float.toString(yMax);
        g.drawString(msg, 8, bord);

        // Draw graph line.
        g.setColor(Color.red);
        drawPlot(xMin, xMax, yMin, yMax, xValBuffer1, yValBuffer1, gw, gh,
                bord, numIncrements, h, g);

        g.setColor(Color.blue);
        if (calcFrame.stack.size() > 1) {
            assert xValBuffer2 != null
                : "@SuppressWarnings(nullness): dependent:  non-null if calcFrame.stack.size() > 1";
            assert yValBuffer2 != null
                : "@SuppressWarnings(nullness): dependent:  non-null if calcFrame.stack.size() > 1";
            drawPlot(xMin, xMax, yMin, yMax, xValBuffer2, yValBuffer2, gw, gh,
                    bord, numIncrements, h, g);
        }

        g.setColor(Color.green);
        if (calcFrame.stack.size() > 2) {
            assert xValBuffer3 != null
                : "@SuppressWarnings(nullness): dependent:  non-null if calcFrame.stack.size() > 2";
            assert yValBuffer3 != null
                : "@SuppressWarnings(nullness): dependent:  non-null if calcFrame.stack.size() > 2";
            drawPlot(xMin, xMax, yMin, yMax, xValBuffer3, yValBuffer3, gw, gh,
                    bord, numIncrements, h, g);
        }

        g.setColor(Color.orange);
        if (calcFrame.stack.size() > 3) {
            assert xValBuffer4 != null
                : "@SuppressWarnings(nullness): dependent:  non-null if calcFrame.stack.size() > 3";
            assert yValBuffer4 != null
                : "@SuppressWarnings(nullness): dependent:  non-null if calcFrame.stack.size() > 3";
            drawPlot(xMin, xMax, yMin, yMax, xValBuffer4, yValBuffer4, gw, gh,
                    bord, numIncrements, h, g);
        }

        // Consider abstracting this better!
    }

    public void updatePlotBuffer(float xMin, float xMax, int numIncrements,
            float xValBuffer[], float yValBuffer[], float yExtrema[],
            RatPoly currentRatPoly) {
        float delta = (xMax - xMin) / numIncrements;
        float currentX = xMin;
        boolean firstTime = true;
        int i;
        float yVal = 0;
        float yMin = 0;
        float yMax = 0;

        for (i = 0; i < numIncrements; ++i) {
            if (currentX < xMax) {
                xValBuffer[i] = currentX;
                yVal = (float) currentRatPoly.eval(currentX);
                yValBuffer[i] = yVal;

                if (firstTime) {
                    firstTime = false;
                    yMin = yVal;
                    yMax = yVal;
                } else {
                    if (yVal < yMin) {
                        yMin = yVal;
                    }

                    if (yVal > yMax) {
                        yMax = yVal;
                    }
                }

                currentX += delta;
            } else {
                xValBuffer[i] = xValBuffer[i - 1];
                yValBuffer[i] = yValBuffer[i - 1];
            }
        }

        yExtrema[0] = yMin;
        yExtrema[1] = yMax;
    }

    public void drawPlot(float xMin, float xMax, float yMin, float yMax,
            float xValBuffer[], float yValBuffer[], float gw, float gh,
            int bord, int numIncrements, int h, Graphics g) {
        float xVal = 0;
        float yVal = 0;
        float previousX = 0;
        float previousY = 0;
        boolean firstTime = true;
        float xRange = xMax - xMin;
        float yRange = yMax - yMin;
        int xPrevScaled = 0;
        int yPrevScaled = 0;
        int xScaled = 0;
        int yScaled = 0;
        int i;
        for (i = 0; i < numIncrements; ++i) {
            if (firstTime) {
                firstTime = false;
                xVal = xValBuffer[i];
                yVal = yValBuffer[i];
                previousX = xVal;
                previousY = yVal;
            } else {
                previousX = xVal;
                previousY = yVal;
                xVal = xValBuffer[i];
                yVal = yValBuffer[i];
            }

            // Now draw a line from previous to current.
            xPrevScaled = Math.round((previousX - xMin) * gw / xRange);
            yPrevScaled = Math.round((previousY - yMin) * gh / yRange);
            xScaled = Math.round((xVal - xMin) * gw / xRange);
            yScaled = Math.round((yVal - yMin) * gh / yRange);

            g.drawLine(bord + xPrevScaled, h - bord - yPrevScaled, bord
                       + xScaled, h - bord - yScaled);
        }
    }

}
