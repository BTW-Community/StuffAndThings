package net.minecraft.src;

public class YYItemBarkStitching extends FCItemCraftingProgressive {

	public static final int m_iBarkStitchingMaxDamage = 600;
	public YYItemBarkStitching(int var1){
		super(var1);
		this.SetBuoyant();
		this.setUnlocalizedName("yyItemBarkStitching");
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
		return new ItemStack(YYBaitMod.yyItemStitchedBark);
	}
	
	protected int GetProgressiveCraftingMaxDamage()
	{
		return m_iBarkStitchingMaxDamage;
	}
}
