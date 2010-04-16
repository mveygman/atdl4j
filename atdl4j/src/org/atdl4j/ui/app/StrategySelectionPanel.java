/*
 * Created on Feb 7, 2010
 *
 */
package org.atdl4j.ui.app;

import java.util.List;

import org.atdl4j.config.Atdl4jConfig;
import org.atdl4j.fixatdl.core.StrategyT;

/**
 * Represents the available strategy choices GUI component. 
 * 
 * Creation date: (Feb 7, 2010 9:49:04 PM)
 * @author Scott Atwell
 * @version 1.0, Feb 7, 2010
 */
public interface StrategySelectionPanel
{
	public Object buildStrategySelectionPanel(Object parentOrShell, Atdl4jConfig atdl4jConfig);
	
	public void loadStrategyList( List<StrategyT> aStrategyList );

//	4/16/2010 Scott Atwell public void selectDropDownStrategy(String strategyName);
	public void selectDropDownStrategyByStrategyName( String aStrategyName );
	
	public void selectDropDownStrategyByStrategyWireValue( String aStrategyWireValue ); 
	
	public void selectFirstDropDownStrategy();

	public Atdl4jConfig getAtdl4jConfig();
	
	public void addListener(StrategySelectionPanelListener aStrategySelectionPanelListener);
	
	public void removeListener(StrategySelectionPanelListener aStrategySelectionPanelListener);

}
