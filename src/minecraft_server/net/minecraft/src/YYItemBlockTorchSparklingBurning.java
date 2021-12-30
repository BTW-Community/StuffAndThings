package net.minecraft.src;

public class YYItemBlockTorchSparklingBurning extends FCItemBlockTorchFiniteBurning
{
	static public final float m_fDamageToBurnTimeRatio = (float)m_iMaxDamage / (float)YYTileEntityTorchSparkling.m_iMaxBurnTime; 
	static public final int m_iSputterDamage = m_iMaxDamage - (int)( m_fDamageToBurnTimeRatio * (float)YYTileEntityTorchSparkling.m_iSputterTime );
	
	public YYItemBlockTorchSparklingBurning(int id)
	{
		super(id);
		setUnlocalizedName("yyBlockTorchSparklingBurning");
	}
	
	@Override
	public void onUpdate( ItemStack stack, World world, EntityPlayer entity, int iInventorySlot, boolean bIsHandHeldItem )
	{
		if ( !world.isRemote )
		{
			if ( stack.stackSize > 0 && stack.hasTagCompound() && stack.getTagCompound().hasKey( "outTime" ) )
			{
				long lExpiryTime = stack.getTagCompound().getLong( "outTime" );
				
				int iCountdown = (int)( lExpiryTime - FCUtilsWorld.GetOverworldTimeServerOnly() );
				
				if ( iCountdown <= 0 || iCountdown > YYTileEntityTorchSparkling.m_iMaxBurnTime )
				{
					// extinguish the torch
					
					int iFXI = MathHelper.floor_double( entity.posX );
					int iFXJ = MathHelper.floor_double( entity.posY ) + 1;
					int iFXK = MathHelper.floor_double( entity.posZ );
					
					world.playAuxSFX( 1004, iFXI, iFXJ, iFXK, 0 ); // fizz sound fx
					
					stack.stackSize--;       		        		
					
					if ( stack.stackSize <= 0 )
					{
						entity.inventory.mainInventory[iInventorySlot] = null;
					}        		
				}
				else if ( ( ( entity.isInWater() && entity.isInsideOfMaterial(Material.water) ) || 
					( entity.IsBeingRainedOn() && entity.worldObj.rand.nextFloat() <= m_fChanceOfGoingOutFromRain ) ) )
				{
					int iFXI = MathHelper.floor_double( entity.posX );
					int iFXJ = MathHelper.floor_double( entity.posY ) + 1;
					int iFXK = MathHelper.floor_double( entity.posZ );
					
					world.playAuxSFX( 1004, iFXI, iFXJ, iFXK, 0 ); // fizz sound fx
					
					stack.stackSize--;       		        		
					
					if ( stack.stackSize <= 0 )
					{
						entity.inventory.mainInventory[iInventorySlot] = null;
					}        		
				}
				else
				{
					int iNewItemDamage = (int)( m_fDamageToBurnTimeRatio * (float)( YYTileEntityTorchSparkling.m_iMaxBurnTime - iCountdown ) ); 
					
					// the below has a minimum of 1 damage to ensure damage bar is initially displayed
					iNewItemDamage = MathHelper.clamp_int( iNewItemDamage, 1, m_iMaxDamage - 1 ); 
					
					if ( iNewItemDamage != stack.getItemDamage() )
					{
						stack.setItemDamage( iNewItemDamage );
					}
				}
			}
		}
	}
}
