package net.minecraft.src;

public class YYItemYarnSpinning extends FCItemCraftingProgressive{
	
	public static final int maxDamage = 600;
	
	public YYItemYarnSpinning(int id)
	{
		super(id);
		setUnlocalizedName("yyItemYarnSpinning");
		SetFilterableProperties(Item.m_iFilterable_Narrow);
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
		FCUtilsItem.GivePlayerStackOrEject(player, new ItemStack(Item.silk));
		return new ItemStack(YYStuffAndThings.yyItemDropSpindle);
	}
	
	protected int GetProgressiveCraftingMaxDamage()
	{
		return maxDamage;
	}
	
	@Override
	protected void PlayCraftingFX( ItemStack stack, World world, EntityPlayer player )
	{
		player.playSound( "step.cloth", 
			0.25F + 0.25F * (float)world.rand.nextInt( 2 ), 
			( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
	}
}
