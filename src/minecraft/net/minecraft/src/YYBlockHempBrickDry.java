package net.minecraft.src;

import java.util.Random;

public class YYBlockHempBrickDry extends FCBlockUnfiredBrick
{
	public YYBlockHempBrickDry(int id)
	{
		super(id);
		setUnlocalizedName("yyBlockBrickHempDry");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new YYTileEntityHempBrickDry();
	}
	
	@Override
	public int idDropped(int iMetadata, Random random, int fortuneModifier)
	{
		return YYStuffAndThings.yyItemHempBrickDry.itemID;
	}
	
	@Override
	public void OnFinishedCooking( World world, int i, int j, int k )
	{
		int iMetadata = world.getBlockMetadata( i, j, k ) & 1; // preserve orientation
		
		world.setBlockAndMetadataWithNotify( i, j, k, YYStuffAndThings.yyBlockHempBrickWet.blockID, iMetadata ); 
	}
}
