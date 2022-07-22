// FCMOD

package net.minecraft.src;

public class FCEntityBlaze extends EntityBlaze
{
	public FCEntityBlaze( World world )
	{
		super( world );
	}
	
	@Override
	protected void dropFewItems( boolean bKilledByPlayer, int iLootingModifier )
	{
		// treat as always killed by player to override vanilla behavior of only dropping rods
		// when killed by player
		
		super.dropFewItems( true, iLootingModifier );
	}
	
	@Override
	public void CheckForScrollDrop()
	{    	
		if ( rand.nextInt( 500 ) == 0 )
		{
			ItemStack itemstack = new ItemStack( FCBetterThanWolves.fcItemArcaneScroll, 1, 
				Enchantment.flame.effectId );
			
			entityDropItem( itemstack, 0F );
		}
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		FCUtilsBlockPos lightpos = new FCUtilsBlockPos(MathHelper.floor_double( posX ),
		MathHelper.floor_double(boundingBox.maxY), MathHelper.floor_double( posZ));
		if (worldObj.getBlockId(lightpos.i, lightpos.j, lightpos.k)==0)
		{
			worldObj.setBlock(lightpos.i, lightpos.j, lightpos.k, DLMain.lightsourceinvis.blockID,0 ,2);
		}
	}
}
