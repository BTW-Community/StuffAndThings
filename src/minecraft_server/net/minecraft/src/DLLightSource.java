package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class DLLightSource extends Block implements ITileEntityProvider
{
	public final static int lightSourceTickRate = 5;

	public DLLightSource( int iBlockID )
	{
		super( iBlockID, Material.air );  
		setLightValue( 0.9375F );    
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
	{
		return null;
	}
	@Override
	public int tickRate( World world )
	{
		return lightSourceTickRate;
	}    
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int getMobilityFlag()
	{
		// disable the ability for piston to push this block 	
		return 1;
	}
	
	@Override
	public int idDropped( int i, Random random, int iFortuneModifier )
	{
		return 0;
	}
	
	@Override
	public boolean canCollideCheck(int i, boolean flag)
	{
		return false;
	}    
	
	@Override
	public int quantityDropped( Random random )
	{
		return 0;
	}
	
	@Override
	public boolean IsAirBlock()
	{
		return true;
	}
	
	@Override
	public boolean TriggersBuddy()
	{
		return false;
	}

	public void checkToRemove( World world, int i, int j, int k)
	{
		if ( !isPlayerInBoundsWithTorch( world, i, j, k ) )
		{
			world.setBlock( i, j, k, 0, 0, 2 );  
		}
	}
	
	private boolean isPlayerInBoundsWithTorch( World world, int i, int j, int k )
	{
		List list = world.getEntitiesWithinAABB( Entity.class, 
		AxisAlignedBB.getAABBPool().getAABB( (double)i, (double)j-0.1, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1) ) ); // -0.1 helps seeing players that are just below the edge of the block
		if( list != null && list.size() > 0 )
		for(int listIndex = 0; listIndex < list.size(); listIndex++)
		{
			Entity targetEntity = (Entity)list.get( listIndex );

			if ( targetEntity instanceof EntityPlayer )
			{
				return ((EntityPlayer) targetEntity).isholdingtorch();
			}
			if((targetEntity.fire > 0) || targetEntity instanceof EntityBlaze || ((targetEntity instanceof EntityArrow || targetEntity instanceof EntityLiving) && world.isRemote))
			{
				return true;
			}
		}
		return false;
	}
	// OLD tryouts (Relics from lens block thing)
//		@Override
	//  public void onEntityCollidedWithBlock( World world, int i, int j, int k, Entity entity)
	//  { //sort of kickstarts the unupdate cycle
//			if ( !world.isRemote &&entity instanceof EntityPlayer)
//			{
//			    boolean bIsOn = IsEntityCollidingFlagOn( world, i, j, k );
//			    	
//			    if ( !bIsOn )
//			    {
//			    	SetEntityCollidingFlag( world, i, j, k, true );
//			    				    		
//			       	world.scheduleBlockUpdate( i, j, k, world.getBlockId( i, j, k ), tickRate( world ) );
//			    }
	//
//			}
	//  }
	//
//			public boolean IsEntityCollidingFlagOn( IBlockAccess iBlockAccess, int i, int j, int k )
//			{
//		        int iMetaData = iBlockAccess.getBlockMetadata( i, j, k );
//		        
//				return ( iMetaData & 1 ) > 0;
//			}
//		public void SetEntityCollidingFlag( World world, int i, int j, int k, boolean bEntityColliding )
//		{
//	        int iMetaData = ( world.getBlockMetadata( i, j, k ) ) & (~1); // filter out old state
//	        
//	        if ( bEntityColliding )
//	        {
//	        	iMetaData |= 1;
//	        }        
//	        world.setBlockMetadataWithNotifyNoClient( i, j, k, iMetaData );
	//
//		}
		
	   @Override
		public TileEntity createNewTileEntity(World var1) {
			return new YYTileEntityDynamicLightSource();
		}
		
		@Override
		public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
		{
			super.breakBlock(par1World, par2, par3, par4, par5, par6);
			par1World.removeBlockTileEntity(par2, par3, par4);
		}
}
