package net.minecraft.src;

public class YYItemPileSoulSand extends Item
{
	public YYItemPileSoulSand(int itemID) {
		super(itemID);
		
		SetBellowsBlowDistance(1);
		SetFilterableProperties(m_iFilterable_Fine);
		
		setUnlocalizedName("fcItemPileSoulSand");
		
		setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override
	public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player)
	{
		if ( !world.isRemote )
		{
			boolean bHasTarget = false;

			double dTargetXPos = player.posX;
			double dTargetZPos = player.posZ;

			if ( world.provider.dimensionId == 0 )
			{
				ChunkCoordinates coords = world.getSpawnPoint();
				if ( coords != null)
				{
					double dx = player.posX-coords.posX;
					double dz = player.posZ-coords.posZ;
					if(dx*dx+dz*dz < 40000)
					{
						dTargetXPos = coords.posX;
						dTargetZPos = coords.posZ;
					
						bHasTarget = true;
					}
				}
			}

			FCEntitySoulSand sandEntity = (FCEntitySoulSand) EntityList.createEntityOfType(FCEntitySoulSand.class, world, player.posX, player.posY + 2.0D - (double)player.yOffset, player.posZ );

			sandEntity.MoveTowards( dTargetXPos, dTargetZPos );

			world.spawnEntityInWorld( sandEntity );
		
			if ( bHasTarget )
			{
				world.playAuxSFX( FCBetterThanWolves.m_iGhastMoanSoundAuxFXID, 
						(int)Math.round( sandEntity.posX ), (int)Math.round( sandEntity.posY ), (int)Math.round( sandEntity.posZ), 0 );
			}

			if (!player.capabilities.isCreativeMode)
			{
				--stack.stackSize;
			}
		}
	
		return stack;
	}
}