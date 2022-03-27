package net.minecraft.src;

import java.util.Random;

public class YYBlockTotemSapling extends BlockFlower
{
	public YYBlockTotemSapling(int id)
	{
		super(id);
		setUnlocalizedName("yyBlockTotemSapling");
		setCreativeTab(CreativeTabs.tabDecorations);
		setHardness(0);
		SetBuoyant();
		setStepSound(soundGrassFootstep);
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if(world.provider.dimensionId==0 && world.canBlockSeeTheSky(i, j, k))
		{
			int metadata = world.getBlockMetadata(i, j, k);
			if(metadata<10)
			{
				metadata++;
				world.setBlockMetadataWithNotify(i, j, k, metadata);
			}
			else
			{
				world.setBlockAndMetadataWithNotify(i, j, k, YYStuffAndThings.yyBlockTotemCarving.blockID, 8);
				for(int y=1; y<=6; y++)
				{
					world.setBlockAndMetadataWithNotify(i, j+y, k, YYStuffAndThings.yyBlockTotemCarving.blockID, 0);
				}
			}
		}
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public ItemStack GetStackRetrievedByBlockDispenser(World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public boolean CanBeCrushedByFallingEntity(World world, int i, int j, int k, EntityFallingSand entity)
	{
		return true;
	}
}
