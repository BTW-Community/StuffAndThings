package net.minecraft.src;

public class YYBlockHempCrop extends FCBlockHempCrop
{
	public YYBlockHempCrop(int id)
	{
		super(id);
	}

	@Override
	public void DropSeeds(World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier) {
		if(GetIsTopBlock(iMetadata))
		{
			FCUtilsItem.DropStackAsIfBlockHarvested( world, i, j, k, new ItemStack( getSeedItem(), 1, 0 ) );
		}
	}
	
}
