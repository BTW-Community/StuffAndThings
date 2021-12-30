package net.minecraft.src;

import java.util.Iterator;

public class YYTileEntityLargeBasket extends FCTileEntityHamper
{
	public static final int m_iInventorySize = 12;
	public static final String m_sUnlocalizedName = "container.yyLargeBasket";
	
	
	public YYTileEntityLargeBasket()
	{
		super();
		this.m_blockBasket = YYStuffAndThings.yyBlockLargeBasket;
	}
	
	@Override
	public String getInvName()
	{
		return "container.yyLargeBasket";
	}
	
	@Override
	public int getSizeInventory()
	{
		return m_iInventorySize;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if ( !worldObj.isRemote )
		{
			if ( m_iNumUsingPlayers > 0 && !m_blockBasket.GetIsOpen( worldObj, xCoord, yCoord, zCoord ) )
			{
				worldObj.playSoundEffect( xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D,
					"step.gravel", 0.25F + ( worldObj.rand.nextFloat() * 0.1F ), 
					0.5F + ( worldObj.rand.nextFloat() * 0.1F ) );

				m_blockBasket.SetIsOpen( worldObj, xCoord, yCoord, zCoord, true );
				
				if ( m_bClosing )
				{
					m_bClosing = false;
					
					worldObj.addBlockEvent( xCoord, yCoord, zCoord, getBlockType().blockID, 1, 0 );
				}
			}
			
			m_iFullUpdateCounter++;
			
			if ( ( m_iFullUpdateCounter + xCoord + yCoord + zCoord ) % m_iFullUpdateInterval == 0 )
			{
				if ( m_iNumUsingPlayers != 0 )
				{
					int iOldNumUsing = m_iNumUsingPlayers;
					m_iNumUsingPlayers = 0;
					
					Iterator<EntityPlayer> playerIterator = ( worldObj.getEntitiesWithinAABB( EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB( 
						xCoord - m_fMaxKeepOpenRange, yCoord - m_fMaxKeepOpenRange, zCoord - m_fMaxKeepOpenRange, 
						xCoord + 1 + m_fMaxKeepOpenRange, yCoord + 1 + m_fMaxKeepOpenRange, zCoord + 1 + m_fMaxKeepOpenRange ) ) ).
						iterator();

					while ( playerIterator.hasNext() )
					{
						EntityPlayer tempPlayer = playerIterator.next();

						if ( tempPlayer.openContainer instanceof YYContainerLargeBasket )
						{
							if ( ((YYContainerLargeBasket)tempPlayer.openContainer).m_containerInventory == this )
							{
								m_iNumUsingPlayers++;
							}
						}
					}
				}
			}    			
		}
	}
}
