package net.minecraft.src;

import java.util.Random;

public class YYBlockHempBrickWet extends FCBlockUnfiredBrick
{	
	public YYBlockHempBrickWet(int id)
	{
		super(id);
		setUnlocalizedName("yyBlockBrickHempWet");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new YYTileEntityHempBrickWet();
	}
	
	@Override
	public int idDropped(int iMetadata, Random random, int fortuneModifier)
	{
		return YYStuffAndThings.yyItemHempBrickWet.itemID;
	}
}
