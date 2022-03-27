package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class YYBlockTotemBase extends Block
{
	protected YYBlockTotemBase(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int metadata)
	{
		ArrayList<Integer> removeIds = new ArrayList<Integer>(Arrays.asList(
				YYStuffAndThings.yyBlockTotem.blockID,
				YYStuffAndThings.yyBlockTotemBottom.blockID,
				YYStuffAndThings.yyBlockTotemCarving.blockID
			));
		
		if(!removeIds.contains(world.getBlockId(x, y, z)))
		{
			if(removeIds.contains(world.getBlockId(x, y+1, z)))
			{
				world.setBlockWithNotify(x, y+1, z, 0);
			}
			if(removeIds.contains(world.getBlockId(x, y-1, z)))
			{
				world.setBlockWithNotify(x, y-1, z, 0);
			}
		}
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getMobilityFlag()
	{
		return 2;
	}

	@Override
	public ItemStack GetStackRetrievedByBlockDispenser(World world, int i, int j, int k)
	{
		return null;
	}
	
	@Override
	public boolean GetDoesStumpRemoverWorkOnBlock(IBlockAccess blockAccess, int i, int j, int k)
	{
		return true;
	}
}
