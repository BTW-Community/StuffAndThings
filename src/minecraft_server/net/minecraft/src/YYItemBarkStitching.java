package net.minecraft.src;

public class YYItemBarkStitching extends FCItemCraftingProgressive
{
	
	public static final int maxDamage = 600;
	
	public YYItemBarkStitching(int itemID)
	{
		super(itemID);
		
		SetBuoyant();
		SetBellowsBlowDistance(2);
		SetIncineratedInCrucible();
		SetFurnaceBurnTime(75);
		SetFilterableProperties(Item.m_iFilterable_Thin);
		
		setUnlocalizedName("yyItemBarkStitching");
	}
	
	@Override
	protected void PlayCraftingFX( ItemStack stack, World world, EntityPlayer player )
	{
		player.playSound( "step.wood", 
			0.25F + 0.25F * (float)world.rand.nextInt( 2 ), 
			( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
	}
	
	@Override
	public void onCreated( ItemStack stack, World world, EntityPlayer player ) 
	{
		if ( player.m_iTimesCraftedThisTick == 0 && world.isRemote )
		{
			player.playSound( "step.wood", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );
		}
		
		super.onCreated( stack, world, player );
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
		return new ItemStack(YYStuffAndThings.yyItemStitchedBark);
	}
	
	protected int GetProgressiveCraftingMaxDamage()
	{
		return maxDamage;
	}
}
