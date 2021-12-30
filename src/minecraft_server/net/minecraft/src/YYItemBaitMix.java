package net.minecraft.src;

public class YYItemBaitMix extends FCItemCraftingProgressive{
	
	public static final int maxDamage = 75;
	
	public YYItemBaitMix(int id){
		super(id);
		this.SetBuoyant();
		this.setUnlocalizedName("yyItemBaitMix");
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
		FCUtilsItem.GivePlayerStackOrEject(player, new ItemStack(YYStuffAndThings.yyItemBait, 2));
		return new ItemStack(Item.bowlEmpty);
	}
	
	protected int GetProgressiveCraftingMaxDamage()
	{
		return maxDamage;
	}
	
	@Override
	protected void PlayCraftingFX( ItemStack stack, World world, EntityPlayer player )
	{
		player.playSound( "mob.slime.attack", 
			0.03F + 0.015F * (float)world.rand.nextInt( 2 ), 
			( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.05F + 0.15F );
	}
	
	@Override
	public void onCreated( ItemStack stack, World world, EntityPlayer player ) 
	{
		if ( player.m_iTimesCraftedThisTick == 0 && world.isRemote )
		{
			player.playSound( "mob.slime.attack", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );
		}
		
		super.onCreated( stack, world, player );
	}
}
