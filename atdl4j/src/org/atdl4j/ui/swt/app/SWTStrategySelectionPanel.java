package org.atdl4j.ui.swt.app;


import java.util.List;

import org.apache.log4j.Logger;
import org.atdl4j.config.Atdl4jConfig;
import org.atdl4j.data.Atdl4jHelper;
import org.atdl4j.fixatdl.core.StrategyT;
import org.atdl4j.ui.app.AbstractStrategySelectionPanel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * Represents the SWT-specific available strategy choices GUI component.
 * 
 * @author Scott Atwell
 * @version 1.0, Mar 1, 2010
 */
public class SWTStrategySelectionPanel 
	extends AbstractStrategySelectionPanel
{
	private final Logger logger = Logger.getLogger(SWTStrategySelectionPanel.class);
	
	private Combo strategiesDropDown;
	private List<StrategyT> strategiesList; // -- kept in sync index-for-index with strategiesDropDown --
	
	public Object buildStrategySelectionPanel(Object parentOrShell, Atdl4jConfig atdl4jConfig)
	{
		return buildStrategySelectionPanel( (Composite) parentOrShell, atdl4jConfig );
	}
	
	public Composite buildStrategySelectionPanel(Composite aParentComposite, Atdl4jConfig atdl4jConfig)
	{
		setAtdl4jConfig( atdl4jConfig );
		
		// Strategy selector dropdown
		Composite dropdownComposite = new Composite(aParentComposite, SWT.NONE);
		GridLayout dropdownLayout = new GridLayout(2, false);
		dropdownComposite.setLayout(dropdownLayout);
		dropdownComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// label
		Label strategiesDropDownLabel = new Label(dropdownComposite, SWT.NONE);
		strategiesDropDownLabel.setText("Strategy");
		// dropDownList
		strategiesDropDown = new Combo(dropdownComposite, SWT.READ_ONLY | SWT.BORDER);
		strategiesDropDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		if ( ( atdl4jConfig != null ) && ( atdl4jConfig.getStrategyDropDownItemDepth() != null ) )
		{
			strategiesDropDown.setVisibleItemCount( atdl4jConfig.getStrategyDropDownItemDepth().intValue() );
		}
		// tooltip
		strategiesDropDown.setToolTipText("Select a Strategy");
		// action listener
		strategiesDropDown.addSelectionListener(new SelectionAdapter() 
			{
				@Override
				public void widgetSelected(SelectionEvent event) 
				{
					int index = strategiesDropDown.getSelectionIndex();
logger.debug( "strategiesDropDown.widgetSelected.  strategiesDropDown.getSelectionIndex(): " + index );					
					selectDropDownStrategy( index );
				}
			} 
		);
	
		return dropdownComposite;
	}

	
	public void loadStrategyList( List<StrategyT> aStrategyList )
	{
		// remove all dropdown items
		strategiesDropDown.removeAll();
/*** 4/16/2010 Scott Atwell
		List<String> tempStrategyUiRepOrNameList = getStrategyUiRepOrNameList( aStrategyList );
		
		if ( tempStrategyUiRepOrNameList == null )
		{
			return;
		}
		
		for (String tempStrategy : tempStrategyUiRepOrNameList) 
		{
			// create dropdown item for strategy
			strategiesDropDown.add( tempStrategy );
		}
***/
		setStrategiesList( aStrategyList );

		if ( getStrategiesList() == null )
		{
			return;
		}

		for (StrategyT tempStrategy : getStrategiesList()) 
		{
			logger.debug( "loadStrategyList() [" + strategiesDropDown.getItemCount() + "] strategiesDropDown.add: " + Atdl4jHelper.getStrategyUiRepOrName( tempStrategy ) );
			
			// create dropdown item for strategy
			strategiesDropDown.add( Atdl4jHelper.getStrategyUiRepOrName( tempStrategy ) );
		}
		
		
//		if (strategiesDropDown.getItemCount() > 0)
//		{
// 4/16/2010 Scott Atwell - Composite panel caller does this			strategiesDropDown.select( 0 );
//		}
	}


	public void selectDropDownStrategy(int index) 
	{
		logger.debug( "selectDropDownStrategy() index: " + index );
		
		if ( getStrategiesList().size() != strategiesDropDown.getItemCount() )
		{
			throw new IllegalStateException( "UNEXPECTED ERROR: getStrategiesList().size(): " + getStrategiesList().size() + " does NOT MATCH strategiesDropDown.getItemCount(): " + strategiesDropDown.getItemCount() );
		}
		
		strategiesDropDown.select( index );
		
/*** 4/16/2010 Scott Atwell		
		if ( (getAtdl4jConfig() != null ) && (getAtdl4jConfig().getStrategies() != null) )
		{
			String tempSelectedDropDownName = strategiesDropDown.getItem( index );
			getAtdl4jConfig().setSelectedStrategy( null ); 
			for ( StrategyT tempStrategy : getAtdl4jConfig().getStrategies().getStrategy() )
			{
				if ( ( ( tempStrategy.getUiRep() != null ) && ( tempStrategy.getUiRep().equals( tempSelectedDropDownName ) ) ) ||
					  ( ( tempStrategy.getUiRep() == null ) && ( tempStrategy.getName().equals( tempSelectedDropDownName ) ) ) )
				{
					getAtdl4jConfig().setSelectedStrategy( tempStrategy );
					fireStrategySelectedEvent( tempStrategy, index );
					break;
				}
			}
		}
***/		
		StrategyT tempStrategy = getStrategiesList().get( index );
		
		if ( ! strategiesDropDown.getItem( index ).equals( Atdl4jHelper.getStrategyUiRepOrName( tempStrategy ) ) )
		{
			throw new IllegalStateException( "UNEXPECTED ERROR: strategiesDropDown.getItem(" + index + "): " + strategiesDropDown.getItem( index ) + " DID NOT MATCH tempStrategy: " + Atdl4jHelper.getStrategyUiRepOrName( tempStrategy ) );
		}
		
		getAtdl4jConfig().setSelectedStrategy( tempStrategy );
// 4/16/2010 Scott Atwell		fireStrategySelectedEvent( tempStrategy, index );
		fireStrategySelectedEvent( tempStrategy );
	}

	
// 4/16/2010 Scott Atwell	public void selectDropDownStrategy(String strategyName) 
	public void selectDropDownStrategyByStrategyName(String aStrategyName) 
	{
/*** 4/16/2010 Scott Atwell		
		for (int i = 0; i < strategiesDropDown.getItemCount(); i++) 
		{
			if ( strategyName.equals( strategiesDropDown.getItem( i ) ) ) 
			{
				selectDropDownStrategy( i );
				return;
			}
		}
***/		
		logger.debug( "selectDropDownStrategyByStrategyName() aStrategyName: " + aStrategyName );
		
		if ( getStrategiesList().size() != strategiesDropDown.getItemCount() )
		{
			throw new IllegalStateException( "UNEXPECTED ERROR: getStrategiesList().size(): " + getStrategiesList().size() + " does NOT MATCH strategiesDropDown.getItemCount(): " + strategiesDropDown.getItemCount() );
		}
			
		for (int i = 0; i < getStrategiesList().size(); i++) 
		{
			StrategyT tempStrategy = getStrategiesList().get( i );
			
			if ( aStrategyName.equals( tempStrategy.getName() ) )
			{
				logger.debug( "selectDropDownStrategyByStrategyName() invoking selectDropDownStrategy( " + i + " )" );
				selectDropDownStrategy( i );
			}
		}
	}

// 4/16/2010 Scott Atwell added	
	public void selectDropDownStrategyByStrategyWireValue( String aStrategyWireValue ) 
	{
		logger.debug( "selectDropDownStrategyByStrategyWireValue() aStrategyWireValue: " + aStrategyWireValue );
		
		if ( getStrategiesList().size() != strategiesDropDown.getItemCount() )
		{
			throw new IllegalStateException( "UNEXPECTED ERROR: getStrategiesList().size(): " + getStrategiesList().size() + " does NOT MATCH strategiesDropDown.getItemCount(): " + strategiesDropDown.getItemCount() );
		}
			
		for (int i = 0; i < getStrategiesList().size(); i++) 
		{
			StrategyT tempStrategy = getStrategiesList().get( i );
			
			if ( aStrategyWireValue.equals( tempStrategy.getWireValue() ) )
			{
				logger.debug( "selectDropDownStrategyByStrategyWireValue() invoking selectDropDownStrategy( " + i + " )" );
				selectDropDownStrategy( i );
			}
		}
	}
	
	public void selectFirstDropDownStrategy()
	{
		if ( ( strategiesDropDown != null ) && 
			  ( strategiesDropDown.getItemCount() > 0 ) )
		{
			strategiesDropDown.deselectAll();
			logger.debug( "selectFirstDropDownStrategy() invoking selectDropDownStrategy( 0 )" );
			selectDropDownStrategy( 0 );
		}
	}

	/**
	 * @param strategiesList the strategiesList to set
	 */
	protected void setStrategiesList(List<StrategyT> strategiesList)
	{
		this.strategiesList = strategiesList;
	}

	/**
	 * @return the strategiesList
	 */
	protected List<StrategyT> getStrategiesList()
	{
		return strategiesList;
	}
}
