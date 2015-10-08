package com.gildedgames.util.ui.data;

import java.util.List;

import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;

public interface Dim2DHolder
{
	
	Dim2D getDim();
	
	void setDim(Dim2D dim);
	
	Dim2DModifier modDim();
	
	Dim2DBuilder copyDim();
	
	List<Dim2DListener> dimListeners();
	
	void addDimListener(Dim2DListener listener);
	
	void removeDimListener(Dim2DListener listener);
	
}