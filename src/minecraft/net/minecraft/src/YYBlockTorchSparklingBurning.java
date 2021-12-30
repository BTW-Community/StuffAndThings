package net.minecraft.src;

public class YYBlockTorchSparklingBurning extends FCBlockTorchFiniteBurning
{
	protected YYBlockTorchSparklingBurning(int id)
	{
		super(id);
		setUnlocalizedName("yyBlockTorchSparklingBurning");
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new YYTileEntityTorchSparkling();
	}
	
	@Override
	public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
	{
		super.breakBlock( world, i, j, k, iBlockID, iMetadata );
		
		if ( !world.isRemote )
		{
			YYTileEntityTorchSparkling tileEntity = (YYTileEntityTorchSparkling)world.getBlockTileEntity( i, j, k );

			if ( tileEntity != null )
			{
				int iBurnCountdown = tileEntity.m_iBurnTimeCountdown;
				
				if ( iBurnCountdown > 0 && !m_bIsBeingCrushed )
				{
					int iNewItemDamage = (int)( YYItemBlockTorchSparklingBurning.m_fDamageToBurnTimeRatio * 
						(float)( YYTileEntityTorchSparkling.m_iMaxBurnTime - tileEntity.m_iBurnTimeCountdown ) ); 
					
					// the below has a minimum of 1 damage to ensure damage bar is initially displayed
					iNewItemDamage = MathHelper.clamp_int( iNewItemDamage, 1, YYItemBlockTorchSparklingBurning.m_iMaxDamage - 1 ); 
					
					ItemStack stack = new ItemStack( blockID, 1, iNewItemDamage );
					
					long iExpiryTime = FCUtilsWorld.GetOverworldTimeServerOnly() + (long)iBurnCountdown;
					
					stack.setTagCompound( new NBTTagCompound() );
					stack.getTagCompound().setLong( "outTime", iExpiryTime);
		
					dropBlockAsItem_do( world, i, j, k, stack );
				}
			}
		}
		
		m_bIsBeingCrushed = false;
		
		world.removeBlockTileEntity(i, j, k);
	}
	
	@Override
	public void onBlockPlacedBy( World world, int i, int j, int k, EntityLiving entity, ItemStack stack )
	{
		TileEntity tileEntity = world.getBlockTileEntity( i, j, k );

		if ( tileEntity != null && tileEntity instanceof YYTileEntityTorchSparkling )
		{
			if ( stack.hasTagCompound() && stack.getTagCompound().hasKey( "outTime" ) )
			{
				long lExpiryTime = stack.getTagCompound().getLong( "outTime" );
				
				int iCountDown = (int)( lExpiryTime - FCUtilsWorld.GetOverworldTimeServerOnly() );
				
				if ( iCountDown < 0 || iCountDown > YYTileEntityTorchSparkling.m_iMaxBurnTime )
				{
					iCountDown = YYTileEntityTorchSparkling.m_iMaxBurnTime;
				}
				
				((YYTileEntityTorchSparkling)tileEntity).m_iBurnTimeCountdown = iCountDown;
				
				if ( iCountDown < YYTileEntityTorchSparkling.m_iSputterTime )
				{
					SetIsSputtering( world, i, j, k, true );
				}
			}
		}
	}
}
