package net.minecraft.src;

public class YYItemBlockTorchSparklingIdle extends FCItemBlockTorchIdle
{
	public YYItemBlockTorchSparklingIdle(int id)
	{
		super(id);
		setUnlocalizedName("yyBlockTorchLongTermIdle");
	}
	
	@Override
	protected ItemStack OnRightClickOnIgniter( ItemStack stack, World world, EntityPlayer player )
	{
		int i = MathHelper.floor_double(player.posX);
		int j = MathHelper.floor_double(player.boundingBox.minY);
		int k = MathHelper.floor_double(player.posZ);
		
		player.playSound( "mob.ghast.fireball", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F );
		
		ItemStack newStack = new ItemStack( YYStuffAndThings.yyBlockTorchSparklingBurning, 1, 0 );
		
		long iExpiryTime = FCUtilsWorld.GetOverworldTimeServerOnly() + (long)YYTileEntityTorchSparkling.m_iMaxBurnTime;
		
		newStack.setTagCompound( new NBTTagCompound() );
		newStack.getTagCompound().setLong( "outTime", iExpiryTime);

		stack.stackSize--;
		
		if ( stack.stackSize <= 0 )
		{
			return newStack; 
		}
		
		FCUtilsItem.GivePlayerStackOrEject( player, newStack, i, j, k );
		
		return stack;
	}
}
