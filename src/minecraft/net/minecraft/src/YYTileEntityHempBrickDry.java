package net.minecraft.src;

public class YYTileEntityHempBrickDry extends FCTileEntityUnfiredBrick
{
	
	@Override
	public void UpdateCooking()
	{
		boolean bNewCooking;
		int iBlockMaxNaturalLight = worldObj.GetBlockNaturalLightValueMaximum( xCoord, yCoord, zCoord );
		int iBlockCurrentNaturalLight = iBlockMaxNaturalLight - worldObj.skylightSubtracted;
		
		bNewCooking = iBlockCurrentNaturalLight >= 15;
		
		int iBlockAboveID = worldObj.getBlockId( xCoord, yCoord + 1, zCoord );
		Block blockAbove = Block.blocksList[iBlockAboveID];
		
		if ( blockAbove != null && blockAbove.IsGroundCover( ) )
		{
			bNewCooking = false;
		}
		
		if ( bNewCooking != m_bIsCooking )
		{			
			m_bIsCooking = bNewCooking;
		
			worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
		}
		
		YYBlockHempBrickDry brickBlock = YYStuffAndThings.yyBlockHempBrickDry;
		
		if ( m_bIsCooking )
		{
			
			m_iCookCounter -= m_iRainCookDecay;
			
			if ( m_iCookCounter < 0 )
			{
				m_iCookCounter = 0;
			}
		
			
		}
		else
		{
			if ( IsRainingOnBrick( worldObj, xCoord, yCoord, zCoord ) )
			{
				m_iCookCounter++;
				
				if ( m_iCookCounter >= m_iTimeToCook )
				{
					brickBlock.OnFinishedCooking( worldObj, xCoord, yCoord, zCoord );
					
					return;
				}
			}
		}
		
		m_bIsCooking = IsRainingOnBrick(worldObj, xCoord, yCoord, zCoord);
		
		int iDisplayedCookLevel = brickBlock.GetCookLevel( worldObj, xCoord, yCoord, zCoord );
		int iCurrentCookLevel = ComputeCookLevel();;
		
		if ( iDisplayedCookLevel != iCurrentCookLevel )
		{
			brickBlock.SetCookLevel( worldObj, xCoord, yCoord, zCoord, iCurrentCookLevel );
		}
	}

	@Override
	protected int ComputeCookLevel()
	{
		int iCookLevel= (int)( ( (1.0F - (float)m_iCookCounter / (float)m_iTimeToCook )) * 7F ) + 1;
		
		return MathHelper.clamp_int( iCookLevel, 0, 7 );
	}
	
	
	
}
