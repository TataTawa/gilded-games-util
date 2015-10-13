package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.rect.RectGetter;

public class TextElement extends GuiFrame
{
	
	private final Text text;

	private final boolean centered;

	public TextElement(Text text, Pos2D pos, boolean centered)
	{
		super(Dim2D.build().pos(pos).scale(text.scale).centerX(centered).flush());

		this.text = text;
		this.centered = centered;
		
		this.dim().mod().addModifier(new RectGetter<TextElement>(this)
		{

			@Override
			public Rect assembleRect()
			{
				return Dim2D.build().width(TextElement.this.text.scaledWidth()).height(TextElement.this.text.scaledHeight()).flush();
			}

			@Override
			public boolean shouldReassemble()
			{
				return true;
			}
			
		}, ModifierType.X, ModifierType.AREA).flush();
	}
	
	public void setText(String text)
	{
		this.text.text = text;
	}
	
	public String getText()
	{
		return this.text.text;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		graphics.drawText(this.text.text, this.dim(), this.text.drawingData);
	}

}
