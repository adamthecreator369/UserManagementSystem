/* Created by Adam Jost on 07/14/2021 */

package main.java.layouts;

import java.awt.Frame;
 
public abstract class Layout {
	
	/** Instantiates all layout components */
	public abstract void instantiateComponents();
	
	/** Sets all layout's component's settings */
	public abstract void componentSettings();
	
	/** Sets the font of all components containing text. */
	public abstract void setFont();

	/** Adds all LoginLayout components to a Frame */
	public abstract void addComponents(Frame frame);

	/** Shows the layout by setting all component's visibility to true. */
	public abstract void show();
	
	/** Hides the layout by setting all component's visibility to false. */
	public abstract void hide();
	
}


	