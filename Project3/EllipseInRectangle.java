//Jacob Darabaris
//2/20/18
//CS 290


package CH.ifa.draw.figures;
import java.awt.*;

public class EllipselnRectangleFigure extends EllipseFigure
{
	public EllipselnRectangleFigure()
	{
		super(new Point(0,0), new Point(0,0));
	}

	public EllipselnRectangleFigure(Point origin, Point corner)
	{
		super(origin, corner);
	}

	//Use override because parent classes have the same name
	@Override public void drawBackground(Graphics g) 
	{ 
		Rectangle r = displayBox();
		super.drawBackground(g); //use parent method 
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	@Override public void drawFrame(Graphics g) 
	{ 
		Rectangle r = displayBox();
		g.drawOval(r.x, r.y, r.width-1, r.height-1); // Draws the Ellipse
		g.drawRect(r.x, r.y, r.width, r.height); //Draws the rectangle
	}
	

	// CODE FROM JavaDrawApp.java
	// tool= new UndoableTool(new CreationTool(this, new EllipselnRectangleFigure()));
	// palette.add(createToolButton(IMAGES + "RECT", "EclipselnRectangle Tool",	tool));

}
