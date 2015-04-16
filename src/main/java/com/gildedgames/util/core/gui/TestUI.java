package com.gildedgames.util.core.gui;

import com.gildedgames.util.core.gui.util.UIFactoryUtil;
import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.UIButtonList;
import com.gildedgames.util.ui.util.UIScrollBar;
import com.gildedgames.util.ui.util.decorators.UIScrollable;
import com.gildedgames.util.ui.util.factory.TestButtonFactory;
import com.gildedgames.util.ui.util.transform.ViewPositionerButton;

public class TestUI extends UIBasicAbstract
{

	public TestUI(UIBasicAbstract parent)
	{
		super(parent, new Dimensions2D());
	}

	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);

		UIFactoryUtil factory = new UIFactoryUtil();

		UIButtonList list = new UIButtonList(new Position2D(), 60, new ViewPositionerButton(), new TestButtonFactory());
		
		Dimensions2D dim = new Dimensions2D().setArea(60, 200);
		
		UIScrollBar scrollBar = factory.createScrollBar(new Position2D(), 200, dim.clone(), false);

		UIScrollable scrollable = new UIScrollable(dim, list, scrollBar);
		
		scrollable.getDimensions().setPos(new Position2D(20, 20));
		
		container.add(scrollable);
		
		//scrollable.getDimensions().setScale(0.5F).setPos(new Position2D(50, 50));
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		
	}

}
