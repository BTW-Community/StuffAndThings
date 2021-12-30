package net.minecraft.src;

public class YYTileEntityTorchSparkling extends FCTileEntityTorchFinite
{
	static public final int m_iMaxBurnTime = FCTileEntityTorchFinite.m_iMaxBurnTime*10;
	static public final int m_iSputterTime = FCTileEntityTorchFinite.m_iSputterTime*10;
	
	public YYTileEntityTorchSparkling()
	{
		super();
		m_iBurnTimeCountdown = m_iMaxBurnTime;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();   

		if ( !worldObj.isRemote )
		{
			m_iBurnTimeCountdown--;
			
			if ( m_iBurnTimeCountdown <= 0 || ( worldObj.rand.nextFloat() <= m_fChanceOfGoingOutFromRain && IsRainingOnTorch() ) )
			{				
				// wait until all chunks in vicinity are loaded to avoid lighting glitches
				
				if ( worldObj.doChunksNearChunkExist( xCoord, yCoord, zCoord, 32 ) )
				{
					ExtinguishTorch();
				}
				else
				{
					// ensure it goes out once fully loaded
					
					m_iBurnTimeCountdown = 0;
				}				
			}
			else 
			{
				if ( m_iBurnTimeCountdown < m_iSputterTime )
				{
					int iMetadata = worldObj.getBlockMetadata( xCoord, yCoord, zCoord );
					
					if ( !FCBlockTorchFiniteBurning.GetIsSputtering( iMetadata ) )
					{
						FCBlockTorchFiniteBurning block = (FCBlockTorchFiniteBurning)(Block.blocksList[worldObj.getBlockId( xCoord, yCoord, zCoord )]);
						
						block.SetIsSputtering( worldObj, xCoord, yCoord, zCoord, true );
					}
				}
				
				if ( worldObj.rand.nextFloat() <= m_fChanceOfFireSpread )
				{
					FCBlockFire.CheckForFireSpreadAndDestructionToOneBlockLocation( worldObj, xCoord, yCoord + 1, zCoord );
				}
			}
		}
		else
		{
			int iMetadata = worldObj.getBlockMetadata( xCoord, yCoord, zCoord );
			
			if ( FCBlockTorchFiniteBurning.GetIsSputtering( iMetadata ) )
			{ 
				Sputter();
			}
		}
	}
	
}
