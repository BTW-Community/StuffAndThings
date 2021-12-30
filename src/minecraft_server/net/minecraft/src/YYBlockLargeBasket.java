package net.minecraft.src;

public class YYBlockLargeBasket extends FCBlockHamper
{
	
	protected YYBlockLargeBasket(int blockID)
	{
		super(blockID);
		this.setUnlocalizedName("yyBlockLargeBasket");
	}
	
	@Override
	public TileEntity createNewTileEntity( World world )
	{
		return new YYTileEntityLargeBasket();
	}
	
	@Override
	public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
	{
		if ( !world.isRemote)
		{
			if ( !FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing( world, i, j + 1, k, 0, true ) && 
				!FCUtilsWorld.IsBlockRestingOnThatBelow( world, i, j + 1, k ) )
			{
				YYTileEntityLargeBasket tileEntity = (YYTileEntityLargeBasket)world.getBlockTileEntity( i, j, k );
				
				if ( player instanceof EntityPlayerMP ) // should always be true
				{
					YYContainerLargeBasket container = new YYContainerLargeBasket( player.inventory, tileEntity );
					
					FCBetterThanWolves.ServerOpenCustomInterface( (EntityPlayerMP)player, container, YYStuffAndThings.yyLargeBasketContainerID );        		
				}
			}
		}
		
		return true;
	}
	
}
