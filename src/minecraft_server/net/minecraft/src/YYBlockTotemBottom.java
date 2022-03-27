package net.minecraft.src;

import java.util.Random;

public class YYBlockTotemBottom extends YYBlockTotem
{
	
	
	public YYBlockTotemBottom(int id)
	{
		super(id);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if(random.nextInt(100)<33)
		{
			return;
		}
		if(canGainHealth(world, x, y, z))
		{
			for(int j=0; j<getHealLimit(world); j++)
			{
				if(j!=0 && world.getBlockId(x, y+j, z)!=YYStuffAndThings.yyBlockTotem.blockID)
				{
					return;
				}
				if(!hasHealth(world, x, y+j, z))
				{
					setHealth(world, x, y+j, z, true);
					updateLastHealed(world, x, y, z);
					return;
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick)
	{
		boolean ret = false;
		if(!world.isRemote)
		{
			int j = y+7;
			while(!YYStuffAndThings.yyBlockTotem.hasHealth(world, x, --j, z) && j>=y);
			while(j>=y)
			{
				if(player.shouldHeal() && YYStuffAndThings.yyBlockTotem.hasHealth(world, x, j, z))
				{
					player.heal(1);
					int id = world.getBlockId(x, j, z);
					setHealth(world, x, j, z, false);
					ret = true;
				}
				j--;
			}
		}
		return ret;
	}
	
	public void updateLastHealed(World world, int x, int y, int z)
	{
		for(int i=-blockingRange; i<=blockingRange; i++)
		{
			for(int k=-blockingRange; k<=blockingRange; k++)
			{
				for(int j=0; j<256; j++)
				{
					int x1 = x+i;
					int y1 = j;
					int z1 = z+k;
					if(world.getBlockId(x1, y1, z1)==YYStuffAndThings.yyBlockTotemBottom.blockID)
					{
						world.setBlockMetadataWithNotify(x1, y1, z1, world.getBlockMetadata(x1, y1, z1) & 1);
					}
				}
			}
		}
	}
	
	public boolean isFullMoonUp(World world)
	{
		return false;
		// dear adventurer who was brave enough to check my source code
		// you might be wondering why this method exists
		// well, it's here because I decided to remove a few features that I wasn't satisfied with
		// and the easiest way to do that was to make this always false
		// and I'm lazy
		// ... I might clean it up later
		// - yany
		
		// return world.getMoonPhase() == 0 && !world.isDaytime();
	}
	
	public int getHealLimit(World world)
	{
		return isFullMoonUp(world) ? 1 : 7;
	}
	
	public int getBlockingLimit(World world)
	{
		return isFullMoonUp(world) ? 10 : 1;
	}
	
	public static final int blockingRange = 60;
	public boolean canGainHealth(World world, int x, int y, int z)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		int healBlocking = 0;
		for(int i=-blockingRange; i<=blockingRange; i++)
		{
			for(int k=-blockingRange; k<=blockingRange; k++)
			{
				for(int j=0; j<256; j++)
				{
					int x1 = x+i;
					int y1 = j;
					int z1 = z+k;
					if(world.getBlockId(x1, y1, z1)==YYStuffAndThings.yyBlockTotemBottom.blockID && hasHealth(world, x1, y1, z1))
					{
						healBlocking++;
					}
				}
			}
		}
		int blockingLimit = getBlockingLimit(world);
		boolean notBlockedByOther = 
			healBlocking < blockingLimit ||
			(
				healBlocking == blockingLimit &&
				hasHealth(world, x, y, z)
			);
		
		
		boolean notBlockedByTime = false;
		if(isFullMoonUp(world) || ((metadata>>1)&7)==7)
		{
			notBlockedByTime = true;
		}
		else if(notBlockedByOther)
		{
			metadata+=2;
			world.setBlockMetadataWithNotify(x, y, z, metadata);
		}
		
		return notBlockedByOther && notBlockedByTime;
	}
}
