package de.gg.pi.tv;

import java.awt.Dimension;
import java.awt.Image;

public interface IActivity {

	
	
	public void call();
	
	
	public void close();
	
	
	public String getName();
	
	
	public Image getIcon();
	
	
	public void resize(Dimension size);
}
