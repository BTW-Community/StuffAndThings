package net.minecraft.src;

public class YYBlockOreDiamond extends FCBlockOreDiamond
{
	public YYBlockOreDiamond(int id)
	{
		super(id);
	}
	
	@Override
	protected void EjectItemsOnChiselConversion(ItemStack stack, World world, int i, int j, int k, int iOldMetadata,int iFromSide) {}
	
}
