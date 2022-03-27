package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;

public class YYBlockTotemCarving extends YYBlockTotemBase
{
	public FCModelBlock models[];
	
	public YYBlockTotemCarving(int id)
	{
		super(id, FCBetterThanWolves.fcMaterialLog);
		setUnlocalizedName("yyBlockTotemCarving");
		SetChiselsEffectiveOn();
		SetFireProperties(FCEnumFlammability.LOGS);
		setStepSound(soundWoodFootstep);
		setHardness(2);
		
		models = new FCModelBlock[6];
		for(int i=0; i<5; i++)
		{
			models[i] = new FCModelBlock();
			double d = 0.016*i;
			double di = 1-d;
			models[i].AddBox(d, 0, d, di, 1, di);
		}
		models[5] = new FCModelBlock();
		models[5].AddBox(0.1, 0, 0.1, 0.9, 1, 0.9);
		models[5].AddBox(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);
	}
	
	@Override
	public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
	{
		return true;
	}

	@Override
	public boolean ConvertBlock(ItemStack stack, World world, int i, int j, int k, int iFromSide)
	{
		int metadata = world.getBlockMetadata(i, j, k);
		if((7&metadata)<=4)
		{
			world.setBlockMetadataWithNotify(i, j, k, metadata+1);
		}
		else
		{
			if((metadata&8)==0)
			{
				world.setBlockAndMetadata(i, j, k, YYStuffAndThings.yyBlockTotem.blockID, iFromSide<2?2:iFromSide<<1);
			}
			else
			{
				world.setBlockAndMetadata(i, j, k, YYStuffAndThings.yyBlockTotemBottom.blockID, 0);
			}
		}
		return true;
	}

	@Override
	public int GetHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k)
	{
		return 1000;
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace( World world, int i, int j, int k, Vec3 startRay, Vec3 endRay )
	{
		return models[world.getBlockMetadata(i, j, k)&7].CollisionRayTrace( world, i, j, k, startRay, endRay );
	}
}
