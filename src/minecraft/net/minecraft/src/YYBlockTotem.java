package net.minecraft.src;

import java.util.Random;

public class YYBlockTotem extends YYBlockTotemBase
{
	public FCModelBlock model;
	
	public YYBlockTotem(int id)
	{
		super(id, Material.wood);
		setUnlocalizedName("yyBlockTotem");
		SetFireProperties(FCEnumFlammability.LOGS);
		setStepSound(soundWoodFootstep);
		setTickRandomly(true);
		setHardness(10);
		
		model = new FCModelBlock();
		double d = 0.125;
		double di = 1-d;
		model.AddBox(d, 0, d, di, 1, di);
		d = 0.0625;
		di = 1-d;
		model.AddBox(d, 0.125, d, di, 0.875, di);
	}
	
	public boolean hasHealth(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		return (id==YYStuffAndThings.yyBlockTotem.blockID || id==YYStuffAndThings.yyBlockTotemBottom.blockID) &&  hasHealth(world.getBlockMetadata(x, y, z));
	}
	
	public static boolean hasHealth(int metadata)
	{
		return (metadata&1) ==1;
	}
	
	public void setHealth(World world, int x, int y, int z, boolean health)
	{
		world.setBlockMetadataWithNotify(x, y, z, (world.getBlockMetadata(x, y, z)&(~1)) | (health ? 1 : 0));
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if(random.nextInt(100)<33)
		{
			return;
		}
		y--;
		Block.blocksList[world.getBlockId(x, y, z)].updateTick(world, x, y, z, random);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick)
	{
		boolean ret = false;
		int metadata = world.getBlockMetadata(x, y, z);
		if(player.isUsingSpecialKey() && iFacing>=2 && iFacing != metadata>>1)
		{
			ret = true;
			metadata = (metadata&1) | (iFacing<<1);
			world.setBlockMetadataWithNotify(x, y, z, metadata);
		}
		return ret;
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace( World world, int i, int j, int k, Vec3 startRay, Vec3 endRay )
	{
		return model.CollisionRayTrace( world, i, j, k, startRay, endRay );
	}


	Icon sideIcon;
	Icon sideHealIcon;
	Icon sideFaceIcon;
	
	public void registerIcons(IconRegister register)
	{
		sideHealIcon = register.registerIcon("yyBlockTotemFace_heal");
		sideFaceIcon = register.registerIcon("yyBlockTotemFace");
		sideIcon = register.registerIcon("yyBlockTotemCarving");
	}
	
	public Icon getIcon(int side, int metadata)
	{
		if(side==(metadata>>1))
		{
			if(hasHealth(metadata))
			{
				return sideHealIcon;
			}
			return sideFaceIcon;
		}
		return sideIcon;
	}
	
	public AxisAlignedBB aabb = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 1, 0.9375);
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		return model.RenderAsBlock(renderer, this, i, j, k);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		AxisAlignedBB temp = aabb.MakeTemporaryCopy();
		return temp.offset(x, y, z);
	}
}
