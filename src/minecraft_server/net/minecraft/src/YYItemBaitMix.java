package net.minecraft.src;

public class YYItemBaitMix extends FCItemCraftingProgressive {

	public static final int m_iBaitMixMaxDamage = 75;
	public YYItemBaitMix(int var1){
		super(var1);
		this.SetBuoyant();
		this.setUnlocalizedName("yyBaitMix");
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
		FCUtilsItem.GivePlayerStackOrEject(player, new ItemStack(YYBaitMod.yyItemBait, 2));
		return new ItemStack(Item.bowlEmpty);
	}
	
	protected int GetProgressiveCraftingMaxDamage()
	{
		return m_iBaitMixMaxDamage;
	}
}
