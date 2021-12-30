package net.minecraft.src;

public class YYBlockOreRedstone extends FCBlockRedstoneOre
{
	public YYBlockOreRedstone(int id, boolean glowing)
	{
		super(id, glowing);
	}

	@Override
	protected void EjectItemsOnChiselConversion(ItemStack stack, World world, int i, int j, int k, int iOldMetadata,int iFromSide) {}
	
}
